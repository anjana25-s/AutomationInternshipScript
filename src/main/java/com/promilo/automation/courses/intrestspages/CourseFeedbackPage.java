package com.promilo.automation.courses.intrestspages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CourseFeedbackPage {

    private Page page;

    public CourseFeedbackPage(Page page) {
        this.page = page;
    }

    // Navigation
    public Locator coursesTab() {
        return page.locator("//a[text()='Courses']");
    }

    public Locator seeMoreButton() {
        return page.locator("//a[text()='See More']").nth(1);
    }

    public Locator lpuCourseCard() {
        return page.locator("//div[text()='LPU']").first();
    }

    // Feedback form
    public Locator feedbackTextarea() {
        return page.locator("//textarea[@placeholder='Write a feedback']");
    }

    public Locator submitFeedbackButton() {
        return page.locator("//button[text()='Submit']");
    }

    public Locator userNameField() {
        return page.locator("//input[@name='userName']").nth(1);
    }

    public Locator userEmailField() {
        return page.locator("//input[@id='userEmail']").nth(1);
    }

    public Locator userMobileField() {
        return page.locator("//input[@name='userMobile']").nth(1);
    }

    public Locator finalSubmitButton() {
        return page.locator("//button[text()='Submit']").nth(1);
    }

    // OTP
    public Locator otpDigit(int index) {
        return page.locator("//input[@aria-label='Please enter OTP character " + index + "']");
    }

    public Locator verifyAndProceedButton() {
        return page.locator("//button[text()='Verify & Proceed']");
    }

    // Thank you popup
    public Locator thankYouPopup() {
        return page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
    }

    public Locator thankYouCloseIcon() {
        return page.locator("img[alt='closeIcon Ask us']");
    }

    // Notifications
    public Locator notificationIcon() {
        return page.locator("//img[@alt='In-App-Notification-Icon']").nth(1);
    }

    public Locator thankYouNotificationText() {
        return page.locator("//h6[contains(text(),'Thank You for Your Feedback!')]");
    }

    // Mailosaur email content
    public Locator feedbackEmailCard() {
        return page.locator("//p[text()='Thank You for Your Valuable Feedback! 🎉']").first();
    }

    public Locator emailGreeting() {
        return page.locator("//span[contains(text(),'Hi ')]");
    }

    public Locator emailThankYouText() {
        return page.locator("//p[contains(text(),'Thank you for sharing your')]");
    }

    public Locator emailWhatsNext() {
        return page.locator("//strong[contains(text(),'What’s')]");
    }

    public Locator emailBodyText() {
        return page.locator("//p[contains(text(),'We')]");
    }

    public Locator emailFooterText() {
        return page.locator("//p[contains(text(),'In the meantime, keep')]");
    }
}
