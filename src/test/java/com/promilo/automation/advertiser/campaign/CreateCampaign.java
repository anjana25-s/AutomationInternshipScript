package com.promilo.automation.advertiser.campaign;

import com.promilo.automation.advertiser.campaign.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AddAssignment;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.jobcampaign.BudgetAndCost;
import com.promilo.automation.advertiser.jobcampaign.Campaign;
import com.promilo.automation.advertiser.jobcampaign.CreateAssignment;
import com.promilo.automation.advertiser.jobcampaign.CreateJobCampaign;
import com.promilo.automation.advertiser.jobcampaign.CreateJobPosting;
import com.promilo.automation.advertiser.jobcampaign.Feedback;
import com.promilo.automation.advertiser.jobcampaign.Myaudience;
import com.promilo.automation.advertiser.jobcampaign.SaveAndPreviewPage;
import com.promilo.automation.advertiser.jobcampaign.ScreeningQuestions;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.ToasterUtil;

public class CreateCampaign extends BaseClass {

    private ExtentReports extent;
    private ExtentTest test;
    private Page page;

    @Test
    public void CreateCampaignTest() {
        try {
            extent = ExtentManager.getInstance();
            test = extent.createTest("🚀 Campaign Creation via Excel Data");

            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

            String email = excel.getCellData(1, 2);
            String InputValue = excel.getCellData(1, 3);
            String campaignName = excel.getCellData(1, 4);
            String jobRole = excel.getCellData(1, 5);
            String brandName = excel.getCellData(1, 6);
            String description = excel.getCellData(1, 7);
            String minExp = excel.getCellData(1, 8);
            String maxExp = excel.getCellData(1, 9);
            String location = excel.getCellData(1, 10);
            String startDate = excel.getCellData(1, 11);
            String endDate = excel.getCellData(1, 12);
            String salaryMin = excel.getCellData(1, 13);
            String salaryMax = excel.getCellData(1, 14);
            String assignmentTitle = excel.getCellData(1, 15);
            String assignmentTopic = excel.getCellData(1, 16);
            String assignmentDesc = excel.getCellData(1, 17);

            page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 668);
            Thread.sleep(5000);

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To expert content is visible");

            login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
            login.loginPasswordField().fill("Karthik@88");
            login.signInButton().click();

            AdvertiserHomepage campaign = new AdvertiserHomepage(page);
            campaign.hamburger().click();
            campaign.campaign().click();

            Campaign clickCampaign = new Campaign(page);
            clickCampaign.AddnewCampaign().click();
            clickCampaign.SmartEhire().click();
            clickCampaign.JobButton().click();

            CreateJobCampaign campaignDetails = new CreateJobCampaign(page);
            campaignDetails.campaingnName().fill("December Automation");
            campaignDetails.JobRole().fill(jobRole);
            campaignDetails.BrandName().fill("December Campaign Automation");

            campaignDetails.CompanyType().click();
            page.keyboard().type("Hospitality");
            page.keyboard().press("Enter");

            Thread.sleep(3000);
            campaignDetails.IndustryDropdown().click();
            campaignDetails.industryAccountingOption().click();

            campaignDetails.JobRoleDropdown().click();
            Thread.sleep(3000);
            campaignDetails.jobRoleFinanceOption().click();
            campaignDetails.Descrption().fill(description);

            campaignDetails.minExperiance().click();
            page.keyboard().type(minExp);
            page.keyboard().press("Enter");

            campaignDetails.maxExperiance().click();
            page.keyboard().type(maxExp);
            page.keyboard().press("Enter");

            campaignDetails.LocationDropdown().click();
            page.keyboard().type(location);
            page.keyboard().press("Enter");

            Thread.sleep(2000);
            campaignDetails.CampaingnStartDate().first().fill("2025-11-21");
            campaignDetails.CampaignEndDate().fill("2026-07-10");

            campaignDetails.Workmode().click();
            page.keyboard().press("Enter");

            campaignDetails.UploadCompanyLogo().setInputFiles(Paths.get("C:/Users/Admin/Downloads/pexels-moh-adbelghaffar-771742.jpg"));
            campaignDetails.cropNextButton().click();
            campaignDetails.SaveButton().click();

            campaignDetails.textArea().fill("Located in the heart of the city..."); 
            campaignDetails.AddTitle().click();
            campaignDetails.BenefitsAndPerks().click();
            campaignDetails.AddButton().click();

            campaignDetails.previewBenefitsTab().click();
            campaignDetails.textArea().nth(1).fill("Located in the heart of the city...");

            campaignDetails.saveAndnextButton().click();

            CreateJobPosting choseDateandTime = new CreateJobPosting(page);
            choseDateandTime.ChoseTimeSlot().click();
            page.keyboard().type("30");
            page.keyboard().press("Enter");

          
            campaignDetails.mondayCheckbox().click();
                // Create 10 slots for the day
                for (int i = 0; i < 10; i++) {
                	campaignDetails.addSlotButton().click();
                	Thread.sleep(1000);

                    // Select nth slot
                	campaignDetails.timeSlotContainer()
                        .nth(i + 1).click();
                    Thread.sleep(1000);

                    page.keyboard().type(String.format("%02d", (i % 24)));
                    Thread.sleep(500);

                    page.keyboard().press("Enter");
                    Thread.sleep(500);
                }

                choseDateandTime.English().click();
                    choseDateandTime.Hindi().click();
                    choseDateandTime.Kannada().click();
                    choseDateandTime.SaveButton().click();


            ScreeningQuestions screening = new ScreeningQuestions(page);
            Feedback feedback = new Feedback(page);
            screening.AddButton().click();
            campaignDetails.questionInput().fill("are you an immediate joiner");
            feedback.EnteroptionField().fill("yes");
            
            campaignDetails.addOptionButton().click();
            
            campaignDetails.optionInput().nth(1).fill("no0");
            screening.SaveButton().click();

            // Feedback again
            feedback.AddButton().click();
            campaignDetails.questionInput().fill("are you an immediate joiner");
            feedback.EnteroptionField().fill("yes");
            campaignDetails.addOptionButton().click();
            
            
            campaignDetails.optionInput().nth(1).fill("no0");
            feedback.FeedbackSavebutton().click();

            AddAssignment assignment = new AddAssignment(page);
            assignment.addAssignment().click();
            assignment.createAssignment().click();

            CreateAssignment create = new CreateAssignment(page);
            create.AssignmentTextfield().fill(assignmentTitle);
            create.assignmenttopic().fill(assignmentTopic);
            create.Jobrole().fill(jobRole);
            create.AssignmentTextarea().fill(assignmentDesc);
            create.SubmitButton().click();
            
            create.SelectButton().first().click();
            create.SaveAndNextButton().click();

            // My Audience
            Myaudience audience = new Myaudience(page);
            audience.AudienceIndustryDropdwon().first().click();
            page.keyboard().type("Media, Entertainment & Telecom");
            page.keyboard().press("Enter");

            audience.FunctionalAreadropdown().click();
            audience.FunctionalAreaOption().click();            
            audience.SelectRelevantTitle().click();
            page.keyboard().type("accounts");
            page.keyboard().press("Enter");

            // Generate a random word (8 letters)
            int wordLength = 8;
            String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            StringBuilder randomWord = new StringBuilder();
            Random random = new Random();

            for (int i = 0; i < wordLength; i++) {
                randomWord.append(alphabet.charAt(random.nextInt(alphabet.length())));
            }

            // Fill the random word
            audience.Keywords().fill(randomWord.toString());
            page.keyboard().press("Enter");

            System.out.println("✅ Entered Random Word: " + randomWord);

            audience.MinAge().first().click();
            page.keyboard().type("20");
            page.keyboard().press("Enter");
            audience.Maxage().nth(1).click();
            page.keyboard().type("29");
            page.keyboard().press("Enter");
            audience.LocationDropdown().click();
            page.keyboard().type(location);
            page.keyboard().press("Enter");
            audience.Savebutton().click();

            // Budget & Cost
            BudgetAndCost budget = new BudgetAndCost(page);
            budget.sendEmail().click();
            budget.sendSMS().click();
            budget.notifications().click();
            budget.whatsapp().click();
            budget.minOfferedSalary().fill(salaryMin);
            budget.maxOfferedSalary().fill(salaryMax);
            budget.evaluate().click();

            budget.costPerInterview().click();
            
            Thread.sleep(2000);
            
            budget.saveAndPreview().click();
            Thread.sleep(3000);
            
         // ====== Role ======
            SaveAndPreviewPage job= new SaveAndPreviewPage(page);
            String title = job.title().textContent();
            System.out.println("Title: " + title);
            Assert.assertTrue(title.contains("Software Tester"),
                    "❌ Title does NOT contain expected text");
            
            

            // ====== Campaign Name ======
            String subTitle = job.subTitle().textContent();
            System.out.println("Sub Title: " + subTitle);
            Assert.assertTrue(subTitle.contains("December Campaign Automation"),
                    "❌ Sub-title does NOT contain expected text");

            // ====== Salary ======
            String salary = job.salary().textContent();
            System.out.println("salary" + salary);
            Assert.assertTrue(salary.contains("₹ 5.7L - 10.0L"),
                    "❌ Company & Location does NOT contain salary indicator");

            // ====== JOB DETAILS ======
            String Experience = job.experience().textContent();
            System.out.println("Experience: " + Experience);
            Assert.assertTrue(Experience.contains("Experience"),
                    "❌ Job Detail 1 missing 'Experience'");

            // ====== COMPANY & LOCATION ======
            String Location = job.location().textContent();
            System.out.println("Location: " + Location);
            Assert.assertTrue(Location.contains("Location"),
                    "❌ Job Detail 2 missing 'Location'");

            // ====== Work Mode======
            String workMode = job.workMode().textContent();
            System.out.println("Work Mode: " + workMode);
            Assert.assertTrue(workMode.contains("Work Mode"),
                    "❌ Job Detail 3 missing 'Work Mode'");

            // ====== JOB DESCRIPTION ======
            job.jobDescriptionTab().click();
            String jobDescription = job.ckContent().textContent();
            System.out.println("Job Description: " + jobDescription);
            Assert.assertTrue(jobDescription.contains("city"),
                    "❌ Job Description missing expected content");

            // ====== BENEFITS & PERKS ======
            job.benefitsTab().click();
            String perks = job.ckContent().textContent();
            System.out.println("Benefits & Perks: " + perks);
            Assert.assertTrue(perks.contains("city"),
                    "❌ Benefits & Perks missing expected content");

            // ====== OTHER DETAILS ======
            String companyName = job.companyName().textContent();
            System.out.println("Company Name: " + companyName);
            Assert.assertTrue(companyName.contains("Company Name"),
                    "❌ Other Detail 1 missing 'Company Name'");

            String companyType = job.companyType().textContent();
            System.out.println("Company Type: " + companyType);
            Assert.assertTrue(companyType.contains("Company Type"),
                    "❌ Other Detail 2 missing 'Company Type'");

            String brandName1 = job.brandName().textContent();
            System.out.println("Brand Name: " + brandName1);
            Assert.assertTrue(brandName1.contains("Brand"),
                    "❌ Other Detail 3 missing 'Brand'");

            String companySize = job.companySize().textContent();
            System.out.println("Company Size: " + companySize);
            Assert.assertTrue(companySize.contains("Company Size"),
                    "❌ Other Detail 4 missing 'Company Size'");

            String aboutCompanyTitle = job.aboutCompanyTitle().textContent();
            System.out.println("About Company Title : " + aboutCompanyTitle);
            Assert.assertTrue(aboutCompanyTitle.contains("About"),
                    "❌ Section title missing 'About'");

            String companyDetails = job.paragraph9().textContent();
            System.out.println("Paragraph 9: " + companyDetails);
            Assert.assertTrue(companyDetails.contains("engineer"),
                    "❌ Paragraph 9 missing expected keyword");

            job.closeButton().click();
            job.publishButton().click();
        
        } catch (Exception e) {
            if (test != null) {
                test.fail("❌ Test failed: " + e.getMessage());
            }
            e.printStackTrace();
        }
    }
}
