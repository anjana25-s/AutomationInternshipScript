package com.promilo.automation.resources;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.Assert;

import java.util.List;

public class CampaignNegativeValidationUtil {

    // ----------------------------
    // Generic Utilities
    // ----------------------------
    
    public static void shortWait(Page page) {
        page.waitForTimeout(3000);
    }

    public static void selectByText(Page page, String text) {
        Locator element = page.locator("//div[text()='" + text + "']");
        element.scrollIntoViewIfNeeded();
        element.click();
    }

    public static void printErrors(List<String> errors) {
        for (String msg : errors) {
            System.out.println("❌ Error: " + msg);
        }
    }

    // ----------------------------
    // Assertions
    // ----------------------------

    public static void assertErrorVisible(Page page, String errorText) {
        Locator errorLocator = page.locator("//span[text()='" + errorText + "']");
        Assert.assertTrue(errorLocator.isVisible(), "❌ Error not visible: " + errorText);
    }

    public static void assertMultipleErrors(Page page, String[] expectedErrors) {
        for (String error : expectedErrors) {
            assertErrorVisible(page, error);
        }
    }

    public static void assertToastMessage(Page page, String expected) {
        Locator toast = page.locator("//div[contains(@class,'toast')]");
        String actual = toast.textContent().trim();
        Assert.assertEquals(actual, expected, "❌ Toaster message mismatch!");
    }

    public static void assertFieldVisible(Page page, String fieldName) {
        Locator locator = page.locator("//input[@name='" + fieldName + "'] | //textarea[@name='" + fieldName + "']");
        Assert.assertTrue(locator.isVisible(), "❌ Field not visible: " + fieldName);
    }

    public static void assertDropdownVisible(Page page, String dropdownName) {
        Locator locator = page.locator("//select[@name='" + dropdownName + "']");
        Assert.assertTrue(locator.isVisible(), "❌ Dropdown not visible: " + dropdownName);
    }

    public static void assertDivText(Page page, String expectedText) {
        Locator locator = page.locator("//div[text()='" + expectedText + "']");
        Assert.assertTrue(locator.isVisible(), "❌ Div not visible with text: " + expectedText);
    }

    public static void assertTextDangerErrors(Page page, String[] expectedErrors) {
        List<String> actualErrors = page.locator(".text-danger").allTextContents();
        printErrors(actualErrors);
        for (String error : expectedErrors) {
            Assert.assertTrue(actualErrors.stream().anyMatch(e -> e.contains(error)),
                    "❌ Expected error not found: " + error);
        }
    }

    // ----------------------------
    // Section-specific validations
    // ----------------------------

    public static void validateMyProfileSection(Page page) {
        String[] errors = {
            "Full Name is required",
            "Email is required",
            "Phone Number is required"
        };
        assertTextDangerErrors(page, errors);
        assertFieldVisible(page, "fullName");
        assertFieldVisible(page, "email");
        assertFieldVisible(page, "phoneNumber");
    }

    public static void validateCampaignDetailsSection(Page page) {
        String[] errors = {
            "Campaign Name is required",
            "Campaign Type is required",
            "Start Date is required"
        };
        assertTextDangerErrors(page, errors);
        assertFieldVisible(page, "campaignName");
        assertDropdownVisible(page, "campaignType");
        assertFieldVisible(page, "startDate");
    }

    public static void validateAboutMeSection(Page page) {
        String[] errors = {
            "Description is required",
            "Profile Image is required"
        };
        assertTextDangerErrors(page, errors);
        assertFieldVisible(page, "description");
    }

    public static void validateMyAudienceSection(Page page) {
        String[] errors = {
            "Select at least one Industry",
            "Select at least one Functional Area"
        };
        assertTextDangerErrors(page, errors);
        assertDropdownVisible(page, "industry");
        assertDropdownVisible(page, "functionalArea");
    }

    public static void validateBudgetCostSection(Page page) {
        String[] errors = {
            "Budget is required",
            "Cost is required"
        };
        assertTextDangerErrors(page, errors);
        assertFieldVisible(page, "budget");
        assertFieldVisible(page, "cost");
    }
}
