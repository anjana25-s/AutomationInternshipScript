package basetest;
import com.microsoft.playwright.*;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class Baseclass {

    public Playwright playwright;
    public Browser browser;
    public BrowserContext context;
    public Page page;

    @BeforeMethod
    public void launchBrowser(ITestContext testContext) {

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );

        context = browser.newContext();
        page = context.newPage();

        testContext.setAttribute("page", page);

        page.navigate("https://stage.promilo.com");
    }

    @AfterMethod
    public void tearDown() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }


}
