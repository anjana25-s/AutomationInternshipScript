package com.promilo.automation.mentorship.mentor.campaign;

import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.mentorship.mentee.AskUsWithInvalidData;
import com.promilo.automation.mentorship.mentee.MentorshipFormComponents;
import com.promilo.automation.mentorship.mentor.AboutMePage;
import com.promilo.automation.mentorship.mentor.BecomeMentor;
import com.promilo.automation.mentorship.mentor.CampaignDetails;
import com.promilo.automation.mentorship.mentor.CreateProfile;
import com.promilo.automation.mentorship.mentor.ServicesPage;
import com.promilo.automation.mentorship.mentor.myAudiencePage;
import com.promilo.automation.pageobjects.myresume.MyResumePage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class CampaignCreation extends BaseClass {

    private static final Logger log = LogManager.getLogger(AskUsWithInvalidData.class);

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        System.out.println("⚙️ Performing signup ONCE before suite using Mailosaur...");

        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();

        registeredEmail = creds[0];
        registeredPassword = creds[1];

        System.out.println("✅ Signup completed. Registered user: " + registeredEmail);
    }

    @Test
    public void addEmploymentPositiveTest() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("✅ Add Employment - Positive Test");

        if (registeredEmail == null || registeredPassword == null) {
            test.fail("❌ Signup credentials not found. Aborting test.");
            return;
        }

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1080, 720);

        test.info("🌐 Navigated to application URL.");

        String excelPath = Paths.get(System.getProperty("user.dir"),
                "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        test.info("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String keyword = excel.getCellData(i, 1);
            if (!"AddEmployment".equalsIgnoreCase(keyword)) continue;

            String inputValue = excel.getCellData(i, 3);
            String description = excel.getCellData(i, 10);

            try {
                test.info("➡️ Starting execution for row " + i + " with input: " + inputValue);

                LandingPage landingPage = new LandingPage(page);
                try {
                    landingPage.getPopup().click();
                    test.info("✅ Popup closed.");
                } catch (Exception ignored) {
                    test.info("ℹ️ No popup found.");
                }

                landingPage.clickLoginButton();
                test.info("🔑 Navigating to Login Page.");

                LoginPage loginPage = new LoginPage(page);
                loginPage.loginMailPhone().fill(registeredEmail);
                loginPage.passwordField().fill(registeredPassword);
                loginPage.loginButton().click();
                test.info("✅ Logged in with registered credentials: " + registeredEmail);
                
                
                

                // Navigate to My stuff
                MyResumePage resumePage = new MyResumePage(page);
                resumePage.Mystuff().click();
                
                

                

                BecomeMentor becomeMentor= new BecomeMentor(page);
                becomeMentor.becomeMentorButton().click();
                becomeMentor.createMentorshipSession().click();

                CreateProfile profile= new CreateProfile(page);
                profile.firstName().fill("karthik");
                profile.lastName().fill("U");
                
                
                
                
                
                
             // Generate dynamic phone number starting from 90000xxxxx
                String dynamicPhone = "90000" + String.format("%05d", (int)(Math.random() * 100000));
                profile.mobileTextfield().fill(dynamicPhone);
                log.info("📱 Entered dynamic phone number: " + dynamicPhone);
                
                
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Send OTP")).click();
                page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("mobile_otp")).fill("9999");
                page.waitForTimeout(2000);
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Verify OTP")).click();
                
                profile.locationDropdown().first().click();
                page.keyboard().type("Ananta");
                page.keyboard().press("Enter");
                
                //Gender
                profile.genderDropdown().first().click();
                page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Male").setExact(true)).click();
                
                
                //Experiance
                profile.experianceDropdwon().first().click();
                page.keyboard().type("2");
                page.keyboard().press("Enter");
                
                
                //type of mentor
                profile.typeofMentor().click();
                page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Academic Guidance")).click();

                
                
                //domain
                profile.domain().click();
                page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Course Selection")).click();
                
                
                page.waitForTimeout(4000);
                

                //category
                profile.categoryDropdown().click();
                page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Engineering")).locator("span").click();
                System.out.println("clicked on button");
                
                //courses
                profile.coursesDropdown().click();
                page.getByText("BE/B.Tech").first().click();
                
                
                profile.dateOfBirth().first().fill("2002-08-24");

                
                //specialization
                profile.specialization().click();
                page.locator("li > .select-item > .item-renderer > span").first().click();

                
                
                //upload picture
                profile.uploadCampaignImage().setInputFiles(Paths.get("C:/Users/Admin/Downloads/pexels-moh-adbelghaffar-771742.jpg"));
                profile.cropButton().click();
                
                //social media
                profile.socialmediaLinks().click();
                profile.instagramOption().click();
                profile.pasteLinks().fill("https://www.linkedin.com/feed/");
                
 
                page.waitForTimeout(2000);
                
                profile.highlightTextfield().fill("dxgfchvjbng vbnm");
                profile.saveButton().click();
                
                page.waitForTimeout(4000);
                
                
                
                //Campaign Details
                CampaignDetails camapaignDetails= new CampaignDetails(page);
                camapaignDetails.campaignName().fill("october Automation");
                camapaignDetails.startDate().fill("2025-10-09");
                page.waitForTimeout(3000);
                camapaignDetails.endDate().fill("2026-10-01");
                camapaignDetails.clickheretoAddskills().click();
                camapaignDetails.skills().fill("automation");
                page.keyboard().press("Enter");
                
                camapaignDetails.saveButton().nth(1).click();
                
                System.out.println("saveButton clicked");
                Thread.sleep(3000);
                // Capture screenshot after successful login
                String screenshotPath = System.getProperty("user.dir") + "/screenshots/"  + "_login_pass.png";
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
                test.addScreenCaptureFromPath(screenshotPath, "🖼️ Screenshot after login");

                
                
                //About ME Page
                
                AboutMePage aboutMe= new AboutMePage(page);
             // Store fun facts in a variable
                String aboutMeText = """
                    Bananas are berries, but strawberries are not.
                    The inventor of the Pringles can was buried in one. (Fred Baur’s ashes were placed in a Pringles tube in 2008).
                    Sharks existed before trees — sharks have been around for about 400 million years, trees about 350 million. 🦈🌳
                    In Japan, there are vending machines that sell fresh eggs.
                    Your stomach gets a new lining every 3–4 days so it doesn’t digest itself.
                """;

                // Fill the textarea
                aboutMe.textArea().fill(aboutMeText);

                aboutMe.addTittle().click();
                Locator elements = page.locator("//div[@class='d-flex align-items-center']");
                
                aboutMe.focus().click();
                aboutMe.fluentIn().click();
                aboutMe.addButton().click();

                         
            
             aboutMe.aboutmeOptions().nth(1).click();
             aboutMe.textArea().fill(aboutMeText);
             
             
             aboutMe.aboutmeOptions().nth(2).click();
             aboutMe.textArea().fill(aboutMeText);
             
             aboutMe.saveButton().nth(2).click();
             
             
             //my audience section
             myAudiencePage audience= new myAudiencePage(page);
             audience.audienceIndustry().click();
             audience.industryOption().click();
             
             
             audience.functionalArea().click();
             page.getByText("Accounting / Tax / Company").click();
             page.waitForTimeout(3000);
             
             audience.relevantTitle().click();
             audience.relevantTitleOption().nth(1).click();
             
             
             audience.audienceLocation().click();
             audience.audiencelocationOption().first().click();
             
             audience.Keywords().click();
             // Generate a random word (8 letters)
             int wordLength = 8;
             String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
             StringBuilder randomWord = new StringBuilder();
             Random random = new Random();

             for (int k = 0; k < wordLength; k++) {
                 randomWord.append(alphabet.charAt(random.nextInt(alphabet.length())));
             }

             // Fill the random word
             audience.Keywords().fill(randomWord.toString());
             page.keyboard().press("Enter");

             System.out.println("✅ Entered Random Word: " + randomWord);
             
             
             
             //min Age
             Locator minAge=page.locator(".form-floating.override-mb-0 > .react-select-container > .react-select__control > .react-select__value-container > .react-select__input-container").first();
             minAge.scrollIntoViewIfNeeded();
             minAge.click();
             page.keyboard().type("25");
             page.keyboard().press("Enter");
             
             
             //max Age
             page.locator("div:nth-child(7) > .form-floating > .react-select-container > .react-select__control > .react-select__value-container > .react-select__input-container").click();
             page.keyboard().type("30");
             page.keyboard().press("Enter");
             audience.Savebutton().click();
             
             
             //Services 
             
             ServicesPage services= new ServicesPage(page);
             //one:one call
             services.oneonecall().click();
             services.serviceName().fill("karthik");
             
             page.waitForTimeout(2000);
             page.locator(".rmsc.service-react-multi-select > .dropdown-container > .dropdown-heading").click();
             page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("English")).click();

            
             
             
             services.serviceFee().fill("800");
             services.evaluate().click();
             System.out.println(services.noserviceAdded().textContent()); 
             services.addService().click();
             System.out.println(services.serviceCard().textContent()); 
             
             //video call
             
             ServicesPage videoCall= new ServicesPage(page);
             videoCall.videoCall().click();
             videoCall.serviceName().fill("video Call");
             page.locator(".rmsc.service-react-multi-select > .dropdown-container > .dropdown-heading").click();
             page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("English")).click();
             
             
             Thread.sleep(2000);
             
             Locator inputContainer = page.locator("//div[@class='react-select__value-container css-hlgwow']//div[@class='react-select__input-container css-19bb58m']");
             inputContainer.nth(1).click();
             System.out.println("slot selected");
             page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("15 mins")).locator("div").click();
             
             videoCall.calenderButton().click();
             
             videoCall.Monday().click();
             

             
            

             // Start time at 12:00 AM
             LocalTime time = LocalTime.of(0, 0);
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a"); // Added space before AM/PM

             // Loop to create 10 slots (each 15 minutes apart)
             for (int slot = 1; slot <= 10; slot++) {
                 // Click the plus icon to add a new slot
                 videoCall.plusIcon().first().click();
                 page.waitForTimeout(1000);

                 // Locate all time input fields
                 Locator timeFields = page.locator("//div[@class='time-slot-select__input-container css-19bb58m']");

                 // Click on the latest (newly created) time field
                 int fieldCount = timeFields.count();
                 timeFields.nth(fieldCount - 1).click();

                 // Format and type the time (12:00 am, 12:15 am, 12:30 am, etc.)
                 String formattedTime = time.format(formatter).toLowerCase();
                 page.keyboard().type(formattedTime);

                 // Press Enter to confirm the selected time
                 page.keyboard().press("Enter");

                 // Short pause before next slot
                 page.waitForTimeout(700);

                 // Increase time by 15 minutes for next slot
                 time = time.plusMinutes(15);
             }

             // After all slots are created, click on the Submit button
             videoCall.submitButton().click();
             videoCall.addService().click();
             
             
             
             
             
             
             
             
             
             
             
             //ask query 
             ServicesPage askQuery= new ServicesPage(page);
             askQuery.askQuery().click();
             askQuery.serviceName().fill("Ask query");
             askQuery.serviceFee().fill("700");
             askQuery.evaluate().click();
             page.locator("//div[contains(@class,'react-select__value-container css-hlgwow')]//div[contains(@class,'react-select__input-container css-19bb58m')]").nth(1).click();
             page.keyboard().type("2");
             page.keyboard().press("Enter");
             askQuery.addService().click();
             System.out.println(askQuery.serviceCard().textContent()); 
             
             
             //resources
             ServicesPage resources= new ServicesPage(page);
             resources.resources().click();
             resources.serviceName().fill("resources");
             resources.serviceFee().fill("600");
             resources.evaluate().click();
             resources.uploadFile().setInputFiles(Paths.get("C:/Users/Admin/Downloads/pexels-moh-adbelghaffar-771742.jpg"));
             resources.addService().click();
             Thread.sleep(3000);
             
             //brand Endorsement
             ServicesPage brandEndorsement= new ServicesPage(page);
             brandEndorsement.brandEndoursement().click();
             brandEndorsement.serviceName().fill("service Name");
             brandEndorsement.addService().click();
             
             //Personalized Video
             ServicesPage personalizedVideo= new ServicesPage(page);
             personalizedVideo.personalizedVideo().click();
             personalizedVideo.serviceName().fill("service name");
             personalizedVideo.serviceFee().fill("700");
             personalizedVideo.evaluate().click();
             personalizedVideo.videoDuration().click();
             page.keyboard().type("5");
             page.keyboard().press("Enter");
             personalizedVideo.deliveryTime().click();
             page.keyboard().type("3");
             page.keyboard().press("Enter");
             personalizedVideo.addService().click();
             
             //save and next button
             personalizedVideo.saveAndNextButton().nth(2).click();
             System.out.println("save button clicked");
             
             
             com.promilo.automation.mentorship.mentor.BudgetAndCost budgeAndCost= new com.promilo.automation.mentorship.mentor.BudgetAndCost(page);
             budgeAndCost.addInvoiceinfo().click();
             
             // -------------------- Invoice Form --------------------
             MentorshipFormComponents askQuery1= new MentorshipFormComponents(page);
             askQuery1.InvoiceNameField().fill("Karthik");
             askQuery1.StreetAdress1().fill("Dasarahalli");
             askQuery1.StreetAdress2().fill("Bangalore");
             askQuery1.pinCode().fill("560057");
             askQuery1.yesRadrioBox().click();
             askQuery1.gstNumber().fill("22AAAAA9999A1Z5");
             askQuery1.panNumber().fill("AAAAA9999A");
             page.getByRole(AriaRole.CHECKBOX, 
                 new Page.GetByRoleOptions().setName("By checking this box, I")).check();
             
             Locator saveButton=page.locator("//button[normalize-space()='Save']");
             saveButton.scrollIntoViewIfNeeded();
             saveButton.click();             
             
             
             //Budget And Cost
             budgeAndCost.addbankAccount().click();
             budgeAndCost.accountHoldername().fill("karthik");
             budgeAndCost.accountNumber().fill("12345678901");
             budgeAndCost.branchName().fill("MG Road");
             budgeAndCost.ifscCode().fill("KKBK0008032");
             budgeAndCost.panNumber().fill("AMKPU0932P");
             budgeAndCost.billingCountry().nth(9).click();
             page.keyboard().type("ind");
             page.keyboard().press("Enter");
             
             Locator saveButton2=page.locator("//button[normalize-space()='Save']");
             saveButton2.scrollIntoViewIfNeeded();
             saveButton2.click();             
             
             
             
             budgeAndCost.emailCheckbox().click();
             budgeAndCost.smsCheckbox().click();
             
             
             page.locator("//button[text()='Save & Preview']").click();
             System.out.println("preview button clicked ");
             
             
             

            } catch (Exception e) {
                test.fail("❌ Error in row " + i + ": " + e.getMessage());
                throw e;
            }
        } 
    }
} 
