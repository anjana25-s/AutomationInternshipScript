package com.promilo.automation.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterClass;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseClass {

	public static String generatedEmail;
	public static String generatedPhone;

	public static boolean keepSessionAlive = false;

	public static String selectedDate = "";
	public static String selectedTime = "";
	public static String askYourQuestionText = "Ask Your Questions Here";
	public static String courseFee;

	public static String rescheduleDate = "";
	public static String rescheduleTime = "";

	private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger(BaseClass.class);

	private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
	protected static ThreadLocal<Browser> browser = new ThreadLocal<>();
	private static ThreadLocal<BrowserContext> context = new ThreadLocal<>();
	private static ThreadLocal<Page> page = new ThreadLocal<>();

	public Properties prop;

	public Page initializePlaywright() throws IOException {

		prop = new Properties();
		String path = System.getProperty("user.dir")
				+ "/src/main/java/com/promilo/automation/resources/data.properties";
		FileInputStream fis = new FileInputStream(path);
		prop.load(fis);

		String browserName = prop.getProperty("browser", "chromium").toLowerCase();
		boolean headless = Boolean.parseBoolean(prop.getProperty("headless", "false"));

		int globalWait = Integer.parseInt(prop.getProperty("globalWait", "500"));
		log.info("⏳ Global wait set to: {} seconds", globalWait);

		playwright.set(Playwright.create());

		BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(headless).setSlowMo(200);

		switch (browserName) {
		case "chrome":
		case "chromium":
			browser.set(playwright.get().chromium().launch(launchOptions));
			break;
		case "firefox":
			browser.set(playwright.get().firefox().launch(launchOptions));
			break;
		case "webkit":
			browser.set(playwright.get().webkit().launch(launchOptions));
			break;
		default:
			throw new RuntimeException("❌ Browser not supported: " + browserName);
		}

		context.set(browser.get().newContext(new Browser.NewContextOptions().setViewportSize(null)));

		page.set(context.get().newPage());

		// 🔥 GLOBAL AI VOICE BOT AUTO-CLOSER (INJECTED ONCE)
		injectAIVoiceBotAutoCloser(page.get());

		page.get().setDefaultTimeout(globalWait * 1000L);
		page.get().setDefaultNavigationTimeout(globalWait * 1000L);

		log.info("✅ Global wait applied: {} seconds for all actions & navigations", globalWait);

		return page.get();
	}

	/**
	 * Injects JS into page to auto-close AI Voice Bot whenever it appears. Clicks
	 * ONLY if the exit icon is visible.
	 */
	private void injectAIVoiceBotAutoCloser(Page page) {

		String script = "setInterval(() => {" + "  try {" + "    const icon = document.evaluate("
				+ "      \"//img[@src='/assets/closeMiliIcon-96dcded9.svg']\"," + "      document," + "      null,"
				+ "      XPathResult.FIRST_ORDERED_NODE_TYPE," + "      null" + "    ).singleNodeValue;"
				+ "    if (icon && icon.offsetParent !== null) {" + "      icon.click();" + "    }" + "  } catch (e) {}"
				+ "}, 2000);";

		page.addInitScript(script);
	}

	public void maximizeWindow() {
		Page currentPage = page.get();
		if (currentPage != null) {
			log.info("✅ Window maximized using setViewportSize(1920, 1080).");
		} else {
			throw new RuntimeException("❌ Page is not initialized. Cannot maximize window.");
		}
	}

	@AfterClass(alwaysRun = true)
	public void closePlaywright() {

		if (context.get() != null) {
			context.get().close();
			context.remove();
		}

		if (browser.get() != null) {
			browser.get().close();
			browser.remove();
		}

		if (playwright.get() != null) {
			playwright.get().close();
			playwright.remove();
		}

		if (page.get() != null) {
			page.remove();
		}

		log.info("✅ Browser and Playwright closed after entire class execution.");
	}

	public static Page getPage() {
		return page.get();
	}

	public static BrowserContext getContext() {
		return context.get();
	}

	public static Browser getBrowser() {
		return browser.get();
	}

	public static Playwright getPlaywright() {
		return playwright.get();
	}
}
