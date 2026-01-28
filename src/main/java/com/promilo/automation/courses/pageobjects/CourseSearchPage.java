package com.promilo.automation.courses.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CourseSearchPage {

    private final Page page;

    // =======================
    // LOCATORS
    // =======================
    private final Locator coursesLink;
    private final Locator courseSearchInput;
    private final Locator fillApplicationButton;

    // =======================
    // CONSTRUCTOR
    // =======================
    public CourseSearchPage(Page page) {
        this.page = page;
        this.coursesLink = page.locator("//a[text()='Courses']");
        this.courseSearchInput = page.locator("//input[@placeholder='Search Colleges and Courses']");
        this.fillApplicationButton = page.locator("//span[text()='Fill Application']");
    }

    // =======================
    // GETTERS
    // =======================
    public Locator coursesLink() { return coursesLink; }
    public Locator courseSearchInput() { return courseSearchInput; }
    public Locator fillApplicationButton() { return fillApplicationButton; }

    // =======================
    // ACTIONS / METHODS
    // =======================
    public void searchAndSelectCourse(String courseName) {
        coursesLink.click();
        courseSearchInput.fill(courseName);
        page.keyboard().press("Enter");
        page.waitForTimeout(10000);
        fillApplicationButton.click();
    }
}
