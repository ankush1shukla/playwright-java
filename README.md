# PlayWright Java Test Project

This project contains automated tests using PlayWright with Java and TestNG.

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Setup

1. Clone the repository
2. Install PlayWright browsers:
   ```bash
   mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
   ```

## Running Tests

### Run all PlayWright tests
```bash
mvn test -DsuiteXmlFile=testng-playwright.xml
```

### Run specific test class
```bash
mvn test -Dtest=PlayWrightTest
```

### Run with specific browser
```bash
mvn test -DsuiteXmlFile=testng.xml
```

## Test Structure

The `PlayWrightTest` class contains three test methods:

1. **testPlaywrightHomepage()** - Tests the Playwright.dev homepage title
2. **testPlaywrightGetStarted()** - Tests navigation to the Get Started page
3. **testPlaywrightSearch()** - Tests the search functionality

## GitHub Actions

The project includes a GitHub Actions workflow (`.github/workflows/playwright-tests.yml`) that:

- Runs on push to main/master branch and pull requests
- Sets up Java 11 and Maven
- Installs PlayWright browsers
- Runs the PlayWright tests
- Uploads test videos and reports as artifacts

## Test Artifacts

- Test videos are saved in the `videos/` directory
- Test reports are generated in `target/surefire-reports/`

## Browser Support

The tests are configured to run on Chromium in headless mode for CI/CD environments. 