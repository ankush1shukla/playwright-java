package com.test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PlayWrightTest {
	
	private Playwright playwright;
	private Browser browser;
	private BrowserContext context;
	private Page page;
	
	@BeforeMethod
	public void setUp() {
		playwright = Playwright.create();
		// Ensure headless mode is enabled for CI/CD
		browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
				.setHeadless(true)
				.setArgs(Arrays.asList("--no-sandbox", "--disable-dev-shm-usage")));
		context = browser.newContext(new Browser.NewContextOptions()
				.setRecordVideoDir(Paths.get("videos/"))
				.setRecordVideoSize(640, 480)
				.setViewportSize(1280, 720));
		page = context.newPage();
	}
	
	@AfterMethod
	public void tearDown() {
		try {
			if (page != null) {
				Path path = page.video().path();
				page.close();
				context.close();
				browser.close();
				playwright.close();
				
				// Rename video file for better identification
				String dir = System.getProperty("user.dir");
				File file = new File(dir + File.separator + "videos" + File.separator + path.getFileName());
				File file2 = new File(dir + File.separator + "videos" + File.separator + "test_video.webm");
				if (file.exists()) {
					file.renameTo(file2);
				}
			}
		} catch (Exception e) {
			System.err.println("Error during cleanup: " + e.getMessage());
		}
	}
	
	@Test(description = "Test Playwright.dev homepage title")
	public void testPlaywrightHomepage() {
		page.navigate("https://playwright.dev");
		String title = page.title();
		System.out.println("Page title is : " + title);
		assertTrue(title.contains("Playwright"));
	}
	
	@Test(description = "Test Playwright.dev navigation to Get Started page")
	public void testPlaywrightGetStarted() {
		page.navigate("https://playwright.dev");
		
		// Click on Get Started link
		page.locator("text=Get started").click();
		
		// Verify navigation to Get Started page
		assertThat(page).hasURL("https://playwright.dev/docs/intro");
		
		// Verify page contains expected content
		assertThat(page.locator("h1")).containsText("Installation");
		
	}
	

}
