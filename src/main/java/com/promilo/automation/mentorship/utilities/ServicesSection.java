package com.promilo.automation.mentorship.utilities;

import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.mentorship.mentor.ServicesPage;

public class ServicesSection {

    private Page page;
    private ServicesPage services;

    public ServicesSection(Page page) {
        this.page = page;
        this.services = new ServicesPage(page);
    }

    public void addServices(String service1Name, String service1Fee, String service2Name, String service2Fee,
                            String service3Name, String service3Fee, String service4Name, String service4Fee,
                            String service5Name, String service5Fee, String service6Name, String service6Fee,
                            String imagePath) {

        // Implement same logic as original code for all services
        // Service 1
        if (service1Name != null && !service1Name.isEmpty()) {
            services.oneonecall().click();
            services.serviceName().fill(service1Name);
            page.locator(".rmsc.service-react-multi-select .dropdown-heading").click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("English")).click();
            if (service1Fee != null && !service1Fee.isEmpty()) services.serviceFee().fill(service1Fee);
            services.evaluate().click();
            services.addService().click();
        }

        // Service 2 - Video Call
        if (service2Name != null && !service2Name.isEmpty()) {
            services.videoCall().click();
            services.serviceName().fill(service2Name);
            page.locator(".rmsc.service-react-multi-select .dropdown-heading").click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("English")).click();

           page.waitForTimeout(4000);
            Locator inputContainer =  page.locator("#meetup_duration > .react-select__control > .react-select__value-container > .react-select__input-container");
            inputContainer.scrollIntoViewIfNeeded();
            inputContainer.click();
            page.waitForTimeout(3000);
            
            Locator slotSelect=page.getByText("15 mins", new Page.GetByTextOptions().setExact(true));
            slotSelect.scrollIntoViewIfNeeded();
            slotSelect.click(new Locator.ClickOptions().setForce(true));

            services.calenderButton().click();
            services.Monday().click();

            LocalTime time = LocalTime.of(0, 0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

            for (int slot = 1; slot <= 10; slot++) {
                services.plusIcon().first().click();
                page.waitForTimeout(1000);
                Locator timeFields = page.locator("//div[@class='time-slot-select__input-container css-19bb58m']");
                int fieldCount = timeFields.count();
                timeFields.nth(fieldCount - 1).click();
                String formattedTime = time.format(formatter).toLowerCase();
                page.keyboard().type(formattedTime);
                page.keyboard().press("Enter");
                page.waitForTimeout(700);
                time = time.plusMinutes(15);
            }

            services.submitButton().click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Service Fee")).fill(service2Fee);
            services.evaluate().click();
            services.addService().click();
        }

        // Similarly, add services 3-6 logic (same as original)
        // Service 3
        if (service3Name != null && !service3Name.isEmpty()) {
            services.askQuery().click();
            services.serviceName().fill(service3Name);
            services.serviceFee().fill(service3Fee);
            services.evaluate().click();
            page.waitForTimeout(3000);
            page.locator("#reply_duration > .react-select__control > .react-select__value-container > .react-select__input-container").click();
            page.keyboard().type("2");
            page.keyboard().press("Enter");
            services.addService().click();
        }

        // Service 4
        if (service4Name != null && !service4Name.isEmpty()) {
            services.resources().click();
            services.serviceName().fill(service4Name);
            if (service4Fee != null && !service4Fee.isEmpty()) services.serviceFee().fill(service4Fee);
            services.evaluate().click();
            if (imagePath != null && !imagePath.isEmpty()) services.uploadFile().setInputFiles(Paths.get(imagePath));
            services.addService().click();
        }

        // Service 5
        if (service5Name != null && !service5Name.isEmpty()) {
            services.brandEndoursement().click();
            services.serviceName().fill(service5Name);
            services.addService().click();
        }

        // Service 6
        if (service6Name != null && !service6Name.isEmpty()) {
            services.personalizedVideo().click();
            services.serviceName().fill(service6Name);
            if (service6Fee != null && !service6Fee.isEmpty()) services.serviceFee().fill(service6Fee);
            services.evaluate().click();
            services.videoDuration().click();
            page.keyboard().type("5");
            page.keyboard().press("Enter");
            services.deliveryTime().click();
            page.keyboard().type("3");
            page.keyboard().press("Enter");
            services.addService().click();
        }

        // Save & Next
        services.saveAndNextButton().nth(2).click();
    }
}
