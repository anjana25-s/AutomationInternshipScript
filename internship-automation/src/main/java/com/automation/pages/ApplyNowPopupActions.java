package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class ApplyNowPopupActions {

    private final Page page;
    private final ApplyNowPopupLocators locators;

    public ApplyNowPopupActions(Page page) {
        this.page = page;
        this.locators = new ApplyNowPopupLocators(page);
    }

    // =========================
    // Utility Methods
    // =========================

    private void waitForVisible(Locator locator, int timeoutMs) {
        locator.waitFor(new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE)
            .setTimeout((double) timeoutMs));
    }

    private void waitForVisible(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions()
            .setState(WaitForSelectorState.VISIBLE));
    }

    // =========================
    // Actions
    // =========================

    public void clickApplyNowCardPage() {
        Locator applyNowBtn = page.locator(locators.applyNowBtn);
        waitForVisible(applyNowBtn);
        applyNowBtn.click();
        System.out.println("[ApplyNowPopupActions] Clicked 'Apply Now' button on card page.");
    }
    
    public void fillUserDetails(String name, String phone, String industry, String email) {
        Locator nameField = page.locator(locators.userNameField);
        Locator phoneField = page.locator(locators.userPhoneField);

        waitForVisible(nameField);
        nameField.fill(name);

        waitForVisible(phoneField);
        phoneField.fill(phone);

        selectIndustry(industry);

        if (email != null && !email.isEmpty()) {
            fillUserEmail(email);
        }

        System.out.println("[ApplyNowPopupActions] Filled user details: Name=" + name + ", Phone=" + phone + ", Industry=" + industry + ", Email=" + email);
    }
    public void fillUserEmail(String email) {
        if (email == null || email.isEmpty()) return;

        Locator emailField = page.locator(locators.userEmailField);
        waitForVisible(emailField);
        emailField.fill(email);

        System.out.println("[ApplyNowPopupActions] Filled email: " + email);
    }



    public void selectIndustry(String industry) {
        if (industry == null || industry.isEmpty()) {
            System.out.println("[ApplyNowPopupActions] Industry not provided, skipping selection.");
            return;
        }

        Locator dropdown = page.locator(locators.industryDropdown);
        waitForVisible(dropdown);
        dropdown.click();

        Locator option = page.locator("//label[contains(@class,'multi-dropdown-search-option-item') and normalize-space(text())='" + industry + "']");
        waitForVisible(option);
        option.click();

        page.keyboard().press("Escape");
        System.out.println("[ApplyNowPopupActions] Industry selected: " + industry);
    }

    public void clickPopupApplyNow() {
        Locator popupApplyNowBtn = page.locator(locators.popupApplyNowBtn);
        waitForVisible(popupApplyNowBtn, 15000);
        popupApplyNowBtn.click();
        System.out.println("[ApplyNowPopupActions] Clicked 'Apply Now' button in popup.");
    }

    public void enterOtp(String otp, boolean isPhoneOtp) {
        if (otp == null || otp.length() != 4) {
            throw new IllegalArgumentException("OTP must be exactly 4 digits.");
        }

        for (int i = 0; i < 4; i++) {
            String selector = locators.getOtpInput(i + 1);
            Locator otpInput = page.locator(selector);
            waitForVisible(otpInput);
            otpInput.fill(String.valueOf(otp.charAt(i)));
        }

        System.out.println("[ApplyNowPopupActions] Entered OTP: " + otp + " (isPhoneOtp=" + isPhoneOtp + ")");
    }

    public void clickVerifyAndProceed() {
        Locator verifyBtn = page.locator(locators.otpVerifyAndProceedBtn);
        waitForVisible(verifyBtn, 10000);
        verifyBtn.click();
        System.out.println("[ApplyNowPopupActions] Clicked 'Verify & Proceed' after OTP entry.");
    }

    public void bookSlotAndHandleScreening(String language, String date, String time) {
        Locator languageMenu = page.locator(locators.languageMenu);

        // Check if language menu is visible (English already selected or menu not present)
        if (languageMenu.isVisible()) {
            languageMenu.click();
            Locator englishOption = page.locator("//div[@id='language']//li[normalize-space(text())='English']");
            waitForVisible(englishOption, 5000);
            englishOption.click();
            System.out.println("[ApplyNowPopupActions] Language selected: English");
        } else {
            System.out.println("[ApplyNowPopupActions] Language already selected or menu not present, skipping selection.");
        }

        // Select Date
        Locator dateSlot = page.locator(locators.getDateSlot(date));
        waitForVisible(dateSlot, 5000);
        dateSlot.click();
        System.out.println("[ApplyNowPopupActions] Date slot clicked: " + date);

        // Select Time
        Locator timeSlot = page.locator(locators.getTimeSlot(time));
        waitForVisible(timeSlot, 5000);
        timeSlot.click();
        System.out.println("[ApplyNowPopupActions] Time slot clicked: " + time);

        // Submit buttons
        Locator directSubmitBtn = page.locator(locators.bookSlotDirectSubmitBtn);
        Locator nextBtn = page.locator(locators.bookSlotNextBtn);

        // Click the appropriate button
        if (directSubmitBtn.isVisible() && directSubmitBtn.isEnabled()) {
            directSubmitBtn.click();
            System.out.println("[ApplyNowPopupActions] Clicked direct 'Submit' after slot selection.");
        } else if (nextBtn.isVisible() && nextBtn.isEnabled()) {
            nextBtn.click();
            System.out.println("[ApplyNowPopupActions] Clicked 'Next' to answer screening questions.");
            handleScreeningQuestions(); // this will click the post-screening submit
        } else {
            System.out.println("[ApplyNowPopupActions] Neither 'Direct Submit' nor 'Next' button visible after slot selection.");
        }

        // Check for "Already Interested" message
        if (isAlreadyInterestedMessageVisible()) {
            System.out.println("[ApplyNowPopupActions] 'Already Interested' message displayed. Handling accordingly.");
            // Optional: Add logic to close popup or log
        }
    }


        

    private void handleScreeningQuestions() {
        // Loop through all screening questions
        Locator questions = page.locator(locators.screeningQuestions);
        int questionCount = questions.count();

        for (int i = 0; i < questionCount; i++) {
            Locator question = questions.nth(i);
            String mandatory = question.getAttribute("data-mandatory"); // example attribute
            String type = question.getAttribute("data-type"); // objective/subjective

            // Objective question (radio/select)
            if ("objective".equalsIgnoreCase(type)) {
                Locator option = question.locator("input[type='radio'], select option");
                waitForVisible(option, 3000);
                option.first().click();
                System.out.println("[ApplyNowPopupActions] Objective question answered (Question " + (i + 1) + ")");
            }
            // Subjective question (text)
            else if ("subjective".equalsIgnoreCase(type)) {
                Locator textInput = question.locator("input[type='text'], textarea");
                waitForVisible(textInput, 3000);
                textInput.fill("Sample answer"); // Can be parameterized
                System.out.println("[ApplyNowPopupActions] Subjective question answered (Question " + (i + 1) + ")");
            }

            // Optional logging for mandatory questions
            if ("true".equalsIgnoreCase(mandatory)) {
                System.out.println("[ApplyNowPopupActions] Mandatory question handled (Question " + (i + 1) + ")");
            }
        }

        // =========================
        // Click Submit (strict-mode safe)
        // =========================
        // Only target the correct button using unique classes inside the popup
        Locator submitScreeningBtn = page.locator(
            "button.w-100.calendar-modal-custom-btn.btn.btn-primary:has-text('Submit')"
        );

        if (submitScreeningBtn.count() > 0) {
            waitForVisible(submitScreeningBtn.first(), 5000);
            submitScreeningBtn.first().click();
            System.out.println("[ApplyNowPopupActions] Submitted all screening questions.");
        } else {
            System.out.println("[ApplyNowPopupActions] Screening Submit button not found!");
        }
    }

    public boolean isAlreadyInterestedMessageVisible() {
        Locator message = page.locator(locators.alreadyInterestedMsg);
        return message.isVisible();
    }
}

