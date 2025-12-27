package com.promilo.automation.resources;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.mentor.mybilling.MentorMyBilling;
import com.promilo.automation.mentorship.mentee.MentorshipFormComponents;
import com.promilo.automation.pageobjects.myresume.Hamburger;

public class AddFundsUtility {

    /**
     * Reusable Add Funds flow.
     * Assumption:
     * - User is already logged in
     * - Same Page/session is active
     */
    public static void addFundsInSameSession(
            Page page,
            String invoiceName,
            String street1,
            String street2,
            String pincode,
            String gst,
            String pan,
            String contactNumber) {

        // ===== Navigation =====
        Hamburger billing = new Hamburger(page);
        billing.Mystuff().click();
        billing.MyAccount().click();

        MentorMyBilling billingValidation = new MentorMyBilling(page);
        billingValidation.myBillingButton().click();

        // ===== Payment Settings =====
        page.locator("//button[text()='Payment Settings']").click();
        page.locator("//button[text()='Edit']").first().click();

        // ===== Invoice Form =====
        MentorshipFormComponents form = new MentorshipFormComponents(page);
        form.InvoiceNameField().fill(invoiceName);
        form.StreetAdress1().fill(street1);
        form.StreetAdress2().fill(street2);
        form.pinCode().fill(pincode);
        form.yesRadrioBox().click();
        form.gstNumber().fill(gst);
        form.panNumber().fill(pan);

        page.getByRole(AriaRole.CHECKBOX).check();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();

        // ===== Add Funds =====
        page.locator("//button[text()='Add Funds']").click();
        page.waitForTimeout(2000);

        page.locator("[class='amount-input form-control']").first().fill("100000");
        page.locator("[class='save-btn w-100 mt-2 mt-md-0 btn btn-secondary']").click();

        // ===== Payment Gateway =====
        FrameLocator iframe = page.frameLocator("iframe");
        iframe.getByTestId("contact-overlay-container").getByTestId("contactNumber").fill(contactNumber);

        page.waitForTimeout(2000);
        iframe.getByTestId("Wallet").click();

        Page popup = page.waitForPopup(() -> {
            iframe.getByRole(AriaRole.BUTTON, new FrameLocator.GetByRoleOptions().setName("PhonePe PhonePe")).click();
        });

        popup.waitForTimeout(2000);
        popup.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Success")).click();
    }
}
