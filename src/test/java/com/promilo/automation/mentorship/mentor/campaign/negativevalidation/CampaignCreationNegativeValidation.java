package com.promilo.automation.mentorship.mentor.campaign.negativevalidation;

import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipFormComponents;
import com.promilo.automation.mentorship.mentor.AboutMePage;
import com.promilo.automation.mentorship.mentor.BecomeMentor;
import com.promilo.automation.mentorship.mentor.CampaignDetails;
import com.promilo.automation.mentorship.mentor.CreateProfile;
import com.promilo.automation.mentorship.mentor.ServicesPage;
import com.promilo.automation.mentorship.mentor.myAudiencePage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.SignupWithMailosaurUI;


public class CampaignCreationNegativeValidation extends BaseClass {

    private static final Logger log = LogManager.getLogger(CampaignCreationNegativeValidation.class);

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    // Reused across tests so the browser session persists
    private static Page page;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        System.out.println("⚙️ Performing signup ONCE before suite using Mailosaur...");

        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();

        registeredEmail = creds[0];
        registeredPassword = creds[1];

        System.out.println("✅ Signup completed. Registered user: " + registeredEmail);
    }

    @BeforeClass
    public void setupAndLogin() throws Exception {
        // initialize playwright and login once, reuse page across tests
        page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1080, 720);

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception e) {}

        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(registeredEmail);
        loginPage.passwordField().fill(registeredPassword);
        loginPage.loginButton().click();

        // Navigate
        Hamburger myStuff = new Hamburger(page);
        myStuff.Mystuff().click();

        BecomeMentor becomeMentor = new BecomeMentor(page);
        becomeMentor.becomeMentorButton().click();
        becomeMentor.createMentorshipSession().click();

    }

    /**
     * Test 1: Profile validations (everything related to CreateProfile and OTP)
     */
    @Test
    public void campaignProfileNegativeValidation() throws Exception {

        CreateProfile profile = new CreateProfile(page);

        // 0) save without any input
        profile.saveButton().click();
        page.waitForTimeout(1200);

        List<String> allErrors1 = page.locator("[class='text-danger']").allTextContents();

        for(String msg : allErrors1) {

            System.out.println("❌ Error: " + msg);

            // assertion
            if(msg.contains("First Name is required") ||
               msg.contains("Last Name is required") ||
               msg.contains("Mobile number is required") ||
               msg.contains("Location is required") ||
               msg.contains("Gender is required") ||
               msg.contains("Date of Birth is required") ||
               msg.contains("Experience is required") ||
               msg.contains("Type of mentor is required") ||
               msg.contains("Domain is required") ||
               msg.contains("Highlight is required") ||
               msg.contains("Image is required")) {

                org.testng.Assert.assertTrue(true);
            } else {
                org.testng.Assert.fail("Unexpected error text found: " + msg);
            }
        }

        // 1a) less than minimum length
        profile.firstName().fill("aa");
        profile.firstName().blur();
        String err1 = page.locator("[class='text-danger']").first().innerText();
        System.out.println("Error for 'aa': " + err1);
        org.testng.Assert.assertTrue(err1.contains("at least 3 characters"), "❌ Expected min length validation not shown");

        // 1b) clear the input for the next input value
        profile.firstName().fill("");
        profile.firstName().blur();

        // 1c) invalid numeric mix
        profile.firstName().fill("aa123");
        profile.firstName().blur();
        String err3 = page.locator("[class='text-danger']").first().innerText();
        System.out.println("Error for 'aa123': " + err3);
        org.testng.Assert.assertTrue(err3.contains("Only alphabets"), "❌ Expected alphabets only validation not shown");

        // finally set correct valid data for further steps
        profile.firstName().fill("karthik");
        profile.firstName().blur();

        // 2) lastname

        // invalid numeric only
        profile.lastName().fill("123");
        profile.lastName().blur();
        String err4 = page.locator("[class='text-danger']").first().innerText();
        System.out.println("Error for '123': " + err4);
        org.testng.Assert.assertTrue(err4.contains("Only alphabets are allowed"), "❌ Expected alphabets only validation not shown for '123'");

        // invalid space at end
        profile.lastName().fill("aaaa ");
        profile.lastName().blur();
        String err5 = page.locator("[class='text-danger']").first().innerText();
        System.out.println("Error for 'aaaa ': " + err5);
        org.testng.Assert.assertTrue(err5.contains("should not start or end with a space"), "❌ Expected trimming validation not shown for last name");

        // finally valid lastname for continuation
        profile.lastName().fill("U");
        profile.lastName().blur();

        // 3) Phone validation
        String dynamicPhone = "90000" + String.format("%05d", (int)(Math.random() * 100000));
        // invalid - alphabets inside
        profile.mobileTextfield().fill("abc90000");
        profile.mobileTextfield().blur();
        String phErr1 = page.locator("[class='text-danger']").first().innerText();
        System.out.println("Error for 'abc90000': " + phErr1);
        org.testng.Assert.assertEquals(phErr1.trim(), "Mobile number must be exactly 10 digits",
                "❌ Validation message mismatch for 'abc90000'");

        // invalid - less digits
        profile.mobileTextfield().fill("90000");
        profile.mobileTextfield().blur();
        String phErr2 = page.locator("[class='text-danger']").first().innerText();
        System.out.println("Error for '90000': " + phErr2);
        org.testng.Assert.assertEquals(phErr2.trim(), "Mobile number must be exactly 10 digits",
                "❌ Validation message mismatch for '90000'");

        // valid dynamic phone
        profile.mobileTextfield().fill(dynamicPhone);
        profile.mobileTextfield().blur();   // trigger validation

        // OTP NEGATIVE validation

        // click send OTP first
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Send OTP")).click();

        Locator otpField = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("mobile_otp"));

        // alphabets
        otpField.fill("abcd");
        otpField.blur();
        String otpErr1 = page.locator("[class='text-danger']").first().innerText();
        System.out.println("Error for 'abcd': " + otpErr1);
        org.testng.Assert.assertEquals(otpErr1.trim(), "OTP must contain only digits",
                "❌ OTP validation mismatch for alpha input");

        // invalid alpha numeric
        otpField.fill("12ab");
        otpField.blur();
        String otpErr2 = page.locator("[class='text-danger']").first().innerText();
        System.out.println("Error for '12ab': " + otpErr2);
        org.testng.Assert.assertEquals(otpErr2.trim(), "OTP must contain only digits",
                "❌ OTP validation mismatch for alphanumeric input");

        // invalid  digits
        otpField.fill("1234");  
        otpField.blur();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Verify OTP")).click();
        
        MentorshipErrorMessagesAndToasters err = new MentorshipErrorMessagesAndToasters(page);
        String actualToaster = err.invalidOtp().textContent().trim();
        Assert.assertTrue(actualToaster.contains("Invalid OTP"), "❌ Invalid OTP toaster not displayed");

        
        // valid otp final
        otpField.fill("9999");
        otpField.blur();


        page.waitForTimeout(1500);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Verify OTP")).click();
        page.waitForTimeout(1200);

        page.waitForTimeout(1200);

        // 4) location
        profile.locationDropdown().first().click();
        page.keyboard().type("Ananta");
        page.keyboard().press("Enter");

        // 5) gender
        profile.genderDropdown().first().click();
        page.getByText("Male").first().click();
        page.waitForTimeout(1200);

        // 6) experience
        profile.experianceDropdwon().first().click();
        page.keyboard().type("2");
        page.keyboard().press("Enter");
        page.waitForTimeout(1200);

        // 7) mentor type
        profile.typeofMentor().click();
        page.getByText("Academic Guidance").click();
        page.waitForTimeout(1200);

        // 8) domain
        profile.domain().click();
        page.getByText("Course Selection").click();
        page.waitForTimeout(1200);

        // 9) category
        profile.categoryDropdown().click();
        page.getByText("Engineering").first().click();
        page.waitForTimeout(1200);

        // 10) course
        profile.coursesDropdown().click();
        page.getByText("BE/B.Tech").first().click();
        page.waitForTimeout(1200);

        // 11) DOB
        profile.dateOfBirth().first().fill("2002-08-24");
        page.waitForTimeout(1200);

        // 12) specialization
        profile.specialization().click();
        page.locator("li > .select-item > .item-renderer > span").first().click();
        page.waitForTimeout(1200);

        // 13) upload image
        profile.uploadCampaignImage().setInputFiles(Paths.get("C:/Users/Admin/Downloads/pexels-moh-adbelghaffar-771742.jpg"));
        profile.cropButton().click();
        page.waitForTimeout(1200);

        // 14) highlight
        profile.highlightTextfield().fill("dxgfchvjbng vbnm");
        page.waitForTimeout(2000);

        //Assertion for success toaster
        profile.saveButton().first().click();
        Locator toast = page.locator("//div[text()='Profile Updated Successfully']");
        toast.scrollIntoViewIfNeeded();
        String toasterMsg = toast.textContent().trim();
        Assert.assertTrue(toasterMsg.contains("Profile Updated Successfully"), "❌ Toaster message mismatch!");

    }

    /**
     * Test 2: Campaign Details validations
     */
    @Test(dependsOnMethods = {"campaignProfileNegativeValidation"})
    public void campaignDetailsNegativeValidation() throws Exception {

        CampaignDetails camapaignDetails= new CampaignDetails(page);

        //clicking on save button without entering the mondatory data
        camapaignDetails.saveButton().nth(1).click();
        System.out.println("save button clicked");

        List<String> camapaignDetailsErrors = page.locator("[class='text-danger']").allTextContents();

        for(String msg : camapaignDetailsErrors) {
            System.out.println("❌ Error: " + msg);
        }

        //  assertions
        org.testng.Assert.assertTrue(
                camapaignDetailsErrors.stream().anyMatch(e -> e.contains("Campaign Name is required")),
                "❌ Campaign Name required text not found");

        org.testng.Assert.assertTrue(
                camapaignDetailsErrors.stream().anyMatch(e -> e.contains("Start Date is required")),
                "❌ Start Date required text not found");

        org.testng.Assert.assertTrue(
                camapaignDetailsErrors.stream().anyMatch(e -> e.contains("End Date is required")),
                "❌ End Date required text not found");

        
        
     
     // ===================== CAMPAIGN NAME NEGATIVE CASES ======================
        camapaignDetails.campaignName().fill("ab");
        camapaignDetails.campaignName().blur();
        String cnameErr1 = page.locator("[class='text-danger']").first().innerText();
        org.testng.Assert.assertEquals(cnameErr1.trim(), "Campaign Name must be at least 3 characters",
                "❌ Campaign name validation mismatch for alpha numeric");
        

         // ===================== POSITIVE VALID CAMPAIGN NAME ======================
        camapaignDetails.campaignName().fill("october Automation");
        camapaignDetails.campaignName().blur();
        System.out.println("✅ Valid Campaign Name Filled");


        // ===================== DATE VALIDATION ======================
        camapaignDetails.startDate().fill("2026-10-24");
        page.waitForTimeout(500);
        camapaignDetails.endDate().fill("2025-10-06");
        page.waitForTimeout(500);

        String dateErr = page.locator("[class='text-danger']").first().innerText();
        org.testng.Assert.assertEquals(dateErr.trim(), "End Date should be greater than Start Date",
                "❌ Campaign name validation mismatch for alpha numeric");

        // Positive (valid date)
        camapaignDetails.startDate().fill("2025-10-09");
        page.waitForTimeout(500);
        camapaignDetails.endDate().fill("2026-10-01");
        page.waitForTimeout(500);
        
        camapaignDetails.clickheretoAddskills().click();
        camapaignDetails.skills().fill("automation");
        page.keyboard().press("Enter");
        page.waitForTimeout(2000);
        Locator saveButton= camapaignDetails.saveButton().nth(1);
        saveButton.scrollIntoViewIfNeeded();
        saveButton.click();

    }

    /**
     * Test 3: About Me validations and save
     */
    @Test(dependsOnMethods = {"campaignDetailsNegativeValidation"})
    public void aboutMeNegativeValidation() throws Exception {

        AboutMePage aboutMe= new AboutMePage(page);
        //clicking on save button without providing the description
        aboutMe.saveButton().nth(2).click();
        String toastMsg = page.locator("//div[text()='Description is missing for Biography']").textContent().trim();
        Assert.assertEquals(toastMsg, "Description is missing for Biography", "❌ Toaster message mismatch!");

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

        aboutMe.saveButton().nth(2).click();
        
        String toastMsg2 = page.locator("//div[text()='Successfully Saved']").textContent().trim();
        Assert.assertEquals(toastMsg2, "Successfully Saved", "❌ Toaster message mismatch!");

    }

    /**
     * Test 4: My Audience validations
     */
    @Test(dependsOnMethods = {"aboutMeNegativeValidation"})
    public void myAudienceNegativeValidation() throws Exception {

        myAudiencePage audience= new myAudiencePage(page);

        // click save without entering anything
        audience.Savebutton().click();

        // print all validation errors
        List<String> audienceErrors = page.locator("//span[@style='color: red;']").allTextContents();
        for(String msg : audienceErrors) {
            System.out.println("❌ Error: " + msg);
        }
        System.out.println("=====================================");

     // ASSERT REQUIRED FIELD MESSAGES
        Assert.assertTrue(audienceErrors.contains("Audience is required"), "❌ Missing: Audience is required");
        Assert.assertTrue(audienceErrors.contains("Functional Area is required"), "❌ Missing: Functional Area is required");
        Assert.assertTrue(audienceErrors.contains("Relevant Title is required"), "❌ Missing: Relevant Title is required");
        Assert.assertTrue(audienceErrors.contains("Location is required"), "❌ Missing: Location is required");
        Assert.assertTrue(audienceErrors.contains("Keyword is required"), "❌ Missing: Keyword is required");
        Assert.assertTrue(audienceErrors.contains("Min Age is required"), "❌ Missing: Min Age is required");
        Assert.assertTrue(audienceErrors.contains("Max Age is required"), "❌ Missing: Max Age is required");

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
        Locator minAge2=page.locator(".form-floating.override-mb-0 > .react-select-container > .react-select__control > .react-select__value-container > .react-select__input-container").first();
        minAge2.scrollIntoViewIfNeeded();
        minAge2.click();
        page.keyboard().type("30");
        page.keyboard().press("Enter");
        
        
        //max Age
        page.locator("div:nth-child(7) > .form-floating > .react-select-container > .react-select__control > .react-select__value-container > .react-select__input-container").click();
        page.keyboard().type("20");
        page.keyboard().press("Enter");
        audience.Savebutton().click();
        
        String ageToaster = page.locator("//span[text()='Minimum age should not be greater than maximum age.']").textContent().trim();
        Assert.assertEquals(ageToaster, "Minimum age should not be greater than maximum age.", "❌ Toaster message mismatch!");

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
        
        
        String toastText = page.locator("//div[text()='Successfully Saved']").textContent().trim();
        Assert.assertEquals(toastText, "Successfully Saved", "❌ Toaster text mismatch!");

    }
    
    

    /**
     * Test 5: Services, Budget & Cost, Invoice, Publish flow
     */
    @Test(dependsOnMethods = {"myAudienceNegativeValidation"})
    public void servicesBudgetAndPublishValidation() throws Exception {

        // Services 
        ServicesPage services= new ServicesPage(page);
        
        //click on add service without adding the details
        services.addService().click();
        
        List<String> oneonecallError = page.locator(".text-danger").allTextContents();

       
     // Assertions
       Assert.assertTrue(oneonecallError.contains("Service Name is required"),  "❌ Missing: Service Name is required");
       Assert.assertTrue(oneonecallError.contains("Language is required"),      "❌ Missing: Language is required");
       Assert.assertTrue(oneonecallError.contains("Service Fee is required"),   "❌ Missing: Service Fee is required");

            
          //one:one call
            services.oneonecall().click();
            services.serviceName().fill("aa");
            services.serviceName().blur();
            
            String toastTextservice1 = page.locator("//span[text()='Service Name must be at least 3 characters']").textContent().trim();
             Assert.assertEquals(toastTextservice1, "Service Name must be at least 3 characters", "❌ Toaster text mismatch!");
            
          

       //one:one call with positive Inputs
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
       
       String serviceToaster = page.locator("//div[text()='Service added sucessfully']").textContent().trim();
       Assert.assertEquals(serviceToaster, "Service added sucessfully", "❌ Toaster message mismatch!");

       
     //video call
       ServicesPage videoCall= new ServicesPage(page);
       videoCall.videoCall().click();
       Locator videoCallService =videoCall.addService();
       videoCallService.scrollIntoViewIfNeeded();
       videoCallService.click();

       page.waitForTimeout(3000);

       List<String> videoCallError = page.locator(".text-danger").allTextContents();

       for(String msg : videoCallError) {
           System.out.println("❌ Error: " + msg);
       }

       // expected messages
       String[] expectedErrors = {
               "Service Name is required",
               "Language is required",
               "Service Fee is required",
               "Slot is required"
       };

       // loop assertion
       for(String expected : expectedErrors){
           Assert.assertTrue(videoCallError.contains(expected), "❌ Missing error message: " + expected);
       }

       videoCall.videoCall().click();
       videoCall.serviceName().fill("video Call");
       page.locator(".rmsc.service-react-multi-select > .dropdown-container > .dropdown-heading").click();
       page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("English")).click();
       
       
       Thread.sleep(2000);
       
       Locator inputContainer = page.locator("//div[@class='react-select__value-container css-hlgwow']//div[@class='react-select__input-container css-19bb58m']");
       inputContainer.nth(2).click();
       System.out.println("slot selected");
       page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("15 mins")).locator("div").click();
       
       videoCall.calenderButton().click();
       
       videoCall.Monday().click();
       
       // Start time at 12:00 AM
       LocalTime time = LocalTime.of(0, 0);
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a"); // Added space before AM/PM
       
       videoCall.submitButton().click();  
       String slotSelectionError = page.locator("//div[contains(text(),'Please select at least two time slots')]").textContent().trim();
       Assert.assertTrue(slotSelectionError.contains("Please select at least two time slots"), "❌ Slot selection validation mismatch!");


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
       page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Service Fee")).fill("900");
       videoCall.evaluate().click();
       videoCall.addService().click();
       
       
       String videoServiceToaster = page.locator("//div[text()='Service added sucessfully']").first().textContent().trim();
       Assert.assertEquals(videoServiceToaster, "Service added sucessfully", "❌ Toaster message mismatch!");

       //ask query 
       ServicesPage askQuery= new ServicesPage(page);
       
       //click on add service without providing the proper inputs
       askQuery.askQuery().click();
       askQuery.addService().click();
       List<String> askQueryError = page.locator(".text-danger").allTextContents();

       for(String msg : askQueryError) {
           System.out.println("❌ askQueryError: " + msg);
       }

       // expected messages
       String[] expectedAskQueryErrors = {
               "Service Name is required",
               "Reply within is required",
               "Service Fee is required"
       };

       // loop assertion
       for(String expected : expectedAskQueryErrors){
           Assert.assertTrue(askQueryError.contains(expected), "❌ Missing askQuery error message: " + expected);
       }

       
       askQuery.serviceName().fill("aa");
       askQuery.serviceName().blur();  
       String askQueryError2 = page.locator("//span[text()='Service Name must be at least 3 characters']").first().textContent().trim();
       Assert.assertEquals(askQueryError2, "Service Name must be at least 3 characters", "❌ Toaster message mismatch!");

      

       askQuery.serviceName().fill("Ask query");
       askQuery.serviceFee().fill("700");
       askQuery.evaluate().click();
       page.locator("//div[contains(@class,'react-select__value-container css-hlgwow')]//div[contains(@class,'react-select__input-container css-19bb58m')]").nth(2).click();
       page.keyboard().type("2");
       page.keyboard().press("Enter");
       askQuery.addService().click();
       System.out.println(askQuery.serviceCard().textContent()); 
       
       String askQueryToast = page.locator("//div[text()='Service added sucessfully']").first().textContent().trim();
       Assert.assertEquals(askQueryToast, "Service added sucessfully", "❌ Toaster message mismatch!");

       
       
       //resources
       ServicesPage resources= new ServicesPage(page);
       resources.resources().click();
       resources.addService().click();
       
       
       List<String> resourcesError = page.locator(".text-danger").allTextContents();

       for(String msg : resourcesError) {
           System.out.println("❌ askQueryError: " + msg);
       }

       // expected messages
       String[] expectedresourcesError = {
               "Service Name is required",
               "Service Fee is required"
       };

       // loop assertion
       for(String expected2 : expectedresourcesError){
           Assert.assertTrue(resourcesError.contains(expected2), "❌ Missing askQuery error message: " + expected2);
       }

       resources.serviceName().fill("aa");
       resources.serviceName().blur();  
       String resourcesError2 = page.locator("//span[text()='Service Name must be at least 3 characters']").first().textContent().trim();
       Assert.assertEquals(resourcesError2, "Service Name must be at least 3 characters", "❌ Toaster message mismatch!");


       resources.serviceName().fill("resources");
       resources.serviceFee().fill("600");
       resources.evaluate().click();
       resources.uploadFile().setInputFiles(Paths.get("C:/Users/Admin/Downloads/pexels-moh-adbelghaffar-771742.jpg"));
       resources.addService().click();
       Thread.sleep(3000);
       
       String resourcesToast = page.locator("//div[text()='Service added sucessfully']").first().textContent().trim();
       Assert.assertEquals(resourcesToast, "Service added sucessfully", "❌ Toaster message mismatch!");

       
       //brand Endorsement
       ServicesPage brandEndorsement= new ServicesPage(page);
       
       
       brandEndorsement.brandEndoursement().click();
       brandEndorsement.addService().click();
       List<String> brandEndorsementError = page.locator(".text-danger").allTextContents();
       Assert.assertTrue(brandEndorsementError.stream().anyMatch(e -> e.contains("Service Name is required")), 
               "❌ Required message not found!");

       brandEndorsement.serviceName().fill("aa");
       brandEndorsement.serviceName().blur();

       String brandEndorsementError2 = page.locator("//span[text()='Service Name must be at least 3 characters']").first().textContent().trim();
       Assert.assertEquals(brandEndorsementError2, "Service Name must be at least 3 characters", "❌ Toaster message mismatch!");

       
       
       brandEndorsement.serviceName().fill("service Name");
       brandEndorsement.addService().click();


       String brandEndorsementToast = page.locator("//div[text()='Service added sucessfully']").first().textContent().trim();
       Assert.assertEquals(brandEndorsementToast, "Service added sucessfully", "❌ Toaster message mismatch!");

       
    // Personalized Video
       ServicesPage personalizedVideo = new ServicesPage(page);

       // click on add service without providing the details
       personalizedVideo.personalizedVideo().click();
       personalizedVideo.addService().click();

       // capture all errors
       List<String> personalizedVideoError = page.locator(".text-danger").allTextContents();

       for(String msg : personalizedVideoError) {
           System.out.println("❌ personalizedVideoError: " + msg);
       }

       // expected messages
       String[] expectedPersonalizedVideoError = {
               "Service Name is required",
               "Service Fee is required"
       };

       // loop assertion
       for(String expected2 : expectedPersonalizedVideoError) {
           Assert.assertTrue(personalizedVideoError.contains(expected2), "❌ Missing personalized video error message: " + expected2);
       }

       // additional validations
       Assert.assertTrue(personalizedVideoError.contains("Video Duration is required"), "❌ Missing: Video Duration is required");
       Assert.assertTrue(personalizedVideoError.contains("Delivery Time is required"), "❌ Missing: Delivery Time is required");

       // enter valid data now
       personalizedVideo.serviceName().fill("aa");
       personalizedVideo.serviceName().blur();

       String personalizedVideoError2 = page.locator("//span[text()='Service Name must be at least 3 characters']").first().textContent().trim();
       Assert.assertEquals(personalizedVideoError2, "Service Name must be at least 3 characters", "❌ Toaster message mismatch!");

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

       // Save and Next
       personalizedVideo.saveAndNextButton().nth(2).click();
       System.out.println("save button clicked");

       String personalizedVideoToast = page.locator("//div[text()='Service added sucessfully']").first().textContent().trim();
       Assert.assertEquals(personalizedVideoToast, "Service added sucessfully", "❌ Toaster message mismatch!");
       
       
       
       com.promilo.automation.mentorship.mentor.BudgetAndCost budgeAndCost= new com.promilo.automation.mentorship.mentor.BudgetAndCost(page);
       budgeAndCost.addInvoiceinfo().click();
       page.waitForTimeout(5000);

       
       
       // -------------------- Invoice Form --------------------
       MentorshipFormComponents invoiceForm= new MentorshipFormComponents(page);
       Locator invoiceFormsaveButton=page.locator("//button[normalize-space()='Save']");

       page.getByRole(AriaRole.CHECKBOX, 
               new Page.GetByRoleOptions().setName("By checking this box, I")).check();
           
           invoiceFormsaveButton.scrollIntoViewIfNeeded();
           invoiceFormsaveButton.click(); 

       invoiceFormsaveButton.scrollIntoViewIfNeeded();
       invoiceFormsaveButton.click(); 
       
       page.waitForTimeout(5000);
       Locator invoiceErrors = page.locator("//span[text()='This field is mandatory']");
       List<String> invoiceErrorTexts = invoiceErrors.allTextContents();

       // assertion
       Assert.assertTrue(invoiceErrorTexts.contains("This field is mandatory"),
               "❌ Mandatory field error not displayed!");

       invoiceForm.InvoiceNameField().fill("Karthik");
       invoiceForm.StreetAdress1().fill("Dasarahalli");
       invoiceForm.StreetAdress2().fill("Bangalore");
       invoiceForm.pinCode().fill("560057");
       invoiceForm.yesRadrioBox().click();
       invoiceForm.gstNumber().fill("22AAAAA9999A1Z5");
       invoiceForm.panNumber().fill("AAAAA9999A");
       page.getByRole(AriaRole.CHECKBOX, 
           new Page.GetByRoleOptions().setName("By checking this box, I")).check();
       
       invoiceFormsaveButton.scrollIntoViewIfNeeded();
       invoiceFormsaveButton.click(); 
       
       String invoiceToast = page.locator("//div[text()='Updated successfully']").textContent().trim();
       Assert.assertEquals(invoiceToast, "Updated successfully", "❌ Toaster text mismatch!");
        
        
       //Budget And Cost
       budgeAndCost.addbankAccount().click();
       budgeAndCost.accountHoldername().fill("karthik");
       budgeAndCost.accountNumber().fill("12345679000");
       budgeAndCost.branchName().fill("abbbbb");
       budgeAndCost.ifscCode().fill("kkbk0008066");
       budgeAndCost.panNumber().fill("AMKPU9080P");
       budgeAndCost.billingCountry().nth(9).click();
       page.keyboard().type("ind");
       page.keyboard().press("Enter");
       
       
       page.waitForTimeout(5000);

       Locator bankErrors = page.locator("//span[text()='This field is mandatory']");
       List<String> bankErrors2 = bankErrors.allTextContents();

       for(String err11 : bankErrors2){
           System.out.println("❌ Bank Error: " + err11);
       }


       String bankDetailsToast = page.locator("//div[text()='Successfully Saved']").textContent().trim();
       Assert.assertEquals(bankDetailsToast, "Successfully Saved", "❌ Toaster message mismatch!");
       
       
       
       budgeAndCost.emailCheckbox().click();
       budgeAndCost.smsCheckbox().click();
       budgeAndCost.notificationCheckboc().click();
       budgeAndCost.whatsappCheckbox().click();
       
       
       page.locator("//button[text()='Save & Preview']").click();
       System.out.println("preview button clicked ");
       
       String previewToaset = page.locator("//div[text()='Successfully Saved']").first().textContent().trim();
       Assert.assertEquals(previewToaset, "Successfully Saved", "❌ Toaster text mismatch!");
       
       page.locator("//div[contains(@class,'fw-extra-bold pb-0 position-relative')]//img[contains(@alt,'close')]").click();
       page.locator("//span[text()='Publish']").click();
       page.getByRole(AriaRole.DIALOG).getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Publish")).click();
       
       page.waitForTimeout(3000);
       Assert.assertTrue(page.locator("(//h4[normalize-space()=\"We'll notify you as soon as it goes live!\"])[1]").isVisible(),
       	        "❌ Notify confirmation message not displayed!");

    }

}
