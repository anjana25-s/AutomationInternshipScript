package com.promilo.automation.mentorship.utilities;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentor.BudgetAndCost;
import com.promilo.automation.mentorship.mentor.CreateProfile;
import com.promilo.automation.resources.ExcelUtil;

public class BudgetInvoiceSection {

    private Page page;
    private BudgetAndCost budget;

    public BudgetInvoiceSection(Page page) {
        this.page = page;
        this.budget = new BudgetAndCost(page);
    }

    public void fillInvoiceAndBank(String invoiceName, String street1, String street2, String pincode,
                                   String gstNumber, String panNumber, String accountHolder,
                                   String accountNumber, String branchName, String ifscCode, String bankPAN,
                                   String country) throws IOException, InterruptedException {

        // ========================== INVOICE INFO SECTION ==========================
        page.waitForTimeout(3000);
        budget.addInvoiceinfo().click();
        com.promilo.automation.mentorship.mentee.MentorshipFormComponents invoice = 
                new com.promilo.automation.mentorship.mentee.MentorshipFormComponents(page);

        if (invoiceName != null) invoice.InvoiceNameField().fill(invoiceName);
        if (street1 != null) invoice.StreetAdress1().fill(street1);
        if (street2 != null) invoice.StreetAdress2().fill(street2);
        if (pincode != null) invoice.pinCode().fill(pincode);
        invoice.yesRadrioBox().click();
        if (gstNumber != null) invoice.gstNumber().fill(gstNumber);
        if (panNumber != null) invoice.panNumber().fill(panNumber);
        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("By checking this box, I")).check();

        page.locator("//button[normalize-space()='Save']").click();


        // ========================== BANK DETAILS SECTION ==========================
        page.waitForTimeout(3000);
        budget.addbankAccount().click();
        if (accountHolder != null) budget.accountHoldername().fill(accountHolder);
        if (accountNumber != null) budget.accountNumber().fill(accountNumber);
        if (branchName != null) budget.branchName().fill(branchName);
        if (ifscCode != null) budget.ifscCode().fill(ifscCode);
        if (bankPAN != null) budget.panNumber().fill(bankPAN);

        budget.billingCountry().nth(9).click();
        page.keyboard().type(country == null ? "ind" : country.substring(0, Math.min(3, country.length())));
        page.keyboard().press("Enter");

        page.locator("//button[normalize-space()='Save']").click();


        // ========================== NOTIFICATION CHECKBOXES SECTION ==========================
        budget.emailCheckbox().click();
        budget.smsCheckbox().click();
        budget.notificationCheckboc().click();
        budget.whatsappCheckbox().click();

        budget.savePreviewButton().click();


        // ========================== VALIDATE NAME ON PREVIEW SECTION ==========================
        page.waitForTimeout(4000);

        Locator heading = page.locator("//div[@class='d-flex font-28 mb-0 pointer box-fit-content mentorship-heading']");
        String uiHeading = heading.textContent().trim();

        CreateProfileSection profileSection = new CreateProfileSection(page);
        CreateProfile profilePage = new CreateProfile(page);

        String fNameUi = profilePage.firstName().inputValue().trim();
        String lNameUi = profilePage.lastName().inputValue().trim();

        String expectedFullName = fNameUi + " " + lNameUi;

        Assert.assertTrue(
                uiHeading.toLowerCase().contains(expectedFullName.toLowerCase()),
                "❌ Name mismatch on preview! Expected: " + expectedFullName + " | But Found: " + uiHeading
        );


        // ========================== VALIDATE HIGHLIGHT TEXT SECTION ==========================
        page.waitForTimeout(2000);

        Locator highlightLocator = page.locator("//h2[@class='font-16 pb-50 pt-25 mb-0 pointer box-fit-content sub-heading']");
        String highlightUi = highlightLocator.textContent().trim();

        com.promilo.automation.mentorship.mentor.CreateProfile profilePage1 =
                new com.promilo.automation.mentorship.mentor.CreateProfile(page);

        String highlightInput = profilePage1.highlightTextfield().inputValue().trim();

        Assert.assertFalse(highlightInput.isEmpty(),
                "❌ The highlight input field is empty — nothing was entered to validate against.");

        Assert.assertTrue(
                highlightUi.equalsIgnoreCase(highlightInput),
                "❌ Highlight mismatch!\nExpected (input): \"" + highlightInput + "\"\nActual (preview): \"" + highlightUi + "\""
        );

        System.out.println("Validate Highlight Text");
        page.waitForTimeout(4000);


        // ========================== VALIDATE TYPE OF MENTOR SECTION ==========================
        Locator typeOfMentorValue = page.locator("//h5[contains(., 'Academic Guidance') and contains(., 'Engineering')]");
        String typeOfMentorUi = typeOfMentorValue.textContent().trim();

        System.out.println("Type of Mentor Preview Text: " + typeOfMentorUi);

        String expectedCourse = "Academic Guidance";
        String expectedSpecialization = "Engineering";

