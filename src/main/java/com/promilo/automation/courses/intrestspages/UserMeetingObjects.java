package com.promilo.automation.courses.intrestspages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class UserMeetingObjects {

    private final Page page;

    public UserMeetingObjects(Page page) {
        this.page = page;
    }

    // Navigation
    public Locator myMeetingsTab() {
        return page.locator("//span[normalize-space()='My Meetings']");
    }

    public Locator allMeetingsTab() {
        return page.locator("//div[text()='All']");
    }

    public Locator joinNowCardButton() {
        return page.locator("//span[text()='Join Now']").first();
    }

    public Locator joinNowPopupButton() {
        return page.locator("//button[text()='Join now']");
    }

    // Controls
    public Locator audioMuteButton() {
        return page.locator("[class='flex items-center justify-center  rounded-lg']").nth(1);
    }

    public Locator raiseHandButton() {
        return page.locator("//button[@class='flex flex-col items-center justify-center']").first();
    }

    // Chat
    public Locator chatOpenButton() {
        return page.locator(
            "//div[@style='opacity: 1; transform: scale(1); transition: 200ms linear;']"
        ).nth(5);
    }

    public Locator viewChatButton() {
        return page.locator(".md\\:flex > div:nth-child(2) > div").first();
    }

    public Locator chatTextBox() {
        return page.getByRole(
            AriaRole.TEXTBOX,
            new Page.GetByRoleOptions().setName("Write your message")
        );
    }

    public Locator chatSendButton() {
        return page.locator("//button[@type='submit']");
    }
}
