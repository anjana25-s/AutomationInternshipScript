package com.promilo.automation.pageobjects.emailnotifications.advertiser.gethrcall;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GetHrCallNotification {

    private final Locator greetingText;
    private final Locator regardingText;
    private final Locator excitingText;
    private final Locator receiveIntrest;
    private final Locator clickLinkText;
    private final Locator candidateCard;
    private final Locator myProspectCard;
    private final Locator promiloLogo1;
    private final Locator promiloLogo2;

    private final Page page;

    public GetHrCallNotification(Page page) {
        this.page = page;
        this.greetingText = page.locator("//span[contains(text(), 'Dear ')]");
        this.regardingText = page.locator("//p[text()='This is regarding your ']");
        this.excitingText = page.locator("(//p)[2]");
        this.receiveIntrest = page.locator("//p[contains(text(), 'We have received interest')]"); // ✅ Added missing initialization
        this.clickLinkText = page.locator("(//p)[4]");
        this.candidateCard = page.locator("//table[@style='  background-color: white;width: 100%;border: 1px solid #cccccc;border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); display: flex; flex-direction: column; gap: 10px;']");
        this.myProspectCard = page.locator("//span[@style='word-break: break-word; line-height: 32px;']");
        this.promiloLogo1 = page.locator("//a[@href='https://promilo.com/public/home']//img[@height='auto']");
        this.promiloLogo2 = page.locator("//div[@align='left']//div//img[@height='auto']");
    }

    public Locator greetingText() { return greetingText; }
    public Locator regardingText() { return regardingText; }
    public Locator receiveIntrest() { return receiveIntrest; }
    public Locator excitingText() { return excitingText; }
    public Locator clickLinkText() { return clickLinkText; }
    public Locator candidateCard() { return candidateCard; }
    public Locator myProspectCard() { return myProspectCard; }
    public Locator promiloLogo1() { return promiloLogo1; }
    public Locator promiloLogo2() { return promiloLogo2; }
}
