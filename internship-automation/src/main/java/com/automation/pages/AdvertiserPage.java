package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AdvertiserPage {

    private final Page page;

    public AdvertiserPage(Page page) {
        this.page = page;
    }

    // ==================================================
    // LOGIN
    // ==================================================

    private final String loginEmailInput =
            "//input[@id='login-email']";

    private final String loginPasswordInput =
            "//input[@placeholder='Enter Password']";

    private final String signInButton =
            "//button[normalize-space()='Sign In']";

    public Locator getEmailInput() {
        return page.locator(loginEmailInput);
    }

    public Locator getPasswordInput() {
        return page.locator(loginPasswordInput);
    }

    public Locator getSignInButton() {
        return page.locator(signInButton);
    }

    // ==================================================
    // NAVIGATION
    // ==================================================

    private final String myAccountTab =
            "//span[normalize-space()='My Account']";

    private final String myProspectTab =
            "//span[normalize-space()='My Prospect']";

    private final String internshipsLink =
            "//a[normalize-space()='Internships']";

    public Locator getMyAccountTab() {
        return page.locator(myAccountTab);
    }

    public Locator getMyProspectTab() {
        return page.locator(myProspectTab);
    }

    public Locator getInternshipsLink() {
        return page.locator(internshipsLink);
    }

    // ==================================================
    // ACCEPTANCE CARD
    // ==================================================

    private final String acceptanceCard =
            "//div[contains(@class,'my-acceptance-card')]";

    private final String meetingStatus =
            acceptanceCard +
            "//span[contains(text(),'Meeting Status')]/ancestor::li//span[contains(@class,'text-primary')]";

    private final String meetingDate =
            acceptanceCard + "//p[text()='Meeting Date']/following-sibling::p";

    private final String meetingTime =
            acceptanceCard + "//p[text()='Meeting Time']/following-sibling::p";

    public Locator getMeetingStatus() {
        return page.locator(meetingStatus).first();
    }

    public Locator getMeetingDate() {
        return page.locator(meetingDate).first();
    }

    public Locator getMeetingTime() {
        return page.locator(meetingTime).first();
    }

    // ==================================================
    // APPROVE / REJECT
    // ==================================================

    private final String approveButton =
            acceptanceCard + "//button[contains(@class,'approve-btn')]";

    private final String rejectButton =
            acceptanceCard + "//button[contains(@class,'reject-btn')]";

    public Locator getApproveButtonOnCard() {
        return page.locator(approveButton).first();
    }

    public Locator getRejectButtonOnCard() {
        return page.locator(rejectButton).first();
    }

    // ==================================================
    // RESCHEDULE (INLINE CARD FLOW)
    // ==================================================

    private final String rescheduleRequest =
            "(//span[normalize-space()='Reschedule Request'])[1]";

    private final String acceptRequestButton =
            "//button[contains(@class,'accept-btn-rescheduled')]";

    private final String cancelRequestButton =
            "//button[contains(@class,'cancel-btn-rescheduled')]";
    
    private final String rejectRescheduleButton =
            "//button[contains(@class,'done-btn') and normalize-space()='Reject']";

    // Active selected slot (date + time)
    private final String rescheduledSlotText =
            "//div[contains(@class,'rescheduledSlots-time') and contains(@class,'active')]";

    public Locator getRescheduleRequest() {
        return page.locator(rescheduleRequest);
    }

    public Locator getAcceptRequestButton() {
        return page.locator(acceptRequestButton);
    }

    public Locator getCancelRequestButton() {
        return page.locator(cancelRequestButton);
    }
 
    
    public Locator getRejectRescheduleButton() {
        return page.locator(rejectRescheduleButton);
    }
    
   

    public Locator getRescheduledSlotText() {
        return page.locator(rescheduledSlotText).first();
    }

    // ==================================================
    // POPUPS (SUCCESS / CONFIRMATION)
    // ==================================================

    private final String popupMessage =
            "//div[contains(@class,'modal-content')]//p[contains(@class,'font-14')]";

    private final String doneButton =
            "//button[contains(@class,'done-btn')]";

    private final String rejectConfirmButton =
            "//button[contains(@class,'done-btn') and normalize-space()='Reject']";

    public Locator getPopupMessage() {
        return page.locator(popupMessage).first();
    }

    public Locator getDoneButton() {
        return page.locator(doneButton).first();
    }

    public Locator getRejectConfirmButton() {
        return page.locator(rejectConfirmButton);
    }

    // ==================================================
    // UTILITIES
    // ==================================================

    public void waitForReschedulePopup() {
        getRescheduledSlotText()
                .waitFor(new Locator.WaitForOptions().setTimeout(15000));
    }

    public String getRescheduledDateOnly() {
        String text = getRescheduledSlotText().innerText().trim();
        return text.split(" ")[0]; // dd/MM/yyyy
    }

    public String getRescheduledStartTimeOnly() {
        String text = getRescheduledSlotText().innerText().trim();
        String timeRange = text.split(" ", 2)[1];
        return timeRange.split("-")[0].trim();
    }
}


