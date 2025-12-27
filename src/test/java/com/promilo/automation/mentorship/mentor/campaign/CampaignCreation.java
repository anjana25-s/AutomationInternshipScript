package com.promilo.automation.mentorship.mentor.campaign;

import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipFormComponents;
import com.promilo.automation.mentorship.mentor.*;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.*;
import com.promilo.automation.resources.*;

public class CampaignCreation extends BaseClass {

    private static final Logger log = LogManager.getLogger(CampaignCreation.class);
    private static String registeredEmail = null;
    private static String registeredPassword = null;

    // -------------------------
    // Signup once for suite
    // -------------------------
    @BeforeSuite
    public void performSignupOnce() throws Exception {
        System.out.println("⚙️ Performing signup ONCE before suite using Mailosaur...");
        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();
        registeredEmail = creds[0];
        registeredPassword = creds[1];
        System.out.println("✅ Signup completed. Registered user: " + registeredEmail);
    }

    // ----------------------------------------------------------
    // Main test - now fully data-driven (reads from Excel)
    // ----------------------------------------------------------
    @Test
    public void addEmploymentPositiveTest() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("✅ Add Employment - Data Driven");

        if (registeredEmail == null || registeredPassword == null) {
            test.fail("❌ Signup credentials not found. Aborting test.");
            return;
        }

        // Browser setup
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        test.info("🌐 Navigated to application URL.");

