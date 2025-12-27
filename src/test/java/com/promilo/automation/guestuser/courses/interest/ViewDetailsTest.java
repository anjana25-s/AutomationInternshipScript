package com.promilo.automation.guestuser.courses.interest;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class ViewDetailsTest extends BaseClass {

    @Test
    public void TaltoExpert() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 ViewDetailsTest Functionality | Data-Driven");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty())
                break;
            rowCount++;
        }

        test.info("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 7); 
            String password = excel.getCellData(i, 6); 
            String comment = excel.getCellData(i, 10); 

            if (!"CommentFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("🔐 Executing TestCase: " + testCaseId + " | Email: " + email);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            test.info("🌐 Navigated to application URL.");

            page.setViewportSize(1000, 768);
            test.info("🖥️ Viewport size set to 1000x768.");

            Thread.sleep(3000);

            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            try {
                mayBeLaterPopUp.getPopup().click();
                test.info("❌ Closed popup successfully.");
            } catch (Exception ignored) {
                test.info("⚠️ No popup found.");
            }

            Thread.sleep(3000);

            page.locator("//a[text()='Courses']").click();
            test.info("📚 Clicked on 'Courses'.");

            page.locator("//span[text()='View Details']").first().click();
            test.info("🔎 Clicked 'View Details' of the first course.");

            Thread.sleep(2000);

            String description = page.locator("//div[@class='description-details search-description-details']").textContent();
            test.info("📖 Course Description: " + description);

            page.locator("//div[@class='swiper-slide swiper-slide-active']");
            test.info("📌 Viewed active course slide in carousel.");
        }
    }
}
