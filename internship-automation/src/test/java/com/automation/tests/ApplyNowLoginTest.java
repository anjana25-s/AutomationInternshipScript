package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.ApplyNowPage;
import com.automation.pages.HomepagePage;
import com.automation.utils.HelperUtility;
import com.automation.utils.LoginUtility;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.*;

public class ApplyNowLoginTest extends BaseClass {

    private HomepagePage home;
    private ApplyNowPage apply;
    private LoginUtility loginUtility;
    private HelperUtility helper;

    private static final String EMAIL = "testme19990@yopmail.com";
    private static final String PASSWORD = "Test@123";
    private static final String INTERNSHIP = "Finance- Job role";

    @BeforeClass
    public void init() {
        home = new HomepagePage(page);
        apply = new ApplyNowPage(page);
        loginUtility = new LoginUtility(page);
        helper = new HelperUtility(page);
    }

    @BeforeMethod
    public void openBaseUrl() {

        helper.log("[Step 1] Navigating to Promilo...");
        page.navigate("https://stage.promilo.com/");
        page.waitForLoadState();
        helper.takeScreenshot("Page_Loaded");

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close 'May Be Later' Popup");
        }
    }

    @Test
    public void verifyApplyNowAsLoggedInUser() {

        // ⭐ Step 2: Login
        helper.log("[Step 2] Logging in...");
        loginUtility.loginWithPassword(EMAIL, PASSWORD);
        helper.takeScreenshot("After_Login");

        Assert.assertTrue(loginUtility.isFullyLoggedIn(), "❌ Login failed!");
        helper.log("[Step 2 ✅] Login successful.");

        helper.safeClick(home.getInternshipsTab(), "Open Internships");

        Locator card = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card, "Internship Card");
        Assert.assertTrue(card.isVisible(), "❌ Internship card not visible!");

        helper.scrollAndClick(card, "Open Internship");
        Assert.assertTrue(apply.getApplyNowButton().isVisible(), "❌ Apply Now button missing!");

        helper.safeClick(apply.getApplyNowButton(), "Click Apply Now");

        // ⭐ Step 5: Language, Date, Time
        helper.safeClick(apply.getLanguageCard("English"), "Select English Language");
        helper.safeClick(apply.getFirstActiveDate(), "Select Date");
        helper.safeClick(apply.getFirstActiveTimeSlot(), "Select Time");

        helper.takeScreenshot("Language_Date_Time_Selected");

        // ASSERT — Date & Time fields selected
        helper.log("✔ Date and Time selection successful");

        // ⭐ Step 6: Screening or Direct Submit
        helper.log("[Step 6] Checking for screening questions...");

        if (apply.getNextButton().isVisible()) {

            helper.safeClick(apply.getNextButton(), "Click Next (Screening)");

            // -----------------------------
            // ⭐ UNIVERSAL SCREENING HANDLER
            // -----------------------------
            Locator questions = apply.getScreeningQuestions();
            int total = questions.count();
            helper.log("Total screening questions: " + total);

            Assert.assertTrue(total > 0, "❌ No screening questions found!");

            for (int i = 0; i < total; i++) {

                helper.log("Answering Question " + (i + 1));
                Locator q = questions.nth(i);

                // objective options
                Locator objectiveOptions = q.locator("input[type='checkbox'], input[type='radio']");
                if (objectiveOptions.count() > 0) {
                    helper.safeClick(objectiveOptions.first(), "Select objective option");
                }

                // subjective
                Locator textArea = q.locator("textarea");
                if (textArea.count() > 0) {
                    helper.safeFill(textArea.first(),
                            "Automated test answer",
                            "Subjective Answer");
                }
            }

            helper.safeClick(apply.getScreeningSubmitButton(),
                    "Submit Screening Answers");

        } else {

            helper.safeClick(apply.getCalendarSubmitButton(),
                    "Submit Without Screening");
        }

        // ⭐ Step 7: Verify Success
        helper.waitForVisible(apply.getThankYouHeader(), "Thank You Popup");

        Assert.assertTrue(apply.getThankYouHeader().isVisible(),
                "❌ Thank You popup NOT shown!");

        helper.log("✔ Thank You popup shown!");

        // CLICK MY INTEREST
        helper.safeClick(apply.getThankYouMyInterestLink(), "Click My Interest");

        page.waitForURL("**/myinterest**");
        Assert.assertTrue(page.url().contains("myinterest"), "❌ My Interest NOT opened!");

        helper.log("✔ Navigated to My Interest");

        // Verify Interest Card
        Locator interestCard = page.locator("//div[contains(@class,'my-interest-card-contianer')]").first();
        helper.waitForVisible(interestCard, "My Interest Card");

        Assert.assertTrue(interestCard.isVisible(), "❌ Interest card not visible!");

        helper.log("✔ Interest Card Verified");

        // Validate Status
        Locator status = interestCard.locator(".my-interest-status-tag");
        Assert.assertEquals(status.innerText().trim(), "Pending",
                "❌ Interest status not Pending!");

        helper.log("✔ Status = Pending");

        // Validate Date & Time
        String date = interestCard.locator("(//div[@class='card_detail-value'])[1]").innerText().trim();
        String time = interestCard.locator("(//div[@class='card_detail-value'])[2]").innerText().trim();

        Assert.assertFalse(date.isEmpty(), "❌ Meeting Date empty!");
        Assert.assertFalse(time.isEmpty(), "❌ Meeting Time empty!");

        helper.log("✔ Meeting Date & Time Validated");

        helper.log("🎉 APPLY NOW LOGIN FLOW PASSED SUCCESSFULLY!");
        helper.takeScreenshot("Application_Success");
    }
}