        boolean containsCourse = typeOfMentorUi.toLowerCase().contains(expectedCourse.toLowerCase());
        boolean containsSpec = typeOfMentorUi.toLowerCase().contains(expectedSpecialization.toLowerCase());

        Assert.assertTrue(containsCourse, "❌ 'Type of mentor' preview missing course!");
        Assert.assertTrue(containsSpec, "❌ 'Type of mentor' preview missing specialization!");

        System.out.println("Validate 'Type of mentor' Contains Course / Specialization ");


        // ========================== VALIDATE LOCATION SECTION ==========================
        String expectedLocation = "Anantapur";
        Locator locationUi = page.locator("//div[@class='col col-md-auto' and contains(., '" + expectedLocation + "')]");

        Assert.assertTrue(locationUi.isVisible(), "❌ Location mismatch! Expected: " + expectedLocation);
        System.out.println("expectedLocation Found");


        // ========================== VALIDATE EXPERIENCE SECTION ==========================
        String expectedExperience = "2 Years";
        Locator experienceUi = page.locator("//div[@class='col col-md-auto' and contains(., '2 Years')]");
        String experienceText = experienceUi.textContent().trim();

        Assert.assertTrue(
                experienceText.toLowerCase().contains(expectedExperience.toLowerCase()),
                "❌ Experience mismatch!"
        );


        // ========================== VALIDATE KEY SKILLS SECTION ==========================
        String expectedSkill = "Automation";
        Locator skillSection = page.locator("//span[@class='keySkillDetails ']");
        String skillText = skillSection.textContent().trim();

        Assert.assertTrue(
                skillText.toLowerCase().contains(expectedSkill.toLowerCase()),
                "❌ Key Skill mismatch!"
        );

        System.out.println("Up to here automation completed");


        Locator serviceTabs = page.locator("//a[@class='d-flex font-sm-12 nav-link tab-link-searchlisting me-1']");
        int tabCount = serviceTabs.count();

        System.out.println("Total service tabs found: " + tabCount);

        // ================= CLICK EVERY TAB & CAPTURE SERVICE CARD TEXT =================
        for (int i = 0; i < tabCount; i++) {

            Locator tab = serviceTabs.nth(i);
            String tabName = tab.textContent().trim();

            System.out.println("\n👉 Clicking tab: " + tabName);
            tab.click();
            page.waitForTimeout(1500);   // allow content to load

            // Get all service cards
            Locator cards = page.locator("//div[@class='services-offered-cards']");
            int cardCount = cards.count();

            System.out.println("🟦 Found " + cardCount + " service cards for tab: " + tabName);

            // Loop through all cards and print visible ones
            for (int c = 0; c < cardCount; c++) {
                Locator card = cards.nth(c);

                if (card.isVisible()) {
                    String text = card.textContent().trim();
                    System.out.println("\n📌 Visible Card " + (c + 1) + " Content:");
                    System.out.println(text);
                }
            }
        }

        // ========================== EXCEL DATA LOADING SECTION ==========================
        String excelPath = Paths.get(
                System.getProperty("user.dir"),
                "Testdata",
                "Mentorship Test Data.xlsx"
        ).toString();

        ExcelUtil excel = new ExcelUtil(excelPath, "Mentorship");


        // ========================== VALIDATE ABOUT ME SECTION ==========================
        DescriptionPage dp = new DescriptionPage(page);

        List<String> expectedTitles = new ArrayList<>();
        List<String> expectedContents = new ArrayList<>();

        for (int r = 1; r <= excel.getRowCount(); r++) {
            String title = excel.getCellData("About Me Tittles", r).trim();
            String content = excel.getCellData("AboutMeContent", r).trim();

            if (!title.isEmpty()) {
                expectedTitles.add(title);
                expectedContents.add(content);
            }
        }

        Locator aboutMeTitles = dp.aboutMeTittle();
        int titleCount = aboutMeTitles.count();

        for (int t = 0; t < titleCount; t++) {
            String actualTitle = aboutMeTitles.nth(t).textContent().trim();
            aboutMeTitles.nth(t).click();
            Thread.sleep(1500);

            String actualContent = dp.aboutMeContent()
                    .textContent()
                    .replaceAll("\\s+", " ")
                    .trim();

            if (expectedTitles.contains(actualTitle)) {
                int idx = expectedTitles.indexOf(actualTitle);
                String expectedContent = expectedContents.get(idx)
                        .replaceAll("\\s+", " ")
                        .trim();

                Assert.assertTrue(
                        actualContent.contains(expectedContent),
                        "❌ Mismatch in About Me content for title: " + actualTitle
                );

                System.out.println("Validations completed");
            }
        }


        // ========================== PUBLISH PROFILE SECTION ==========================
        budget.previewClose().nth(13).click();
        budget.publishButton().click();
        page.pause();
        budget.publishConfirmation().nth(1).click();

        String actualText = page.getByRole(
                AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("We'll notify you as soon as")
        ).textContent().trim();

        Assert.assertTrue(
                actualText.toLowerCase().contains("we'll notify you as soon"),
                "❌ Expected publish confirmation heading missing!"
        );

        System.out.println("✔ About Me section validated successfully!");
    }
}