        // -------------------------
        // Excel setup (user-specified path)
        // -------------------------
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "CampaignCreation");

        // map columns once for readability
        final int colTestCaseId = excel.getColumnIndex("TestCaseID");
        final int colKeyword = excel.getColumnIndex("Keyword");
        final int colFirstName = excel.getColumnIndex("FirstName");
        final int colLastName = excel.getColumnIndex("LastName");
        final int colPhone = excel.getColumnIndex("Phone");
        final int colOTP = excel.getColumnIndex("OTP");
        final int colLocation = excel.getColumnIndex("Location");
        final int colGender = excel.getColumnIndex("Gender");
        final int colExperience = excel.getColumnIndex("Experience");
        final int colMentorType = excel.getColumnIndex("MentorType");
        final int colDomain = excel.getColumnIndex("Domain");
        final int colCategory = excel.getColumnIndex("Category");
        final int colCourse = excel.getColumnIndex("Course");
        final int colDOB = excel.getColumnIndex("DOB");
        final int colSpecialization = excel.getColumnIndex("Specialization");
        final int colImagePath = excel.getColumnIndex("ImagePath");
        final int colSocialLink = excel.getColumnIndex("SocialLink");
        final int colHighlight = excel.getColumnIndex("Highlight");
        final int colCampaignName = excel.getColumnIndex("CampaignName");
        final int colStartDate = excel.getColumnIndex("StartDate");
        final int colEndDate = excel.getColumnIndex("EndDate");
        final int colSkill = excel.getColumnIndex("Skill");
        final int colAboutMe = excel.getColumnIndex("AboutMe");
        final int colIndustry = excel.getColumnIndex("Industry");
        final int colFunctionalArea = excel.getColumnIndex("FunctionalArea");
        final int colRelevantTitle = excel.getColumnIndex("RelevantTitle");
        final int colAudienceLocation = excel.getColumnIndex("AudienceLocation");
        final int colKeywordText = excel.getColumnIndex("KeywordText");
        final int colAgeFrom = excel.getColumnIndex("AgeFrom");
        final int colAgeTo = excel.getColumnIndex("AgeTo");
        final int colService1Name = excel.getColumnIndex("Service1Name");
        final int colService1Fee = excel.getColumnIndex("Service1Fee");
        final int colService2Name = excel.getColumnIndex("Service2Name");
        final int colService2Fee = excel.getColumnIndex("Service2Fee");
        final int colService3Name = excel.getColumnIndex("Service3Name");
        final int colService3Fee = excel.getColumnIndex("Service3Fee");
        final int colService4Name = excel.getColumnIndex("Service4Name");
        final int colService4Fee = excel.getColumnIndex("Service4Fee");
        final int colService5Name = excel.getColumnIndex("Service5Name");
        final int colService5Fee = excel.getColumnIndex("Service5Fee");
        final int colService6Name = excel.getColumnIndex("Service6Name");
        final int colService6Fee = excel.getColumnIndex("Service6Fee");
        final int colInvoiceName = excel.getColumnIndex("InvoiceName");
        final int colStreet1 = excel.getColumnIndex("Street1");
        final int colStreet2 = excel.getColumnIndex("Street2");
        final int colPincode = excel.getColumnIndex("Pincode");
        final int colGSTNumber = excel.getColumnIndex("GSTNumber");
        final int colPANNumber = excel.getColumnIndex("PANNumber");
        final int colAccountHolder = excel.getColumnIndex("AccountHolder");
        final int colAccountNumber = excel.getColumnIndex("AccountNumber");
        final int colBranchName = excel.getColumnIndex("BranchName");
        final int colIFSCCode = excel.getColumnIndex("IFSCCode");
        final int colBankPAN = excel.getColumnIndex("BankPAN");
        final int colCountry = excel.getColumnIndex("Country");

        int lastRow = excel.getRowCount(); // last row index
        test.info("📘 Excel rows available: " + lastRow);

        // iterate rows (assumes header at row 0)
        for (int i = 1; i <= lastRow; i++) {
            String keyword = excel.getCellData(i, colKeyword);
            if (keyword == null || !keyword.equalsIgnoreCase("CampaignCreation")) continue;

            try {
                test.info("➡️ Executing row " + i + " (TestCaseID=" + excel.getCellData(i, colTestCaseId) + ")");

                // --------- Read data for this row -----------
                String firstName = excel.getCellData(i, colFirstName);
                String lastName = excel.getCellData(i, colLastName);
                String phone = excel.getCellData(i, colPhone);
                String otp = excel.getCellData(i, colOTP);
                String location = excel.getCellData(i, colLocation);
                String gender = excel.getCellData(i, colGender);
                String experience = excel.getCellData(i, colExperience);
                String mentorType = excel.getCellData(i, colMentorType);
                String domain = excel.getCellData(i, colDomain);
                String category = excel.getCellData(i, colCategory);
                String course = excel.getCellData(i, colCourse);
                String dob = excel.getCellData(i, colDOB);
                String specialization = excel.getCellData(i, colSpecialization);
                String imagePath = excel.getCellData(i, colImagePath);
                String socialLink = excel.getCellData(i, colSocialLink);
                String highlight = excel.getCellData(i, colHighlight);
                String campaignName = excel.getCellData(i, colCampaignName);
                String startDate = excel.getCellData(i, colStartDate);
                String endDate = excel.getCellData(i, colEndDate);
                String skill = excel.getCellData(i, colSkill);
                String aboutMeText = excel.getCellData(i, colAboutMe);
                String industry = excel.getCellData(i, colIndustry);
                String functionalArea = excel.getCellData(i, colFunctionalArea);
                String relevantTitle = excel.getCellData(i, colRelevantTitle);
                String audienceLocation = excel.getCellData(i, colAudienceLocation);
                String keywordText = excel.getCellData(i, colKeywordText);
                String ageFrom = excel.getCellData(i, colAgeFrom);
                String ageTo = excel.getCellData(i, colAgeTo);
                String service1Name = excel.getCellData(i, colService1Name);
                String service1Fee = excel.getCellData(i, colService1Fee);
                String service2Name = excel.getCellData(i, colService2Name);
                String service2Fee = excel.getCellData(i, colService2Fee);
                String service3Name = excel.getCellData(i, colService3Name);
                String service3Fee = excel.getCellData(i, colService3Fee);
                String service4Name = excel.getCellData(i, colService4Name);
                String service4Fee = excel.getCellData(i, colService4Fee);
                String service5Name = excel.getCellData(i, colService5Name);
                String service5Fee = excel.getCellData(i, colService5Fee);
                String service6Name = excel.getCellData(i, colService6Name);
                String service6Fee = excel.getCellData(i, colService6Fee);
                String invoiceName = excel.getCellData(i, colInvoiceName);
                String street1 = excel.getCellData(i, colStreet1);
                String street2 = excel.getCellData(i, colStreet2);
                String pincode = excel.getCellData(i, colPincode);
                String gstNumber = excel.getCellData(i, colGSTNumber);
                String panNumber = excel.getCellData(i, colPANNumber);
                String accountHolder = excel.getCellData(i, colAccountHolder);
                String accountNumber = excel.getCellData(i, colAccountNumber);
                String branchName = excel.getCellData(i, colBranchName);
                String ifscCode = excel.getCellData(i, colIFSCCode);
                String bankPan = excel.getCellData(i, colBankPAN);
                String country = excel.getCellData(i, colCountry);

                // --------- Login Flow -----------
                MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
                try {
                    mayBeLaterPopUp.getPopup().click();
                    test.info("✅ Popup closed.");
                } catch (Exception ignored) {
                    test.info("ℹ️ No popup found.");
                }

                mayBeLaterPopUp.clickLoginButton();
                test.info("🔑 Navigating to Login Page.");

                LoginPage loginPage = new LoginPage(page);
                loginPage.loginMailPhone().fill("happily-importance@qtvjnqv9.mailosaur.net");
                loginPage.passwordField().fill(registeredPassword);
                loginPage.loginButton().click();
                test.info("✅ Logged in with: " + registeredEmail);

                // --------- Navigate to mentorship creation -----------
                Hamburger myStuff = new Hamburger(page);
                myStuff.Mystuff().click();

                BecomeMentor becomeMentor = new BecomeMentor(page);
                becomeMentor.becomeMentorButton().click();	
                becomeMentor.createMentorshipSession().click();

                // --------- Create Profile (data-driven) -----------
                CreateProfile profile = new CreateProfile(page);
                profile.firstName().fill(firstName);
                profile.lastName().fill(lastName);

                // Phone: if blank in excel, generate dynamic; otherwise use excel value
                String mobileToUse = (phone == null || phone.trim().isEmpty())
                        ? "90000" + String.format("%05d", new Random().nextInt(100000))
                        : phone;
                profile.mobileTextfield().fill(mobileToUse);
                log.info("📱 Entered phone: " + mobileToUse);

                // OTP
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Send OTP")).click();
                String otpToUse = (otp == null || otp.trim().isEmpty()) ? "9999" : otp;
                page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("mobile_otp")).fill(otpToUse);
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Verify OTP")).click();

                // Location
                if (location != null && !location.isEmpty()) {
                    profile.locationDropdown().first().click();
                    page.keyboard().type(location);
                    page.keyboard().press("Enter");
                }

                // Gender
                if (gender != null && !gender.isEmpty()) {
                    profile.genderDropdown().first().click();
                    page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(gender).setExact(true)).click();
                }

                // Experience
                if (experience != null && !experience.isEmpty()) {
                    profile.experianceDropdwon().first().click();
                    page.keyboard().type(experience);
                    page.keyboard().press("Enter");
                }

                // Mentor Type
                if (mentorType != null && !mentorType.isEmpty()) {
                    profile.typeofMentor().click();
                    page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(mentorType)).click();
                }

                // Domain
                if (domain != null && !domain.isEmpty()) {
                    profile.domain().click();
                    page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(domain)).click();
                }

                page.waitForTimeout(3000);
                // Category
                if (category != null && !category.isEmpty()) {
                    profile.categoryDropdown().click();
                    page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(category)).locator("span").click();
                }

                // Course
                if (course != null && !course.isEmpty()) {
                    profile.coursesDropdown().click();
                    page.getByText(course).first().click();
                }

                // DOB
                if (dob != null && !dob.isEmpty()) {
                    profile.dateOfBirth().first().fill(dob);
                }

                // Specialization (if provided)
                if (specialization != null && !specialization.isEmpty()) {
                    profile.specialization().click();
                    // if specialization is a direct visible text, try to type it; otherwise fallback to the existing click-first behavior
                    try {
                        page.keyboard().type(specialization);
                        page.keyboard().press("Enter");
                    } catch (Exception e) {
                        page.locator("li > .select-item > .item-renderer > span").first().click();
                    }
                }

                // Upload picture
                if (imagePath != null && !imagePath.isEmpty()) {
                    profile.uploadCampaignImage().setInputFiles(Paths.get(imagePath));
                    profile.cropButton().click();
                }

                // Social media
                if (socialLink != null && !socialLink.isEmpty()) {
                    profile.socialmediaLinks().click();
                    profile.instagramOption().click();
                    profile.pasteLinks().fill(socialLink);
                }

                // Highlights
                if (highlight != null && !highlight.isEmpty()) {
                    profile.highlightTextfield().fill(highlight);
                }

                profile.saveButton().click();
                
                

                //Assertion for the toaster
                page.waitForTimeout(3000);
                Assert.assertTrue(page.locator("//div[text()='Profile Updated Successfully']").isVisible(),
                        "❌ Profile update toaster missing!");

                // --------- Campaign Details -----------
                CampaignDetails campaign = new CampaignDetails(page);
                if (campaignName != null) campaign.campaignName().fill("Menotrship Campaign for automation");
                if (startDate != null) campaign.startDate().fill(startDate);
                if (endDate != null) {
                    Locator endDateField = campaign.endDate();
                    endDateField.scrollIntoViewIfNeeded();
                    endDateField.fill(endDate);
                }
                if (skill != null && !skill.isEmpty()) {
                    campaign.clickheretoAddskills().click();
                    campaign.skills().fill(skill);
                    page.keyboard().press("Enter");
                }
                campaign.saveButton().nth(1).click();

                // --------- About Me -----------
                AboutMePage aboutMe = new AboutMePage(page);
                if (aboutMeText != null && !aboutMeText.isEmpty()) {
                    aboutMe.textArea().fill(aboutMeText);
                } else {
                    // fallback filler if AboutMe empty
                    aboutMe.textArea().fill("About me data from excel not provided.");
                }

                aboutMe.addTittle().click();
                aboutMe.focus().click();
                aboutMe.fluentIn().click();
                aboutMe.addButton().click();

                // fill multiple about-me sections as original flow did
                aboutMe.aboutmeOptions().nth(1).click();
                aboutMe.textArea().fill(aboutMeText == null ? "" : aboutMeText);
                aboutMe.aboutmeOptions().nth(2).click();
                aboutMe.textArea().fill(aboutMeText == null ? "" : aboutMeText);
                aboutMe.saveButton().nth(2).click();

                //Assertion for success toaster
                page.waitForTimeout(3000);
                Assert.assertEquals(page.locator("//div[text()='Successfully Saved']").textContent().trim(),
                        "Successfully Saved", "❌ AboutMe toaster mismatch!");

                // --------- My Audience -----------
                myAudiencePage audience = new myAudiencePage(page);
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


                // Age filters
                if (ageFrom != null && !ageFrom.isEmpty()) {
                    Locator minAge=page.locator(".form-floating.override-mb-0 > .react-select-container > .react-select__control > .react-select__value-container > .react-select__input-container").first();
                    minAge.scrollIntoViewIfNeeded();
                    minAge.click();
                    page.keyboard().type(ageFrom);
                    page.keyboard().press("Enter");
                }

                if (ageTo != null && !ageTo.isEmpty()) {
                    Locator maxAge= page.locator("div:nth-child(7) > .form-floating > .react-select-container > .react-select__control > .react-select__value-container > .react-select__input-container");
                    maxAge.scrollIntoViewIfNeeded();
                    maxAge.click();
                    page.keyboard().type(ageTo);
                    page.keyboard().press("Enter");
                }

                audience.Savebutton().click();

                //Assertion for the success toaster
                page.waitForTimeout(3000);
                Assert.assertEquals(page.locator("//div[text()='Successfully Saved']").textContent().trim(),
                        "Successfully Saved", "❌ Audience save mismatch!");

                // --------- Services Section (data-driven) -----------
                ServicesPage services = new ServicesPage(page);

                // Service 1
                if (service1Name != null && !service1Name.isEmpty()) {
                    services.oneonecall().click();
                    services.serviceName().fill(service1Name);
                    page.locator(".rmsc.service-react-multi-select .dropdown-heading").click();
                    page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("English")).click();
                    if (service1Fee != null && !service1Fee.isEmpty()) services.serviceFee().fill(service1Fee);
                    services.evaluate().click();
                    services.addService().click();
                    Assert.assertEquals(page.locator("//div[text()='Service added sucessfully']").textContent().trim(),
                            "Service added sucessfully", "❌ 1:1 Call toaster mismatch!");
                }

                // Service 2 - Video Call
                if (service2Name != null && !service2Name.isEmpty()) {
                    services.videoCall().click();
                    services.serviceName().fill(service2Name);
                    page.locator(".rmsc.service-react-multi-select .dropdown-heading").click();
                    page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("English")).click();
                    
                    Locator inputContainer = page.locator("//div[@class='react-select__value-container css-hlgwow']//div[@class='react-select__input-container css-19bb58m']");
                    inputContainer.nth(1).click();
                    System.out.println("slot selected");
                    page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("15 mins")).locator("div").click();
                    

                    services.calenderButton().click();
                    services.Monday().click();

                    LocalTime time = LocalTime.of(0, 0);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

                 // Loop to create 10 slots (each 15 minutes apart)
                    for (int slot = 1; slot <= 10; slot++) {
                        // Click the plus icon to add a new slot
                    	services.plusIcon().first().click();
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
                    services.submitButton().click();  
                    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Service Fee")).fill(service2Fee);
                    services.evaluate().click();
                    services.addService().click();
                    
                    
                    String videoServiceToaster = page.locator("//div[text()='Service added sucessfully']").first().textContent().trim();
                    Assert.assertEquals(videoServiceToaster, "Service added sucessfully", "❌ Toaster message mismatch!");

                // Service 3 - Ask Query
                    services.askQuery().click();
                    services.serviceName().fill("Ask query");
                    services.serviceFee().fill("700");
                    services.evaluate().click();
                    
                    page.waitForTimeout(3000);
                    page.locator("#reply_duration > .react-select__control > .react-select__value-container > .react-select__input-container").click();
                    page.keyboard().type("2");
                    page.keyboard().press("Enter");
                    services.addService().click();
                    System.out.println(services.serviceCard().textContent()); 
                    
                    String askQueryToast = page.locator("//div[text()='Service added sucessfully']").first().textContent().trim();
                    Assert.assertEquals(askQueryToast, "Service added sucessfully", "❌ Toaster message mismatch!");
                }

                // Service 4 - Resources (with file)
                if (service4Name != null && !service4Name.isEmpty()) {
                    services.resources().click();
                    services.serviceName().fill(service4Name);
                    if (service4Fee != null && !service4Fee.isEmpty()) services.serviceFee().fill(service4Fee);
                    services.evaluate().click();
                    // reuse image file if imagePath provided
                    if (imagePath != null && !imagePath.isEmpty()) {
                        services.uploadFile().setInputFiles(Paths.get(imagePath));
                    }
                    services.addService().click();
                    Assert.assertEquals(page.locator("//div[text()='Service added sucessfully']").first().textContent().trim(),
                            "Service added sucessfully", "❌ Resources toaster mismatch!");
                }

                // Service 5 - Brand Endorsement
                if (service5Name != null && !service5Name.isEmpty()) {
                    services.brandEndoursement().click();
                    services.serviceName().fill(service5Name);
                    services.addService().click();
                    
                    page.waitForTimeout(3000);
                    Assert.assertEquals(page.locator("//div[text()='Service added sucessfully']").first().textContent().trim(),
                            "Service added sucessfully", "❌ Brand Endorsement toaster mismatch!");
                }

                // Service 6 - Personalized Video
                if (service6Name != null && !service6Name.isEmpty()) {
                    services.personalizedVideo().click();
                    services.serviceName().fill(service6Name);
                    if (service6Fee != null && !service6Fee.isEmpty()) services.serviceFee().fill(service6Fee);
                    services.evaluate().click();

                    services.videoDuration().click();
                    if (excel.getCellData(i, colService6Fee) != null) {
                        page.keyboard().type("5"); // fallback value; if you want to drive duration too, add column
                        page.keyboard().press("Enter");
                    }
                    services.deliveryTime().click();
                    page.keyboard().type("3");
                    page.keyboard().press("Enter");

                    services.addService().click();
                    
                    Assert.assertEquals(page.locator("//div[text()='Service added sucessfully']").first().textContent().trim(),
                            "Service added sucessfully", "❌ Brand Endorsement toaster mismatch!");
                }
                
                page.waitForTimeout(1000);

                // verify all service labels exist (original constants)
                String[] servicesValidation = {
                        "1:1 Call", "Video Call", "Ask Query",
                        "Resources", "Brand Endorsement", "Personalized Video"
                };
                for (String s : servicesValidation) {
                    Assert.assertTrue(page.locator("//p[text()='" + s + "']").isVisible(),
                            "❌ Service not visible: " + s);
                }

                services.saveAndNextButton().nth(2).click();

                // --------- Budget & Invoice -----------
                BudgetAndCost budget = new BudgetAndCost(page);
                budget.addInvoiceinfo().click();

                MentorshipFormComponents invoice = new MentorshipFormComponents(page);
                if (invoiceName != null) invoice.InvoiceNameField().fill(invoiceName);
                if (street1 != null) invoice.StreetAdress1().fill(street1);
                if (street2 != null) invoice.StreetAdress2().fill(street2);
                if (pincode != null) invoice.pinCode().fill(pincode);
                invoice.yesRadrioBox().click();
                if (gstNumber != null) invoice.gstNumber().fill(gstNumber);
                if (panNumber != null) invoice.panNumber().fill(panNumber);
                page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("By checking this box, I")).check();

                page.locator("//button[normalize-space()='Save']").click();
                Assert.assertEquals(page.locator("//div[text()='Updated successfully']").textContent().trim(),
                        "Updated successfully", "❌ Invoice save mismatch!");

                // Bank details
                budget.addbankAccount().click();
                if (accountHolder != null) budget.accountHoldername().fill(accountHolder);
                if (accountNumber != null) budget.accountNumber().fill(accountNumber);
                if (branchName != null) budget.branchName().fill(branchName);
                if (ifscCode != null) budget.ifscCode().fill(ifscCode);
                if (bankPan != null) budget.panNumber().fill(bankPan);
                budget.billingCountry().nth(9).click();
                page.keyboard().type(country == null ? "ind" : country.substring(0, Math.min(3, country.length())));
                page.keyboard().press("Enter");

                page.locator("//button[normalize-space()='Save']").click();
                Assert.assertEquals(page.locator("//div[text()='Successfully Saved']").textContent().trim(),
                        "Successfully Saved", "❌ Bank details mismatch!");

                budget.emailCheckbox().click();
                budget.smsCheckbox().click();
                budget.notificationCheckboc().click();
                budget.whatsappCheckbox().click();

                // --------- Save & Preview -----------
                page.locator("//button[text()='Save & Preview']").click();
                Locator heading = page.locator("//div[@class='d-flex font-28 mb-0 pointer box-fit-content mentorship-heading']");
                
                page.waitForTimeout(4000);
                String uiHeading = heading.textContent().trim();

                String fNameUi = profile.firstName().inputValue().trim();
                String lNameUi = profile.lastName().inputValue().trim();
                String expectedName = fNameUi + " " + lNameUi;

                Assert.assertTrue(uiHeading.toLowerCase().contains(expectedName.toLowerCase()),
                        "❌ Name mismatch on preview!");

                
                // Publish
                page.waitForTimeout(3000);
                page.locator("//span[text()='Publish']").click();
                page.getByRole(AriaRole.DIALOG).getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Publish")).click();
                page.waitForTimeout(3000);

                page.waitForTimeout(5000);

                Assert.assertTrue(page.locator("(//h4[normalize-space()=\"We'll notify you as soon as it goes live!\"])[1]").isVisible(),
                        "❌ Publish confirmation not displayed!");
                

                }
                
                
                
                
                
             catch (Exception e) {
                test.fail("❌ Error in row " + i + ": " + e.getMessage());
                throw e; // rethrow for test framework visibility
            }
        } // end rows loop
  
    }}
