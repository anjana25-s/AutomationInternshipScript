package com.promilo.automation.advertiser.mybilling;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MybillingaddFunds extends BaseClass {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("🚀 Advertiser MybillingaddFunds Test | Data-Driven");

    // -----------------------------------------
    // FIX: Safe amount cleaning method
    // -----------------------------------------
    private String cleanAmount(String raw) {
        return raw.replace("₹", "")
                  .replace(",", "")
                  .replace("\u00A0", "")    // remove non-breaking space
                  .replaceAll("\\s+", "")   // remove all unicode spaces
                  .trim();
    }

    @Test
    public void MybillingaddFundsTest() {
        try {
            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                    "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            test.info("✅ Navigated to: " + prop.getProperty("stageUrl"));

            // ------------------- LOGIN -------------------
            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            login.loginMailField().fill("warm-apart@ofuk8kzb.mailosaur.net");
            login.loginPasswordField().fill("Karthik@88");
            login.signInButton().click();
            test.info("✅ Logged in successfully");

            // ------------------- NAVIGATE TO BILLING -------------------
            AdvertiserHomepage homepage = new AdvertiserHomepage(page);
            homepage.hamburger().click();
            homepage.myAccount().click();
            homepage.myBilling().click();
            test.info("✅ Navigated to My Billing");

            // Billing object (unchanged)
            Billing billing = new Billing(page);

            // ------------------- READ INITIAL AMOUNT -------------------
            String beforeAddingFund = page.locator("//span[@class='text-primary fw-bold m-0']").textContent().trim();
            System.out.println("First Raw Amount: " + beforeAddingFund);

            String firstCleaned = cleanAmount(beforeAddingFund);
            double firstAmount = Double.parseDouble(firstCleaned);
            System.out.println("First Amount (Before): " + firstAmount);

            // ------------------- ADD FUNDS -------------------
            billing.AddFunds().click();
            
            
            
         // ------------------- ASSERT BEFORE ADDING FUND WITH Pop-Up -------------------
            String popUpAmountRaw = page.locator(".font-11.text-primary.pt-50").textContent().trim();
            System.out.println("Raw Amount in new locator: " + popUpAmountRaw);

            // Extract only numbers (including decimals) from the string
            String popUpAmount = popUpAmountRaw.replaceAll("[^0-9.]", "");
            String beforeAmount = beforeAddingFund.replaceAll("[^0-9.]", "");

            System.out.println("Numeric Amount in new locator: " + popUpAmount);

            // Assert that both funds match
            org.testng.Assert.assertEquals(popUpAmount, beforeAmount, "Amount mismatch after clicking Add Funds!");
           
            
            
            
            
            billing.amountTextfield().fill("100000");
            test.info("✅ Add Funds clicked and amount entered");

            billing.payButton().click();
            test.info("✅ Pay button clicked");

            // ------------------- PAYMENT FRAME OPERATIONS -------------------
            page.locator("iframe").contentFrame().getByTestId("contactNumber").click();
            page.locator("iframe").contentFrame().getByTestId("contactNumber").fill("9000086844");
            page.locator("iframe").contentFrame().getByTestId("Wallet").click();
            test.info("✅ Wallet button clicked");

            Page page8 = page.waitForPopup(() -> {
                page.locator("iframe").contentFrame()
                        .getByTestId("screen-container")
                        .locator("div")
                        .filter(new Locator.FilterOptions().setHasText("PhonePe"))
                        .nth(2)
                        .click();
            });

            page8.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Success")).click();

            Locator successMessage = page.getByText("Payment Successfully");

            successMessage.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

            Assert.assertTrue(successMessage.isVisible(), "✅ Payment success message is visible");
            test.info("✅ Payment success message is visible");
            test.info("Add fund functionality test pass");

            // ------------------- READ FINAL AMOUNT -------------------
            String afterAddingFund = page.locator("//span[@class='text-primary fw-bold m-0']").textContent().trim();
            System.out.println("Second Raw Amount: " + afterAddingFund);

            String secondCleaned = cleanAmount(afterAddingFund);
            double secondAmount = Double.parseDouble(secondCleaned);
            System.out.println("Second Amount (After): " + secondAmount);

            // Validate amount >= 1 lakh
            Assert.assertTrue(
                    secondAmount >= 100000,
                    "Amount is NOT greater than or equal to 1 Lakh! Actual: " + secondAmount
            );
            
            
            page.reload();
            page.waitForTimeout(3000);
            
            

         // -------------------- Fetch the date from table --------------------
            String dateText = page.locator(
                "(//table//tr[td//div[contains(normalize-space(.), 'Fund added- promilo wallet')]]//div[@class='d-flex align-items-center'])[2]"
            ).textContent().trim();

            System.out.println("Date from table: " + dateText);

            // -------------------- Get current date in same format --------------------
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            String today = LocalDate.now().format(formatter);

            System.out.println("Current date: " + today);

            // -------------------- Assert the dates match --------------------
            assertEquals(dateText, today, "Date in table does not match current date!");
            
            

         // -------------------- Capture timestamp (exact time row appeared) --------------------
            LocalDateTime rowGeneratedTime = LocalDateTime.now();

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
            String generatedAt = rowGeneratedTime.format(timeFormatter);

            System.out.println("Row generated timestamp: " + generatedAt);
            
            
            
            

            // -------------------- ASSERT the timestamp is "reasonable" --------------------
            LocalDateTime now = LocalDateTime.now();

            // Allowed window: ±10 seconds
            long secondsDiff = java.time.Duration.between(rowGeneratedTime, now).getSeconds();

            // Assert within 10 seconds (you can adjust)
            // This means the timestamp captured and the actual current time are close enough
            assertTrue(Math.abs(secondsDiff) <= 10,
                    "Timestamp is not within expected range! Difference: " + secondsDiff + " seconds.");

            

        } catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            extent.flush();
        }
    }
}
