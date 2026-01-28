package com.promilo.automation.courses.pageobjects;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AdvertiserMeetingObjects {

    private final Page page;

    public AdvertiserMeetingObjects(Page page) {
        this.page = page;
    }

    // Join meeting
    public Locator joinMeetingButton() {
        return page.locator("//button[text()='Join now']");
    }

    // Audio controls
    public Locator changeMicrophoneButton() {
       return page.locator("[class='flex items-center justify-center  rounded-lg']").nth(1);
    }

    public Locator videoToggleButton() {
        return page.locator("[class='flex items-center justify-center  rounded-lg']").nth(2);
    }

    // Minimized 3-dot menu
    public Locator threeDotMenu() {
        return page.locator("[class='flex items-center justify-center  rounded-lg']").nth(3);
    }

    
    
    public Locator chatButton() {
    	
    	return page.locator("[class='p-0 rounded-lg']").nth(2);
    }
    // Raise hand
    public Locator raiseHandButton() {
        return page.locator("//button[@class='flex flex-col items-center justify-center']").first();
    }

    // Chat
    public Locator chatInput() {
        return page.locator("//input[@placeholder='Write your message']");
    }

    public Locator chatSendButton() {
        return page.locator("//button[@type='submit']");
    }

    // Feedback
    public Locator feedbackEmoji() {
        return page.locator(
            "//div[contains(@class,'rounded-lg bg-red-150')]//*[name()='svg']"
        );
    }

    public Locator remarkTextarea() {
        return page.locator("[name='remark']");
    }

    public Locator starRatings() {
        return page.locator("//span[text()='★']");
    }

    public Locator submitFeedbackButton() {
        return page.locator("//button[text()='Submit']");
    }

    // Billing
    public Locator billingSecondRow() {
        return page.locator("(//tr)[2]");
    }

    public Locator billingClientName() {
        return page.locator("(//tr)[2]//div").nth(2);
    }
}
