package com.promilo.automation.resources;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ToasterUtil {
    private final Page page;
    private final Locator successToaster;

    // Constructor
    public ToasterUtil(Page page) {
        this.page = page;
        this.successToaster = page.locator("//div[@role='status']");
    }

    // Getter method for the success toaster
    public Locator getSuccessToaster() {
        return successToaster;
    }
}
