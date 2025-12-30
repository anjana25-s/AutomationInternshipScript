package com.promilo.automation.internship.assignment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Page Object Model for Business user functionality in Promilo Application.
 */
public class BusinessPage {

    private final Page page;

    // Locators for Business page operations
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator signInButton;
    private final Locator myAccountMenu;
    private final Locator myProspectMenu;
    private final Locator internShipsTab;
    private final Locator callBackTab;
    private final Locator approveBtn;
    private final Locator proceedBtn;
    private final Locator rejectBtn;
    private final Locator rejectedConfirmationBtn;
    private final Locator rejectTextMessage;
    private final Locator campaignName;

    /**
     * Constructor to initialize Page and elements.
     * @param page Playwright Page object
     */
    public BusinessPage(Page page) {
        this.page = page;

        this.usernameField = page.locator("#login-email");
        this.passwordField = page.locator("//input[@placeholder='Enter Password']");
        this.signInButton = page.locator("//button[text()='Sign In']");
        this.myAccountMenu = page.locator("//span[text()='My Account']");
        this.myProspectMenu = page.locator("//span[text()='My Prospect']");
        this.internShipsTab = page.locator("//a[text()='Internships']");
        this.callBackTab = page.locator("//a[text()='Callback/ Talk to Expert']");
        this.approveBtn = page.locator("(//span[text()='Approve'])[1]");
        this.proceedBtn = page.locator("//button[text()='Proceed']");
        this.rejectBtn = page.locator("(//span[text()='Reject'])[1]");
        this.rejectedConfirmationBtn = page.locator("//button[@class='btn done-btn w-100']");
        this.rejectTextMessage=page.locator("//div[text()='Rejected']");
        this.campaignName=page.locator("(//span[text()='B2C'])[1]");
    }

    /** Fill the username field */
    public void enterUserName(String username) {
        usernameField.fill(username);
    }

    /** Fill the password field */
    public void enterPassword(String password) {
        passwordField.fill(password);
    }

    /** Click on Sign In Button */
    public void clickSignIn() {
        signInButton.click();
    }

    /** Navigate to My Account */
    public void clickMyAccount() {
        myAccountMenu.click();
    }

    /** Navigate to My Prospect tab */
    public void clickMyProspect() {
        myProspectMenu.click();
    }

    /** Navigate to Internships tab */
    public void clickInternships() {
        internShipsTab.click();
    }

    /** Navigate to Callback/ Talk to Expert tab */
    public void clickCallBack() {
        callBackTab.click();
    }

    /** Click Approve button on first request */
    public void clickApprove() {
        approveBtn.click();
        }

    /** Click Proceed button */
    public void clickProceed() {
        proceedBtn.click();
    }

    /** Click Reject button for first request */
    public void clickReject() {
        rejectBtn.click();
    }

    /** Confirm Reject action */
    public void confirmReject() {
        rejectedConfirmationBtn.click();
    }
    
    public Locator rejectTextMessage() {
    	return rejectTextMessage;
    }
    public Locator campName() {
    	return campaignName;
    }
}
