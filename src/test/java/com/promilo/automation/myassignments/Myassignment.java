package com.promilo.automation.myassignments;

import static org.testng.Assert.assertEquals;

import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class Myassignment extends BaseClass {

    private static final Logger logger = LogManager.getLogger(Myassignment.class);

    private static String registeredEmail;
    private static String registeredPassword;

    private static final ExtentReports extent = ExtentManager.getInstance();

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        logger.info("Performing signup once before suite...");

        SignupWithMailosaurUI signup = new SignupWithMailosaurUI();
        String[] creds = signup.performSignupAndReturnCredentials();

        registeredEmail = creds[0];
        registeredPassword = creds[1];

        logger.info("Signup completed with email: {}", registeredEmail);
    }

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {

        String excelPath = Paths.get(
                System.getProperty("user.dir"),
                "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx"
        ).toString();

        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloJob");

        int rowCount = 0;
        for (int i = 1; i <= 1; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.isEmpty())
                break;
            rowCount++;
        }

        Object[][] data = new Object[rowCount][8];
        for (int i = 1; i <= rowCount; i++) {
            data[i - 1][0] = excel.getCellData(i, 0); // TestCaseID
            data[i - 1][1] = excel.getCellData(i, 1); // Keyword
            data[i - 1][2] = excel.getCellData(i, 4); // InputValue
            data[i - 1][3] = excel.getCellData(i, 6); // Password
            data[i - 1][4] = excel.getCellData(i, 7); // Name
            data[i - 1][5] = excel.getCellData(i, 5); // OTP
            data[i - 1][6] = excel.getCellData(i, 8); // MailPhone
            data[i - 1][7] = i; // RowIndex
        }
        return data;
    }

    @Test(dataProvider = "jobApplicationData")
    public void applyForJobTestFromExcel(String testCaseId, String keyword,
                                         String inputvalue, String password,
                                         String name, String otp,
                                         String mailphone, int rowIndex) throws Exception {

        ExtentTest test = extent.createTest("My Assignment | " + testCaseId);

        

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        applyForJobAsRegisteredUser(page, inputvalue, password, test);

        test.pass("Assignment submitted successfully");
        extent.flush();
    }

    public void applyForJobAsRegisteredUser(Page page,
                                             String email,
                                             String password,
                                             ExtentTest test) throws Exception {

        MayBeLaterPopUp popup = new MayBeLaterPopUp(page);
        try {
            popup.getPopup().click();
        } catch (Exception ignored) {
        }

        popup.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill("rest-missing@qtvjnqv9.mailosaur.net");
        loginPage.passwordField().fill("Karthik@88");
        loginPage.loginButton().click();

        Hamburger hamburger = new Hamburger(page);
        hamburger.Mystuff().click();
        hamburger.MyAccount().click();

        page.locator("//a[text()='My Assignment']").click();
        page.locator("//span[text()='Start Assignment']").first().click();

        Thread.sleep(2000);
        Locator title = page.locator("(//div[@class='assignment-feedback-comment'])[1]");
        Locator topic = page.locator("(//div[@class='assignment-feedback-comment'])[2]");
        Locator description= page.locator("[class='assignment-feedback-comment description-box ck-content font-sm-12 bullets']");

        String titleText = title.textContent().trim();
        String topicText = topic.textContent().trim();
        String descriptionText= description.textContent().trim();
        
        assertEquals(titleText, "software Tester Assignment");
        assertEquals(topicText, "Automation Task");
        assertEquals(descriptionText, "We are seeking a highly skilled and detail-oriented Software DeveloperWe are seeking a highly skilled and detail-oriented Software Developer…");

        test.info("Assignment Title: " + titleText);
        test.info("Assignment Topic: " + topicText);

        Assert.assertFalse(titleText.isEmpty(), "Assignment title empty");
        Assert.assertFalse(topicText.isEmpty(), "Assignment topic empty");
        
        

        page.locator("//span[text()='Submit Assignment']").click();
        
     // Open text editor and type assignment
        page.locator("div[aria-label='Editor editing area: main']").click();
        page.keyboard().type("Automated assignment submission with Mailosaur user.");
        
        page.locator("//span[text()='Submit']").click();
        Locator toast = page.locator("//div[@role='status']");
        toast.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        

        Assert.assertTrue(toast.isVisible(), "Toast not displayed");
        test.pass("Toast displayed successfully");

        page.close();
    }
}
