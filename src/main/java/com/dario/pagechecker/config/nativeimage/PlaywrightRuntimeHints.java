package com.dario.pagechecker.config.nativeimage;

import static org.springframework.aot.hint.MemberCategory.DECLARED_FIELDS;
import static org.springframework.aot.hint.MemberCategory.INVOKE_DECLARED_CONSTRUCTORS;
import static org.springframework.aot.hint.MemberCategory.UNSAFE_ALLOCATED;

import com.microsoft.playwright.impl.driver.jar.DriverJar;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ViewportSize;
import java.util.Locale;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

public final class PlaywrightRuntimeHints implements RuntimeHintsRegistrar {

  @Override
  public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
    hints.reflection().registerType(DriverJar.class, INVOKE_DECLARED_CONSTRUCTORS);
    hints.reflection().registerType(
        TypeReference.of("com.microsoft.playwright.impl.Message"),
        DECLARED_FIELDS,
        INVOKE_DECLARED_CONSTRUCTORS,
        UNSAFE_ALLOCATED);
    registerGsonType(hints, "com.microsoft.playwright.impl.SerializedError");
    registerGsonType(hints, "com.microsoft.playwright.impl.SerializedError$Error");
    registerGsonType(hints, "com.microsoft.playwright.impl.SerializedValue");
    registerGsonType(hints, "com.microsoft.playwright.impl.SerializedValue$E");
    registerGsonType(hints, "com.microsoft.playwright.impl.SerializedValue$R");
    registerGsonType(hints, "com.microsoft.playwright.impl.SerializedValue$O");
    registerGsonType(hints, "com.microsoft.playwright.options.HttpHeader");
    registerGsonType(hints, "com.microsoft.playwright.options.Timing");
    hints.reflection().registerType(BrowserType.LaunchOptions.class, DECLARED_FIELDS);
    hints.reflection().registerType(
        Browser.NewContextOptions.class, DECLARED_FIELDS, INVOKE_DECLARED_CONSTRUCTORS);
    hints.reflection().registerType(BrowserContext.StorageStateOptions.class, DECLARED_FIELDS);
    hints.reflection().registerType(Page.NavigateOptions.class, DECLARED_FIELDS);
    hints.reflection().registerType(Page.WaitForLoadStateOptions.class, DECLARED_FIELDS);
    registerOptionType(hints, "com.microsoft.playwright.Frame$NavigateOptions");
    registerOptionType(hints, "com.microsoft.playwright.Frame$WaitForLoadStateOptions");
    registerOptionType(hints, "com.microsoft.playwright.Frame$ClickOptions");
    registerOptionType(hints, "com.microsoft.playwright.Frame$FillOptions");
    registerOptionType(hints, "com.microsoft.playwright.Frame$PressOptions");
    registerOptionType(hints, "com.microsoft.playwright.Frame$InnerTextOptions");
    registerOptionType(hints, "com.microsoft.playwright.Frame$WaitForSelectorOptions");
    hints.reflection().registerType(ViewportSize.class, DECLARED_FIELDS, UNSAFE_ALLOCATED);
    hints.reflection().registerType(Locator.ClickOptions.class, DECLARED_FIELDS);
    hints.reflection().registerType(Locator.FillOptions.class, DECLARED_FIELDS);
    hints.reflection().registerType(Locator.PressOptions.class, DECLARED_FIELDS);
    hints.reflection().registerType(Locator.WaitForOptions.class, DECLARED_FIELDS);
    hints.reflection().registerType(Locator.InnerTextOptions.class, DECLARED_FIELDS);
    hints.resources().registerPattern("driver/package/**");
    hints.resources().registerPattern("driver/" + playwrightPlatform() + "/**");
  }

  private static void registerGsonType(RuntimeHints hints, String typeName) {
    hints.reflection().registerType(
        TypeReference.of(typeName), DECLARED_FIELDS, INVOKE_DECLARED_CONSTRUCTORS, UNSAFE_ALLOCATED);
  }

  private static void registerOptionType(RuntimeHints hints, String typeName) {
    hints.reflection().registerType(
        TypeReference.of(typeName), DECLARED_FIELDS, INVOKE_DECLARED_CONSTRUCTORS);
  }

  private static String playwrightPlatform() {
    var os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
    var arch = System.getProperty("os.arch").toLowerCase(Locale.ROOT);

    if (os.contains("mac")) {
      return arch.contains("aarch64") ? "mac-arm64" : "mac";
    }
    if (os.contains("win")) {
      return "win32_x64";
    }
    return arch.contains("aarch64") ? "linux-arm64" : "linux";
  }
}
