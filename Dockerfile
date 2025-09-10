# ---------- 1) Build the JAR ----------
FROM maven:3.9.9-eclipse-temurin-21-jammy AS build
WORKDIR /app
COPY . .
RUN mvn -B -q package -DskipTests

# ---------- 2) Bring in Playwright browsers ----------
# This image already contains Chromium/WebKit/Firefox + all OS deps.
FROM mcr.microsoft.com/playwright:v1.47.0-jammy AS pw

# ---------- 3) Runtime (JRE + minimal libs) ----------
FROM eclipse-temurin:21-jre-jammy

# Install Playwright/Chromium runtime dependencies (smaller than full pw image)
RUN apt-get update && apt-get install -y --no-install-recommends \
    libnss3 libatk1.0-0 libatk-bridge2.0-0 libcups2 libdrm2 \
    libxkbcommon0 libasound2 libxcomposite1 libxdamage1 libxrandr2 \
    libgbm1 libgtk-3-0 libpango-1.0-0 libpangocairo-1.0-0 libatspi2.0-0 \
    libwayland-client0 libwayland-server0 libxshmfence1 fonts-liberation \
    ca-certificates libcurl4 \
 && rm -rf /var/lib/apt/lists/*

# Copy the Playwright browsers from the pw stage
COPY --from=pw /ms-playwright /ms-playwright

# App
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar

# Tell Playwright to use the preinstalled browsers and skip downloads
ENV PLAYWRIGHT_BROWSERS_PATH=/ms-playwright
ENV PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD=1

# Make a home dir for storage state
RUN mkdir -p /root/.visa-checker

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
