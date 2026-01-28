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
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.jobcampaign.BudgetAndCost;
import com.promilo.automation.advertiser.jobcampaign.Feedback;
import com.promilo.automation.advertiser.jobcampaign.Myaudience;
import com.promilo.automation.advertiser.jobcampaign.ScreeningQuestions;
import com.promilo.automation.courses.pageobjects.AddApplicationPage;
import com.promilo.automation.courses.pageobjects.CampusVisitCampaignCreattion;
import com.promilo.automation.courses.pageobjects.CreateCampaignPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class CreateCampaign extends BaseClass {

    private ExtentReports extent;
    private ExtentTest test;
    private Page page;

    @Test
    public void createCampaignFlow() throws IOException, InterruptedException {

        extent = ExtentManager.getInstance();
        test = extent.createTest("🚀 Campaign Creation - Data Driven");

        // ================= LOAD EXCEL =================
        String excelPath = Paths.get(System.getProperty("user.dir"),
                "Testdata", "Mentorship Test Data.xlsx").toString();

        ExcelUtil excel = new ExcelUtil(excelPath, "Courses");

        // ================= HEADER MAP (SAFE) =================
        Map<String, Integer> colMap = new HashMap<>();
        for (int c = 0; c < excel.getColumnCount(); c++) {
            String header = excel.getCellData(0, c);
            if (header != null && !header.trim().isEmpty()) {
                colMap.put(header.trim(), c);
            }
        }

        System.out.println("📌 Excel Headers: " + colMap.keySet());

        // ================= ROW COUNT =================
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String tcId = excel.getCellData(i, getCol(colMap, "Tc_Id"));
            if (tcId == null || tcId.trim().isEmpty()) break;
            rowCount++;
        }

        Set<String> targetKeywords = Collections.singleton("Course Campaign Creation");

        for (int i = 1; i <= rowCount; i++) {

            String keyword = excel.getCellData(i, getCol(colMap, "Keyword"));
            if (!targetKeywords.contains(keyword)) continue;

            // ================= READ EXCEL DATA =================
            String email = excel.getCellData(i, getCol(colMap, "Email"));
            String password = excel.getCellData(i, getCol(colMap, "Password"));
            String campaignName = excel.getCellData(i, getCol(colMap, "CampaignName"));
            String productTitle = excel.getCellData(i, getCol(colMap, "ProductTitle"));
            String brandName = excel.getCellData(i, getCol(colMap, "BrandName"));
            String description = excel.getCellData(i, getCol(colMap, "Description"));

            String startDate = excel.getCellData(i, getCol(colMap, "StartDate"));
            String endDate = excel.getCellData(i, getCol(colMap, "EndDate"));
            String years = excel.getCellData(i, getCol(colMap, "DurationYears"));

            String imagePath = excel.getCellData(i, getCol(colMap, "UploadImagePath"));
            String documentPath = excel.getCellData(i, getCol(colMap, "UploadDocumentPath"));

            String driveLink = excel.getCellData(i, getCol(colMap, "DriveLink"));
            String UrlTitle = excel.getCellData(i, getCol(colMap, "UrlTitle"));
            String CampaignUrl = excel.getCellData(i, getCol(colMap, "CampaignUrl"));

            String applicationName = excel.getCellData(i, getCol(colMap, "ApplicationName"));
            String openDate = excel.getCellData(i, getCol(colMap, "OpeningDate"));
            String closeDate = excel.getCellData(i, getCol(colMap, "ClosingDate"));
            String street = excel.getCellData(i, getCol(colMap, "StreetAddress"));
            String pincode = excel.getCellData(i, getCol(colMap, "PinCode"));
            String city = excel.getCellData(i, getCol(colMap, "City"));
            String state = excel.getCellData(i, getCol(colMap, "State"));
            String applicationCost = excel.getCellData(i, getCol(colMap, "ApplicationCost"));

            String totalCost = excel.getCellData(i, getCol(colMap, "TotalCost"));
            String prospectCount = excel.getCellData(i, getCol(colMap, "ProspectCount"));
            String location = excel.getCellData(i, getCol(colMap, "Location"));
            String selectedCourse= excel.getCellData(i, getCol(colMap, "Course"));


            String tabContent = productTitle;

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
            Locator checkboxes = campaignPage.checkboxes();

            int count = checkboxes.count();
            int limit = Math.min(3, count);

            for (int i1 = 0; i1 < limit; i1++) {
                checkboxes.nth(i1).click();
            }
            
            campaignPage.addButton().click();
            
            
            Thread.sleep(3000);
            campaignPage.tabTextbox().first().fill(tabContent);
            campaignPage.coursesAndFeesTab().click();
            campaignPage.tabTextbox().nth(1).fill(tabContent);
            campaignPage.syllabusTab().click();
            campaignPage.tabTextbox().nth(2).fill(tabContent);
            campaignPage.saveNextSpan().click();

            
            
            // ================= LINKS & DOCUMENT =================
            campaignPage.uploadLinkDiv().click();
            campaignPage.driveLinkInput().fill(driveLink);
            campaignPage.submitInput().click();
            
            
            
            campaignPage.addPhotosOrVideosDiv().click();

            page.setInputFiles(
                    "input[type='file']",
                    Paths.get("C:/Users/Admin/Downloads/preview_fullpage.png")
            );
            campaignPage.cropButton().click();
            
            campaignPage.saveNextP().click();
            
            
            

            
            
            
            CampusVisitCampaignCreattion campusVisit= new CampusVisitCampaignCreattion(page);
            
            

            page.waitForTimeout(1000);
            campusVisit.ChoseTimeSlot().click();
            page.keyboard().type("30");
            page.keyboard().press("Enter");

          
            campaignPage.monday().click();
                // Create 10 slots for the day
                for (int i1 = 0; i1 < 10; i1++) {
                	campusVisit.addSlotButton().click();
                	Thread.sleep(500);

                    // Select nth slot
                	campaignPage.timeSlotContainer()
                        .nth(i1 + 1).click();
                    Thread.sleep(500);

                    page.keyboard().type(String.format("%02d", (i1 % 24)));
                    Thread.sleep(500);

                    page.keyboard().press("Enter");
                    Thread.sleep(500);
                }

                campaignPage.English().click();
                campaignPage.Hindi().click();
                campaignPage.Kannada().click();
                
                
                
                
                page.waitForTimeout(500);
                campusVisit.visitCheckBox().click();
                
                page.waitForTimeout(500);
                campusVisit.ChoseTimeSlot().nth(1).click();
                page.keyboard().type("30");
                page.keyboard().press("Enter");

              
                campusVisit.Monday().click();
                    // Create 10 slots for the day
                    for (int i1 = 0; i1 < 10; i1++) {
                    	campusVisit.addSlotButton().nth(1).click();
                    	Thread.sleep(500);

                        // Select nth slot
                    	campusVisit.timeSlotContainer()
                            .nth(i1 ).click();
                        Thread.sleep(500);

                        page.keyboard().type(String.format("%02d", (i1 % 24)));
                        Thread.sleep(500);

                        page.keyboard().press("Enter");
                        Thread.sleep(500);
                    }

                    campusVisit.English().click();
                    campusVisit.Hindi().click();
                    campusVisit.Kannada().click();
                    campusVisit.mapLinkField().fill("https://maps.app.goo.gl/mwbHXKqbHAha6sFQ8");   
                    campusVisit.selectCounselors().click();
                    page.waitForTimeout(2000);
                    campusVisit.selectCounselorsOption().click();
                    campusVisit.SaveButton().click();
                    


                
                   
            ScreeningQuestions screening = new ScreeningQuestions(page);
            Feedback feedback = new Feedback(page);
            screening.AddButton().click();
            campusVisit.questionInput().fill("are you an immediate joiner");
            feedback.EnteroptionField().fill("yes");
            
            campusVisit.addOptionButton().click();
            
            campusVisit.optionInput().nth(1).fill("no0");
            screening.SaveButton().click();

            // Feedback again
            feedback.AddButton().click();
            campusVisit.questionInput().fill("are you an immediate joiner");
            feedback.EnteroptionField().fill("yes");
            campusVisit.addOptionButton().click();
            
            
            campusVisit.optionInput().nth(1).fill("no0");
            feedback.FeedbackSavebutton().click();   
            
            
            
            
            
            
            
            
            
            
            
            
            campaignPage.urlTitleInput().fill(UrlTitle);
            campaignPage.campaignUrlInput().fill(CampaignUrl);
            campaignPage.documentTitleInput().fill("College PDF");
            campaignPage.uploadDocumentButton().setInputFiles(Paths.get(documentPath));
            
            
            
            
            // ================= ADD APPLICATION (FULL) =================

            
         
         
         

            AddApplicationPage addApp = new AddApplicationPage(page);

         // ================= ADD APPLICATION =================
         addApp.clickAddApplication().click();
         addApp.clickCreateApplication().click();
         addApp.createManually().click();

         // ================= CLEAR PRE-SELECTED OPTIONS =================
         long endTime = System.currentTimeMillis() + 20000;

         while (System.currentTimeMillis() < endTime) {
             int count1 = addApp.selectedOptionCross().count();
             if (count1 == 0) {
                 break;
             }

             for (int i1 = 0; i1 < count1; i1++) {
                 Locator cross = addApp.selectedOptionCross().nth(i1);
                 if (cross.isVisible()) {
                     cross.click();
                     break;
                 }
             }
         }

         // Assertion
         Assert.assertEquals(
                 addApp.selectedOptionCross().count(),
                 0,
                 "❌ Exit icons still present"
         );

         // ================= APPLICATION DETAILS =================
         addApp.enterApplicationFormName().fill(applicationName);
         addApp.openingDate().fill(openDate);
         addApp.closingDate().fill(closeDate);
         addApp.streetAdress().fill(street);
         addApp.pinCode().fill(pincode);
         addApp.city().fill(city);
         addApp.state().fill(state);
         addApp.applicationCost().fill(applicationCost);

         // ================= ACCREDITATION =================
         addApp.selectAccreditation().click();
         page.keyboard().type("NAAC");
         page.keyboard().press("Enter");

         // ================= PERSONAL INFO =================
         addApp.selectPersonalIdentificationAndDemographics().click();
         addApp.selectLegalName().click();
         clickAllSubOptions(page);

         // ================= ACADEMIC HISTORY =================
         addApp.selectAcademicHistory().click();

         addApp.class10Details().click();
         clickAllSubOptions(page);

         addApp.class12Details().click();
         clickAllSubOptions(page);

         addApp.universityLastAttended().click();
         clickAllSubOptions(page);

         addApp.degreeCertificate().click();
         clickAllSubOptions(page);

         addApp.workExperienceCertificate().click();
         clickAllSubOptions(page);

         addApp.fieldSelectionAndPreview().click();

         // ================= ENTRANCE EXAMS =================
         addApp.entranceExamsSection().click();

         addApp.nationalLevelExams().click();
         clickAllSubOptions(page);

         addApp.stateLevelExams().click();
         clickAllSubOptions(page);

         addApp.universitySpecificExams().click();
         clickAllSubOptions(page);

         addApp.fieldSelectionAndPreview().click();

         // ================= CATEGORY & ELIGIBILITY =================
         addApp.categoryEligibilitySection().click();

         addApp.categoryOfAdmission().click();
         clickAllSubOptions(page);

         addApp.documentaryEvidence().click();
         clickAllSubOptions(page);

         addApp.pwdStatus().click();
         clickAllSubOptions(page);

         addApp.sportsQuota().click();
         clickAllSubOptions(page);

         addApp.ecaQuota().click();
         clickAllSubOptions(page);

         addApp.domicileStatus().click();
         clickAllSubOptions(page);

         addApp.fieldSelectionAndPreview().click();

         // ================= DOCUMENTATION =================
         page.waitForTimeout(3000);

         addApp.selectDocumentation().click();

         addApp.personalIdentityDocuments().click();
         clickAllSubOptions(page);

         addApp.academicDocuments().click();
         clickAllSubOptions(page);

         addApp.conditionalStatutoryDocuments().click();
         addApp.affidavitsUndertakings().click();
         clickAllSubOptions(page);

         addApp.fieldSelectionAndPreview().click();

         // ================= CUSTOM FIELD =================
         addApp.customFieldDropdown().click();
         addApp.customFieldCategoryText().click();
         addApp.customFieldNameInput().fill("Something");
         addApp.addButton().click();

         page.waitForTimeout(3000);

         // Required checkboxes
         for (int checkbox1 = 0; checkbox1 < addApp.requiredCheckboxes().count(); checkbox1++) {
             Locator checkbox = addApp.requiredCheckboxes().nth(checkbox1);
             if (checkbox.isVisible()) {
                 checkbox.click();
             }
         }

         // ================= NEXT FLOW =================
         addApp.nextButton().click();
         System.out.println("1st button clicked");

         addApp.nextButton().click();
         System.out.println("2nd button clicked");

         addApp.nextButton().click();
         System.out.println("3rd button clicked");

         page.waitForTimeout(3000);
         addApp.nextButton().click();
         System.out.println("4th button clicked");

         // ================= NATIONAL LEVEL EXAMS =================
         page.waitForTimeout(500);
         addApp.selectNationalLevelExam().click();
         page.keyboard().type("JEE");
         page.keyboard().press("Enter");

         page.waitForTimeout(500);
         addApp.addMoreLink().first().click();

         page.waitForTimeout(500);
         addApp.selectNationalLevelExam().nth(1).click();
         page.keyboard().type("JEE");
         page.keyboard().press("Enter");

         // ================= CREATE APPLICATION =================
         addApp.createButton().click();
         addApp.closePopupIcon().click();

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
            
            

            page.waitForTimeout(2000);
            // ================= ASSERTIONS =================
            Assert.assertEquals(
                    campaignPage.brandName().textContent().trim(), brandName);
            Assert.assertEquals(
                    campaignPage.productTitle().textContent().trim(), productTitle);
            
            Assert.assertEquals(
                    campaignPage.location().textContent().trim(), location);
            
            Assert.assertEquals(       
            		campaignPage.duration().textContent().trim(), years);
            
            Assert.assertEquals(  
            		campaignPage.averageFees().textContent().trim(), totalCost);
            
            
            
            
            
            
            
            campaignPage.collegeInfoTab().click();
            Assert.assertEquals(       
            		campaignPage.tabsContent().textContent().trim(), productTitle);
            
            
            campaignPage.coursesAndFeesTab().click();
            Assert.assertEquals(       
            		campaignPage.tabsContent().textContent().trim(), productTitle);

            
            campaignPage.syllubus().click();
            Assert.assertEquals(       
            		campaignPage.tabsContent().textContent().trim(), productTitle);
            

            
            campaignPage.closePreview().click();
            
            
            

            campaignPage.publishButton().click();
            test.pass("✅ Campaign created successfully");
        }
    }

    // ================= SAFE COLUMN ACCESS =================
    private int getCol(Map<String, Integer> colMap, String header) {
        if (!colMap.containsKey(header)) {
            throw new RuntimeException("❌ Missing Excel column: " + header);
        }
        return colMap.get(header);
    }

    // ================= UTILITY =================
    private void clickAllSubOptions(Page page) {
        Locator options = page.locator(".sub-option");
        for (int i = 0; i < options.count(); i++) {
            options.nth(i).click();
        }
    }
}
