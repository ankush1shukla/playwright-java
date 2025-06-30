#!/bin/bash

echo "Setting up PlayWright environment..."

# Create videos directory if it doesn't exist
mkdir -p videos

# Install PlayWright browsers (if not already installed)
echo "Installing PlayWright browsers..."
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chromium"

# Run the tests
echo "Running PlayWright tests..."
mvn test -DsuiteXmlFile=testng-playwright.xml -Dheadless=true

echo "Tests completed. Check the videos/ directory for test recordings." 