package com.promilo.automation.mentorship.mentor.campaign;

import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;
import com.promilo.automation.mentorship.mentor.BecomeMentor;
import com.promilo.automation.mentorship.utilities.AboutMeSection;
import com.promilo.automation.mentorship.utilities.AudienceSection;
import com.promilo.automation.mentorship.utilities.BudgetInvoiceSection;
import com.promilo.automation.mentorship.utilities.CampaignDetailsSection;
import com.promilo.automation.mentorship.utilities.CreateProfileSection;
import com.promilo.automation.mentorship.utilities.ServicesSection;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;

public class CampaignCreationRunner extends BaseClass {

    private static final Logger log = LogManager.getLogger(CampaignCreationRunner.class);
    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        System.out.println("⚙️ Performing signup ONCE before suite using Mailosaur...");
        com.promilo.automation.resources.SignupWithMailosaurUI signupWithMailosaur =
                new com.promilo.automation.resources.SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();
        registeredEmail = creds[0];
        registeredPassword = creds[1];
        System.out.println("✅ Signup completed. Registered user: " + registeredEmail);
    }

    @Test
    public void runFullCampaignCreation() throws Exception {
        if (registeredEmail == null || registeredPassword == null) {
            log.error("❌ Signup credentials missing. Aborting test.");
            return;
        }

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));

        // Excel setup
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "CampaignCreation");
        int lastRow = excel.getRowCount();

        for (int i = 1; i <= lastRow; i++) {
            String keyword = excel.getCellData(i, excel.getColumnIndex("Keyword"));
            if (keyword == null || !keyword.equalsIgnoreCase("CampaignCreation")) continue;

            try {
                // ---------------- Read Excel Data ----------------
                String firstName = excel.getCellData(i, excel.getColumnIndex("FirstName"));
                String lastName = excel.getCellData(i, excel.getColumnIndex("LastName"));
                String phone = excel.getCellData(i, excel.getColumnIndex("Phone"));
                String otp = excel.getCellData(i, excel.getColumnIndex("OTP"));
                String location = excel.getCellData(i, excel.getColumnIndex("Location"));
                String gender = excel.getCellData(i, excel.getColumnIndex("Gender"));
                String experience = excel.getCellData(i, excel.getColumnIndex("Experience"));
                String mentorType = excel.getCellData(i, excel.getColumnIndex("MentorType"));
                String domain = excel.getCellData(i, excel.getColumnIndex("Domain"));
                String category = excel.getCellData(i, excel.getColumnIndex("Category"));
                String course = excel.getCellData(i, excel.getColumnIndex("Course"));
                String dob = excel.getCellData(i, excel.getColumnIndex("DOB"));
                String specialization = excel.getCellData(i, excel.getColumnIndex("Specialization"));
                String imagePath = excel.getCellData(i, excel.getColumnIndex("ImagePath"));
                String socialLink = excel.getCellData(i, excel.getColumnIndex("SocialLink"));
                String highlight = excel.getCellData(i, excel.getColumnIndex("Highlight"));
                String aboutMeText = excel.getCellData(i, excel.getColumnIndex("AboutMe"));
                String industry = excel.getCellData(i, excel.getColumnIndex("Industry"));
                String functionalArea = excel.getCellData(i, excel.getColumnIndex("FunctionalArea"));
                String relevantTitle = excel.getCellData(i, excel.getColumnIndex("RelevantTitle"));
                String audienceLocation = excel.getCellData(i, excel.getColumnIndex("AudienceLocation"));
                String ageFrom = excel.getCellData(i, excel.getColumnIndex("AgeFrom"));
                String ageTo = excel.getCellData(i, excel.getColumnIndex("AgeTo"));
                String campaignName = excel.getCellData(i, excel.getColumnIndex("CampaignName"));
                String startDate = excel.getCellData(i, excel.getColumnIndex("StartDate"));
                String endDate = excel.getCellData(i, excel.getColumnIndex("EndDate"));
                String skill = excel.getCellData(i, excel.getColumnIndex("Skill"));

                // ---------------- Login Flow ----------------
                MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
                try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}
                mayBeLaterPopUp.clickLoginButton();

                LoginPage loginPage = new LoginPage(page);
                loginPage.loginMailPhone().fill(registeredEmail);
                loginPage.passwordField().fill(registeredPassword);
                loginPage.loginButton().click();

                // Navigate to My Stuff
                Hamburger myStuff = new Hamburger(page);
                myStuff.Mystuff().click();

                BecomeMentor becomeMentor = new BecomeMentor(page);
                becomeMentor.becomeMentorButton().click();
                becomeMentor.createMentorshipSession().click();

                // ---------------- Fill Sections ----------------
                new CreateProfileSection(page).fillProfile(firstName, lastName, phone, otp,
                        location, gender, experience, mentorType, domain, category,
                        course, dob, specialization, imagePath, socialLink, highlight);
                
                new CampaignDetailsSection(page).fillCampaignDetails(campaignName, startDate, endDate, skill);


                new AboutMeSection(page).fillAboutMe(aboutMeText);

                new AudienceSection(page).fillAudience(industry, functionalArea, relevantTitle,
                        audienceLocation, null, ageFrom, ageTo);


                new ServicesSection(page).addServices("1:1 Call", "1000", "Video Call", "2000",
                        "Ask Query", "1500", "Resources", "2500", "Brand Endorsement", "3000",
                        "Personalized Video", "5000", imagePath);

                new BudgetInvoiceSection(page).fillInvoiceAndBank("ExcelInvoice", "Street 1", "Street 2",
                        "500001", "22AAAAA0000A1Z5", "AMKPU8302M", "Account Holder", "1234567890",
                        "Branch Name", "KKBK0008066", "AMKPU8032P", "India");

                

            } catch (Exception e) {
                log.error("❌ Error in row " + i + ": " + e.getMessage());
                throw e;
            }
            
        }

        page.close();
    }
}
