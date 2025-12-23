package com.promilo.automation.mentorship.utilities;

import java.nio.file.Paths;
import java.util.Random;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.mentorship.mentor.CreateProfile;

public class CreateProfileSection {

    private Page page;
    private CreateProfile profile;

    public CreateProfileSection(Page page) {
        this.page = page;
        this.profile = new CreateProfile(page);
    }

    public void fillProfile(String firstName, String lastName, String phone, String otp,
                            String location, String gender, String experience, String mentorType,
                            String domain, String category, String course, String dob,
                            String specialization, String imagePath, String socialLink, String highlight) {

        profile.firstName().fill(firstName);
        profile.lastName().fill(lastName);

        String mobileToUse = "90000" + String.format("%05d", new Random().nextInt(100000));

        profile.mobileTextfield().fill(mobileToUse);

        // OTP
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Send OTP")).click();
        String otpToUse = (otp == null || otp.trim().isEmpty()) ? "9999" : otp;
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("mobile_otp")).fill(otpToUse);
        page.waitForTimeout(3000);
        Locator otpVerify=page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Verify OTP"));
        otpVerify.scrollIntoViewIfNeeded();
        otpVerify.click();

        // Location
        if (location != null && !location.isEmpty()) {
            profile.locationDropdown().first().click();
            page.keyboard().type(location);
            page.keyboard().press("Enter");
        }

        // Gender
        if (gender != null && !gender.isEmpty()) {
            profile.genderDropdown().first().click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(gender).setExact(true)).click();
        }

        // Experience
        if (experience != null && !experience.isEmpty()) {
            profile.experianceDropdwon().first().click();
            page.keyboard().type(experience);
            page.keyboard().press("Enter");
        }

        // Mentor Type
        if (mentorType != null && !mentorType.isEmpty()) {
            profile.typeofMentor().click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(mentorType)).click();
        }

        // Domain
        if (domain != null && !domain.isEmpty()) {
            profile.domain().click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(domain)).click();
        }

        // Category
        page.waitForTimeout(3000);
        if (category != null && !category.isEmpty()) {
            profile.categoryDropdown().click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(category)).locator("span").click();
        }

        // Course
        if (course != null && !course.isEmpty()) {
            profile.coursesDropdown().click();
            page.getByText(course).first().click();
        }

        // DOB
        if (dob != null && !dob.isEmpty()) {
            profile.dateOfBirth().first().fill(dob);
        }

        // Specialization
        if (specialization != null && !specialization.isEmpty()) {
            profile.specialization().click();
            try {
                page.keyboard().type(specialization);
                page.keyboard().press("Enter");
            } catch (Exception e) {
                page.locator("li > .select-item > .item-renderer > span").first().click();
            }
        }

        // Upload image
        if (imagePath != null && !imagePath.isEmpty()) {
            profile.uploadCampaignImage().setInputFiles(Paths.get(imagePath));
            profile.cropButton().click();
        }

        // Social links
        if (socialLink != null && !socialLink.isEmpty()) {
            profile.socialmediaLinks().click();
            profile.instagramOption().click();
            profile.pasteLinks().fill(socialLink);
        }

        // Highlight
        if (highlight != null && !highlight.isEmpty()) {
            profile.highlightTextfield().fill(highlight);
        }

        profile.saveButton().click(new Locator.ClickOptions().setForce(true));
    }
}
