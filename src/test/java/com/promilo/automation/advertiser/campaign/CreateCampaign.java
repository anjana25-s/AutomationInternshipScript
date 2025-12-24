package com.promilo.automation.advertiser.campaign;

import com.promilo.automation.advertiser.campaign.*;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
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
import com.promilo.automation.advertiser.jobcampaign.ScreeningQuestions;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.ToasterUtil;

public class CreateCampaign extends Baseclass {

    private ExtentReports extent;
    private ExtentTest test;
    private Page page;

    @Test
    public void performLoginTest() {
        try {
            extent = ExtentManager.getInstance();
            test = extent.createTest("🚀 Campaign Creation via Excel Data");

            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

            String email = excel.getCellData(1, 2);
            String password = excel.getCellData(1, 3);
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

            login.loginMailField().fill(email);
            login.loginPasswordField().fill(password);
            login.signInButton().click();

            page.locator("//a[@class='nav-menu-main menu-toggle hidden-xs is-active nav-link']").click();
            AdvertiserHomepage campaign = new AdvertiserHomepage(page);
            campaign.campaign().click();

            Campaign clickCampaign = new Campaign(page);
            clickCampaign.AddnewCampaign().click();
            clickCampaign.SmartEhire().click();
            clickCampaign.JobButton().click();

            CreateJobCampaign campaignDetails = new CreateJobCampaign(page);
            campaignDetails.campaingnName().fill(campaignName);
            campaignDetails.JobRole().fill(jobRole);
            campaignDetails.BrandName().fill(brandName);

            campaignDetails.CompanyType().click();
            page.keyboard().type("Hospitality");
            page.keyboard().press("Enter");

            Thread.sleep(3000);
            campaignDetails.IndustryDropdown().click();
            page.locator("//div[contains(text(),'Accounting / Tax / Company Secretary / Audit')]").click();

            campaignDetails.JobRoleDropdown().click();
            Thread.sleep(3000);
            page.locator("//div[text()='Head/VP/GM-Finance/Audit']").click();
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
            campaignDetails.CampaingnStartDate().fill(startDate);
            campaignDetails.CampaignEndDate().fill(endDate);

            campaignDetails.Workmode().click();
            page.keyboard().press("Enter");

            campaignDetails.UploadCompanyLogo().setInputFiles(Paths.get("C:/Users/Admin/Downloads/pexels-moh-adbelghaffar-771742.jpg"));
            page.locator("button[class='crop-next-buttonN btn btn-secondary']").click();
            campaignDetails.SaveButton().click();

            campaignDetails.textArea().fill("Located in the heart of the city..."); // still hardcoded as discussed
            campaignDetails.AddTitle().click();
            campaignDetails.BenefitsAndPerks().click();
            campaignDetails.AddButton().click();

            page.locator("//a[text()='Benefits & Perks']").click();
            campaignDetails.textArea().nth(1).fill("Located in the heart of the city...");

            campaignDetails.saveAndnextButton().click();

            CreateJobPosting choseDateandTime = new CreateJobPosting(page);
            choseDateandTime.ChoseTimeSlot().click();
            page.keyboard().type("30");
            page.keyboard().press("Enter");

            choseDateandTime.Monday().click();
            page.locator("//button[@class='plus-icon btn btn-secondary']").click();

            page.locator("//div[@class='time-slot-select__input-container css-19bb58m']").nth(1).click();
            page.keyboard().type("12");
            page.keyboard().press("Enter");

            page.locator("//button[@class='plus-icon btn btn-secondary']").click();
            page.locator("//div[@class='time-slot-select__input-container css-19bb58m']").nth(2).click();
            page.keyboard().type("02");
            page.keyboard().press("Enter");

            choseDateandTime.English().click();
            choseDateandTime.Hindi().click();
            choseDateandTime.Kannada().click();
            choseDateandTime.SaveButton().click();

            ScreeningQuestions screening = new ScreeningQuestions(page);
            Feedback feedback = new Feedback(page);
            screening.AddButton().click();
            page.locator("input[placeholder='Type Question Here']").fill("are you an immediate joiner");
            feedback.EnteroptionField().fill("yes");
            
            page.locator("//button[@class='py-0 px-1 ms-0 btn btn-primary']").click();
            
            page.locator("//input[@class='border-0 outline-0 ms-1 form-control']").nth(1).fill("no0");
            screening.SaveButton().click();

            
            
            
            
            


            // Feedback again
            feedback.AddButton().click();
            page.locator("input[placeholder='Type Question Here']").fill("are you an immediate joiner");
            feedback.EnteroptionField().fill("yes");
            page.locator("//button[@class='py-0 px-1 ms-0 btn btn-primary']").click();
            
            page.locator("//input[@class='border-0 outline-0 ms-1 form-control']").nth(1).fill("no0");
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
audience.FunctionalAreaOption().click();            audience.SelectRelevantTitle().click();
            page.keyboard().type("accounts");
            page.keyboard().press("Enter");

            audience.Keywords().fill("Promilo engineer9");
            page.keyboard().press("Enter");

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

            Locator statusDiv = page.locator("//div[@role='status']");
            Assert.assertTrue(statusDiv.isVisible(), "Status div should be visible");            
            test.pass("✅ Campaign successfully created using Excel data for: " + email);

        } catch (Exception e) {
            if (test != null) {
                test.fail("❌ Test failed: " + e.getMessage());
            }
            e.printStackTrace();
        }
    }
}
