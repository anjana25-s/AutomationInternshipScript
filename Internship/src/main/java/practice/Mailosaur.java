package practice;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Mailosaur {
    private final Page page;

    private final Locator mailId;
    private final Locator password;
    private final Locator loginButton;
    private final Locator continueButton;

    public Mailosaur(Page page) {
        this.page = page;

        // Initializing locators
        this.mailId = page.locator("#email");
        this.password = page.locator("#password");
        this.loginButton = page.locator("//button[@type='submit']");
        this.continueButton = page.locator("//button[text()='Continue']");
    }

    // Getter methods for locators
    public Locator mailId() {
        return mailId;
    }

    public Locator password() {
        return password;
    }

    public Locator loginButton() {
        return loginButton;
    }

    public Locator continueButton() {
        return continueButton;
    }

    
   
}