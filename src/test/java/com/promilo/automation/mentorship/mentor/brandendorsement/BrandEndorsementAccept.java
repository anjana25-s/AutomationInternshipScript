package com.promilo.automation.mentorship.mentor.brandendorsement;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.LocatorOptions;
import com.promilo.automation.courses.intrestspages.ViewedIntrestPage;
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class BrandEndorsementAccept extends com.promilo.automation.resources.BaseClass{
	
	
	
	// ✅ Use generated email from previous test
    String emailToLogin = BaseClass.generatedEmail;
    String phoneToLogin = BaseClass.generatedPhone;

	 ExtentReports extent;
	    ExtentTest test;

	    @BeforeClass
	    public void setUpExtent() {
	        extent = ExtentManager.getInstance(); // Initialize ExtentReports
	    }

	    @AfterClass
	    public void tearDownExtent() {
	        if (extent != null) {
	            extent.flush();
	        }
	    }

	    @Test(
	        dependsOnMethods = {
	            "com.promilo.automation.mentorship.mentee.intrests.BrandEndorsement.mentorshipBrandEndorsement"
	        } 
	    )
public void AcceptVideoServiceRequestTest() throws Exception {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("✅ Accept video service - Positive Test");

   
 // LOAD EXCEL
    String excelPath = Paths.get(System.getProperty("user.dir"),
            "Testdata", "Mentorship Test Data.xlsx").toString();
    ExcelUtil excel = new ExcelUtil(excelPath, "CampaignCreation");


    int totalRows = excel.getRowCount();
    if (totalRows < 2) {
        test.fail("❌ No data found in Excel.");
        Assert.fail("No data in Excel");
    }

    // BUILD HEADER MAP
    int totalCols = excel.getColumnCount();
    Map<String, Integer> headerMap = new HashMap<>();
    for (int c = 0; c < totalCols; c++) {
        String header = excel.getCellData(0, c);
        if (header != null && !header.trim().isEmpty()) {
            headerMap.put(header.trim().replace("\u00A0", "").toLowerCase(), c);
        }
    }

    // FIND ROWS WITH KEYWORD = AddEmployment (or your relevant keyword)
    for (int i = 1; i < totalRows; i++) {
        String keyword = excel.getCellData(i, headerMap.get("keyword"));
        if (!"BrandEndorsementAccept".equalsIgnoreCase(keyword)) continue;

        String InputValue = excel.getCellData(i, headerMap.get("inputvalue"));
        String password = excel.getCellData(i, headerMap.get("password"));

        test.info("📘 Executing Excel row " + i + " | InputValue: " + InputValue);

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1080, 720);

        try {
            // HANDLE OPTIONAL POPUP
            MayBeLaterPopUp popup = new MayBeLaterPopUp(page);
            try { popup.getPopup().click(); test.info("✅ Popup closed"); } catch (Exception ignored) {}

            popup.clickLoginButton();

            // LOGIN
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill("92466825@qtvjnqv9.mailosaur.net");
            loginPage.passwordField().fill("Karthik@88");
            loginPage.loginButton().click();
            test.info("✅ Logged in with credentials: " + InputValue);
            HomePage myStuff= new HomePage(page);
            myStuff.mystuff().click();

            Hamburger myaccount= new Hamburger(page);
            myaccount.MyAccount().click();
            
            
            
            
            //click on my-acceptance button
            MyAcceptance acceptRequest= new MyAcceptance(page);
            acceptRequest.myAcceptance().click();
            
            
            
            String actualCampaignName=acceptRequest.brandEndorsementCampaignName().textContent().trim();
            assertEquals(actualCampaignName, "January Automation");
            
            String actualMenteeName=acceptRequest.brandEndorsementMenteeName().textContent().trim();
            assertEquals(actualMenteeName , "December");
            
            String actualHighlightTex=acceptRequest.brandEndorsementHighLightText().textContent().trim();
            assertEquals(actualHighlightTex, "dxgfchvjbng vbnm");
            
            
            
            String actualStatus=acceptRequest.brandEndorsementStatus().textContent().trim();
            assertEquals(actualStatus, "Completed");
            
            //click on brand Endorsement
            page.waitForTimeout(1000);
            acceptRequest.brandEndorsementAccept().click();
            System.out.println(acceptRequest.modalContent().textContent()); 
            
            //close the modal's exit icon
            page.locator("img[alt='Add New']").click();
            
            
            
         // Click the copy icon for email
            Locator mailcopyIcon = page.locator("(//p[text()='Brand Endorsement']//following::img[@alt='copyIcon'])[1]");
            mailcopyIcon.click();

            // Get clipboard content for email
            Clipboard clipboardMail = Toolkit.getDefaultToolkit().getSystemClipboard();
            String copiedMail = (String) clipboardMail.getData(DataFlavor.stringFlavor);
            System.out.println("Copied mail: " + copiedMail);

            // Click the copy icon for phone number
            Locator phonecopyIcon = page.locator("(//p[text()='Brand Endorsement']//following::img[@alt='copyIcon'])[2]");
            phonecopyIcon.click();

            // Get clipboard content for phone number
            Clipboard clipboardPhone = Toolkit.getDefaultToolkit().getSystemClipboard();
            String copiedNumber = (String) clipboardPhone.getData(DataFlavor.stringFlavor);
            System.out.println("Copied number: " + copiedNumber);

            // Expected dynamic values from signup utility
            String expectedEmail = BaseClass.generatedEmail.trim().toLowerCase();
            String expectedPhone = BaseClass.generatedPhone.trim();

            // Normalize copied values
            String copiedMailNormalized = copiedMail.trim().toLowerCase();
            String copiedNumberNormalized = copiedNumber.trim().replaceAll("\\D", "");
            String expectedPhoneDigits = expectedPhone.replaceAll("\\D", "");

            // ---- 🧠 MAIL VALIDATION ----
            if (copiedMailNormalized.equals(expectedEmail)) {
                System.out.println("✅ Copied mail matches generated mail: " + copiedMail);
            } else {
                System.out.println("❌ Copied mail does NOT match generated mail.");
                System.out.println("Expected: " + expectedEmail);
                System.out.println("Found:    " + copiedMailNormalized);
            }

            // ---- 📱 PHONE VALIDATION ----
            if (copiedNumberNormalized.equals(expectedPhoneDigits)) {
                System.out.println("✅ Copied phone number matches generated phone: " + copiedNumber);
            } else {
                System.out.println("❌ Copied phone number does NOT match generated phone.");
                System.out.println("Expected: " + expectedPhoneDigits);
                System.out.println("Found:    " + copiedNumberNormalized);
            }

            // Continue test flow
            acceptRequest.brandEnodorsementViewMessage().click();
            String modalText = acceptRequest.modalContent().textContent();
            System.out.println("Modal text: " + modalText);

            
            
            // Create a new browser or page context from mentee perspective
            Browser actualBrowser = browser.get();
            BrowserContext advertiserContext = actualBrowser.newContext();
            Page mentorPage = advertiserContext.newPage();

            // Now use Mentor Page as usual
            mentorPage.navigate(prop.getProperty("url"));

            
            MayBeLaterPopUp mayBeLaterPopUp2 = new MayBeLaterPopUp(mentorPage);
            try {
          	  mayBeLaterPopUp2.getPopup().click();
                test.info("✅ Popup closed.");
            } catch (Exception ignored) {
                test.info("ℹ️ No popup found.");
            }

            mayBeLaterPopUp2.clickLoginButton();              


            
         
               LoginPage loginPage1 = new LoginPage(mentorPage);
               loginPage1.loginMailPhone().fill(BaseClass.generatedPhone); // use the generated email
               loginPage1.loginWithOtp().click();
               loginPage1.otpField().fill("9999");
               loginPage1.loginButton().click();
         
               
               
               
               ViewedIntrestPage cardValidation= new ViewedIntrestPage(mentorPage);
               cardValidation.myInterestTab().click();
               page.waitForTimeout(1000);
               cardValidation.myPreferenceTab().click();
               Locator intrestCard=cardValidation.mentorshipCard();
               intrestCard.textContent().trim();
               assertTrue(intrestCard.isVisible());
               
               
               
               
               
               
               
               
              
            
            
            


            
          
            
            

        }
        
        
        catch (Exception e) {
            test.fail("❌ Test failed for row " + i + ": " + e.getMessage());
            e.printStackTrace();
        }
	
    }}
	
	
	
}
