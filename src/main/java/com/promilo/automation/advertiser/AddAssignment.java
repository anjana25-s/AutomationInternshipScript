package com.promilo.automation.advertiser;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AddAssignment {
    
    private final Locator addAssignment;
    private final Locator existingAssignmentSelect;
    private final Locator viewButton;
    private final Locator deleteIcon;
    private final Locator sampleAssignment;
    private final Locator createAssignment;
    private final Locator searchTextField;
    private final Locator searchButton;
    private final Locator count;

    private final Page page;

    public AddAssignment(Page page) {
        this.page = page;

        this.addAssignment = page.locator("button[class='add-url-btn btn btn-secondary']");
        this.existingAssignmentSelect = page.locator("button[class='btn-primary px-1 py-25 rounded me-50 font-12 fw-500']");
        this.viewButton = page.locator("[alt='view']");
        this.deleteIcon = page.locator("[title='Delete']");
        this.sampleAssignment = page.locator(".btn-primary.outlined.px-2.py-50.rounded.font-14.fw-500");
        this.createAssignment = page.locator("//button[text()='Create Assignment']");
        this.searchTextField = page.locator("[placeholder='Search']");
        this.searchButton = page.locator("//button[text()='Search']");
        this.count = page.locator("[class='dropdown-toggle btn btn-primary']");
    }

    public Locator addAssignment() { return addAssignment; }
    public Locator existingAssignmentSelect() { return existingAssignmentSelect; }
    public Locator viewButton() { return viewButton; }
    public Locator deleteIcon() { return deleteIcon; }
    public Locator sampleAssignment() { return sampleAssignment; }
    public Locator createAssignment() { return createAssignment; }
    public Locator searchTextField() { return searchTextField; }
    public Locator searchButton() { return searchButton; }
    public Locator count() { return count; }
}
