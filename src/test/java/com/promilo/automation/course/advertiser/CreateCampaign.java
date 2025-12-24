package com.promilo.automation.course.advertiser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.*;
import com.promilo.automation.advertiser.jobcampaign.*;
import com.promilo.automation.courses.pageobjects.CreateCampaignPage;
import com.promilo.automation.resources.*;

public class CreateCampaign extends Baseclass {

    private ExtentReports extent;
    private ExtentTest test;
    private Page page;

    @Test
    public void createCampaignFlow() throws IOException, InterruptedException {

        extent = ExtentManager.getInstance();
        test = extent.createTest("🚀 Campaign Creation - Data Driven");

        // ======== Load Excel ========
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "Mentorship Test Data.xlsx").toString();

        ExcelUtil excel;
        try {
            excel = new ExcelUtil(excelPath, "Courses");
        } catch (Exception e) {
            test.fail("Excel load failed: " + e.getMessage());
            Assert.fail();
            return;
        }

        // ======== Header Mapping ========
        Map<String, Integer> colMap = new HashMap<>();
        int totalCols = excel.getColumnCount();
        for (int c = 0; c < totalCols; c++) {
            colMap.put(excel.getCellData(0, c).trim(), c);
        }

        // ======== Row Count ========
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String tcId = excel.getCellData(i, colMap.get("Tc_Id"));
            if (tcId == null || tcId.trim().isEmpty()) break;
            rowCount++;
        }

        Set<String> targetKeywords = Collections.singleton("Course Campaign Creation");

        for (int i = 1; i <= rowCount; i++) {
        	
        	
        	String keyword = excel.getCellData(i, colMap.get("Keyword"));
        	String UrlTitle = excel.getCellData(i, colMap.get("UrlTitle"));
        	String CampaignUrl = excel.getCellData(i, colMap.get("CampaignUrl"));


        	String years = excel.getCellData(i, colMap.get("DurationYears"));
        	String imagePath = excel.getCellData(i, colMap.get("UploadImagePath"));
        	String documentPath = excel.getCellData(i, colMap.get("UploadDocumentPath"));

        	String street = excel.getCellData(i, colMap.get("StreetAddress"));
        	String openDate = excel.getCellData(i, colMap.get("OpeningDate"));
        	String closeDate = excel.getCellData(i, colMap.get("ClosingDate"));

        	if (!targetKeywords.contains(keyword)) continue;

        	// ======== Read Excel Columns ========
        	String email = excel.getCellData(i, colMap.get("Email"));
        	String password = excel.getCellData(i, colMap.get("Password"));
        	String campaignName = excel.getCellData(i, colMap.get("CampaignName"));
        	String productTitle = excel.getCellData(i, colMap.get("ProductTitle"));
        	String brandName = excel.getCellData(i, colMap.get("BrandName"));
        	String description = excel.getCellData(i, colMap.get("Description"));
        	String startDate = excel.getCellData(i, colMap.get("StartDate"));
        	String endDate = excel.getCellData(i, colMap.get("EndDate"));

        	String tabContent = productTitle; // FIXED

        	String driveLink = excel.getCellData(i, colMap.get("DriveLink"));
        	String urlTitle = excel.getCellData(i, colMap.get("UrlTitle"));
        	String campaignUrl = excel.getCellData(i, colMap.get("CampaignUrl"));

        	String applicationName = excel.getCellData(i, colMap.get("ApplicationName"));
        	String pincode = excel.getCellData(i, colMap.get("PinCode")); // FIXED
        	String city = excel.getCellData(i, colMap.get("City"));
        	String state = excel.getCellData(i, colMap.get("State"));
        	String applicationCost = excel.getCellData(i, colMap.get("ApplicationCost"));

        	String totalCost = excel.getCellData(i, colMap.get("TotalCost"));
        	String prospectCount = excel.getCellData(i, colMap.get("ProspectCount"));
        	
        	
        	


            // ================= LAUNCH =================
            page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 668);
            Thread.sleep(5000);

            // ================= LOGIN =================
            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            login.loginMailField().fill(email);
            login.loginPasswordField().fill(password);
            login.signInButton().click();

            AdvertiserHomepage home = new AdvertiserHomepage(page);
            home.hamburger().click();
            home.campaign().click();

            CreateCampaignPage campaignPage = new CreateCampaignPage(page);

            // ================= CAMPAIGN INFO =================
            campaignPage.addNewCampaignButton().click();
            Thread.sleep(2000);
            campaignPage.firstCampaignCard().first().click();

            campaignPage.campaignNameField().first().fill(campaignName);
            campaignPage.productTitleField().fill(productTitle);
            campaignPage.displayBrandNameField().fill(brandName);
            campaignPage.dropdownBasic().click();
            campaignPage.engineeringButton().click();
            campaignPage.beBtechOption().click();
            campaignPage.descriptionField().fill(description);

            campaignPage.selectYearDropdown().click();
            campaignPage.yearOption().click();
            campaignPage.numbersInput().fill(years);
            campaignPage.chooseAppointmentSvg().nth(1).click();
            campaignPage.locationDropdown().first().click();
            campaignPage.startDateField().fill(startDate);
            campaignPage.endDateField().fill(endDate);
            campaignPage.deliveryModeDropdown().click();
            campaignPage.regularOfflineOption().click();

            // ================= IMAGE =================
            campaignPage.uploadImageButton().setInputFiles(Paths.get(imagePath));
            campaignPage.cropNextButton().click();
            campaignPage.saveAndNextButton().click();

            // ================= TABS =================
            campaignPage.addTitleButton().click();
            Thread.sleep(3000);

            campaignPage.tabTextbox().first().fill(tabContent);
            campaignPage.coursesAndFeesTab().click();
            campaignPage.tabTextbox().nth(1).fill(tabContent);
            campaignPage.syllabusTab().click();
            campaignPage.tabTextbox().nth(2).fill(tabContent);
            campaignPage.saveNextSpan().click();

            // ================= LINK =================
            campaignPage.uploadLinkDiv().click();
            campaignPage.driveLinkInput().fill(driveLink);
            campaignPage.submitInput().click();

            // ================= DOCUMENT =================
            campaignPage.urlTitleInput().fill(UrlTitle);
            campaignPage.campaignUrlInput().fill(CampaignUrl);
            campaignPage.documentTitleInput().fill("College PDF");
            campaignPage.uploadDocumentButton().setInputFiles(Paths.get(documentPath));

            // ================= ADD APPLICATION =================
            page.locator("//button[text()='Add Application ']").click();
            page.locator("//button[text()='Create Application']").click();
            page.locator("//button[text()='Create Manually']").click();

            page.locator("#application_form_name").fill(applicationName);
            page.locator("#opening_date").fill(openDate);
            page.locator("#closing_date").fill(closeDate);
            page.locator("#street_address").fill(street);
            page.locator("#pin_code").fill(pincode);
            page.locator("#city").fill(city);
            page.locator("#state").fill(state);
            page.locator("#application_cost").fill(applicationCost);

            page.locator("//div[@class='accreditation-select__input-container css-19bb58m']").click();
            page.keyboard().type("NAAC");
            page.keyboard().press("Enter");

            // ================= PERSONAL INFO =================
            page.locator("//div[@id='PERSONAL_INFO']").click();
            page.locator("//div[text()='Legal Name']").click();
            clickAllSubOptions(page);

            // ================= ACADEMIC HISTORY =================
            page.locator("//div[contains(text(),'Select Academic History')]").click();

            page.locator("//div[text()='Class 10th Details']").click();
            clickAllSubOptions(page);

            page.locator("//div[contains(text(),'Class 12th Details')]").click();
            clickAllSubOptions(page);

            page.locator("//div[contains(text(),'University Last Attended')]").click();
            clickAllSubOptions(page);

            page.locator("//div[contains(text(),'Provisional/Final Degree Certificate')]").click();
            clickAllSubOptions(page);

            page.locator("//div[contains(text(),'Work Experience Certificates')]").click();
            clickAllSubOptions(page);

            // ================= ENTRANCE EXAMS =================
            page.locator("//div[@id='ENTRANCE_EXAMS_AND_TESTS']").click();

            page.locator("//div[contains(text(),'National Level Exams')]").click();
            clickAllSubOptions(page);

            page.locator("//div[contains(text(),'State Level Exams')]").click();
            clickAllSubOptions(page);

            page.locator("//div[contains(text(),'University Specific Exams')]").click();
            clickAllSubOptions(page);

            // ================= CATEGORY & ELIGIBILITY =================
            page.locator("//div[@id='CATEGORY_AND_ELIGIBILITY']").click();

            page.locator("//div[contains(text(),'Category of Admission')]").click();
            clickAllSubOptions(page);

            page.locator("//div[contains(text(),'Documentary Evidence')]").click();
            clickAllSubOptions(page);

            page.locator("//div[contains(text(),'Person with Disability (PWD) Status')]").click();
            clickAllSubOptions(page);

            page.locator("//div[contains(text(),'Sports Quota')]").click();
            clickAllSubOptions(page);

            page.locator("//div[contains(text(),'Extra-Curricular Activities (ECA) Quota')]").click();
            clickAllSubOptions(page);

            page.locator("//div[contains(text(),'Domicile/Residence Status')]").click();
            clickAllSubOptions(page);

            // ================= DOCUMENTATION =================
            page.locator("//div[contains(text(),'Select Documentation')]").click();

            page.locator("//div[text()='Personal & Identity Documents']").click();
            clickAllSubOptions(page);

            page.locator("//div[text()='Academic Documents']").click();
            clickAllSubOptions(page);

            page.locator("//div[text()='Conditional & Statutory Documents']").click();
            page.locator("//div[text()='Special Affidavits & Undertakings']").click();
            clickAllSubOptions(page);

            // ================= CUSTOM FIELD =================
            page.locator("#customFieldMulti > .css-13cymwt-control > .css-hlgwow > .css-19bb58m").click();
            page.getByText("Personal Identification & DemographicsAcademic HistoryEntrance Exam & Test").click();
            page.locator("//input[@placeholder='Enter Field Name']").fill("Something");
            page.waitForTimeout(3000);

            // ================= NATIONAL LEVEL EXAMS =================
            page.locator("//div[text()='Select National Level Exam']").click();
            page.keyboard().type("JEE");
            page.keyboard().press("Enter");

            Locator addMoreLink = page.locator("[class='text-primary pointer']");
            addMoreLink.first().click();

            page.locator("//div[text()='Select National Level Exam']").nth(1).click();
            page.keyboard().type("JEE");
            page.keyboard().press("Enter");

            page.locator("//button[text()='Create']").click();
            campaignPage.saveAndNextButton().click();

            // ================= AUDIENCE =================
            campaignPage.audienceIndustryDropdown().click();
            campaignPage.animationVfxOption().click();
            campaignPage.functionalAreaDropdown().click();
            campaignPage.engineeringElectricalOption().click();
            campaignPage.relevantTitleDropdown().click();
            campaignPage.electricalEngineerOption().click();
            page.keyboard().type("accounts");
            page.keyboard().press("Enter");
            campaignPage.keywordButton().click();

            Myaudience audience = new Myaudience(page);
            audience.MinAge().first().click();
            page.keyboard().type("20");
            page.keyboard().press("Enter");
            audience.Maxage().nth(1).click();
            page.keyboard().type("29");
            page.keyboard().press("Enter");

            campaignPage.searchNameInput().nth(1).click();
            page.keyboard().type("Bangalore");
            page.keyboard().press("Enter");

            campaignPage.saveAndNextButton().click();

            // ================= BUDGET =================
            campaignPage.emailCheckbox().click();
            campaignPage.smsCheckbox().click();
            campaignPage.notificationCheckbox().click();
            campaignPage.totalCostInput().fill(totalCost);
            campaignPage.prospectCountInput().fill(prospectCount);

            BudgetAndCost budget = new BudgetAndCost(page);
            budget.evaluate().click();
            campaignPage.savePreviewButton().first().click();

            // ================= ASSERTIONS =================
            Assert.assertEquals(
                    campaignPage.brandName().textContent().trim(), brandName);
            Assert.assertEquals(
                    campaignPage.productTitle().textContent().trim(), productTitle);

            campaignPage.publishButton().click();
            test.pass("✅ Campaign created successfully");
        }
    }

    // ================= UTILITY =================
    private void clickAllSubOptions(Page page) {
        Locator options = page.locator(".sub-option");
        int count = options.count();
        for (int i = 0; i < count; i++) {
            options.nth(i).click();
        }
    }
}
