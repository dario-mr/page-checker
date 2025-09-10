package com.dario.pagechecker.core.service.visa;

import static com.microsoft.playwright.options.LoadState.NETWORKIDLE;
import static java.text.Normalizer.Form.NFD;
import static java.text.Normalizer.normalize;

import com.dario.pagechecker.core.service.EmailService;
import com.dario.pagechecker.core.service.ShutdownService;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisaStatusChecker {

  private final EmailService emailService;
  private final ShutdownService shutdownService;

  private static final Path STATE_FILE = Path.of("visa-status.json");
  private static final String IN_PROGRESS = "zpracovava se";

  @Value("${visa-checker.url}")
  private String url;

  public void checkStatus() {
    log.info("Checking VISA status from url {}... ", url);

    try (var pw = Playwright.create();
        var browser = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false))) {
      var context = prepareContext(browser);
      var page = navigateTo(context, url);

      fillForm(page);
      var applicationStatus = submitAndExtractStatus(page);

      var inProgress = applicationStatus.contains(IN_PROGRESS);
      if (!inProgress) {
        log.info("⚠️ VISA application status changed: {}", applicationStatus);
        emailService.send("⚠️ VISA application status changed", "New state: " + applicationStatus);
        shutdownService.shutdown(0);
        return;
      }

      context.storageState(new BrowserContext.StorageStateOptions().setPath(STATE_FILE));
      log.info("VISA status check completed");
    } catch (Exception e) {
      log.error("Error during VISA status check", e);
    }
  }

  private BrowserContext prepareContext(Browser browser) {
    var contextOptions = new Browser.NewContextOptions();
    if (Files.exists(STATE_FILE)) {
      contextOptions.setStorageStatePath(STATE_FILE);
    }
    return browser.newContext(contextOptions);
  }

  private Page navigateTo(BrowserContext browserContext, String url) {
    var page = browserContext.newPage();
    page.navigate(url);
    page.waitForLoadState(NETWORKIDLE);
    return page;
  }

  private void fillForm(Page page) {
    page.locator("input[name='proceedings.referenceNumber']").fill("10371");
    page.locator("input[name='proceedings.additionalSuffix']").fill("04");
    selectReactOptionByIndex(page, 0, "PP");
    selectReactOptionByIndex(page, 1, "2025");
  }

  private void selectReactOptionByIndex(Page page, int index, String valueToChoose) {
    var controls = page.locator(".react-select__control");
    controls.nth(index).click();

    var input = page.locator("input[aria-autocomplete='list']").nth(index);
    input.fill(valueToChoose);
    input.press("Enter");
  }

  private String submitAndExtractStatus(Page page) {
    page.click("button[type=submit], input[type=submit]");
    handleCaptcha();

    var alertContent = page.locator(".alert.alert--form-warning .alert__content").first();
    alertContent.waitFor();

    // the bold element contains the status value
    var statusRaw = alertContent.locator("b").first().innerText().trim();

    return normalize(statusRaw, NFD)
        .replaceAll("\\p{M}+", "") // remove diacritics
        .toLowerCase();
  }

  private void handleCaptcha() {
    // TODO it seems we never get a captcha, so this stays empty for now
  }

}
