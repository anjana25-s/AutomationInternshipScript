package com.promilo.automation.courses.intrestspages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class FillApplication {

    private final Page page;

    // =======================
    // MY VISITS / APPLICATION CARD
    // =======================
    private final Locator visitTab;
    private final Locator campaignName;
    private final Locator submittedDate;
    private final Locator paidAmount;
    private final Locator serviceIcon;
    private final Locator serviceName;
    private final Locator location;
    private final Locator courseName;
    private final Locator statusTag;
    
    
    

    // =======================
    // SLOT BOOKING
    // =======================
    private final Locator chooseSlotText;
    private final Locator currentMonth;
    private final Locator availableDate;
    private final Locator firstTimeSlot;
    private final Locator thankYouPopUpExitIcon;

    // =======================
    // FIRST PAGE – PERSONAL DETAILS
    // =======================
    private final Locator lastName;
    private final Locator dateOfBirth;
    private final Locator genderDropdown;
    private final Locator maleOption;
    private final Locator sendOtpButton;
    private final Locator emailOtp;
    private final Locator verifyOtpButton;
    private final Locator aadhar;
    private final Locator mobileOtp;
    private final Locator nextButton;

    // =======================
    // SECOND PAGE – EDUCATION / EXPERIENCE
    // =======================
    private final Locator boardOfExamination;
    private final Locator centralBoardOption;
    private final Locator selectBoardText;
    private final Locator subOption;
    private final Locator bandScoreOption;
    private final Locator companyName;
    private final Locator jobTitle;
    private final Locator saveAndNextButton;
    private final Locator jeeMainOption;
    private final Locator reactSelectControlInput;

    // =======================
    // THIRD PAGE – ACHIEVEMENTS / UPLOAD
    // =======================
    private final Locator selectedValuesDisplay;
    private final Locator allSubOptions;
    private final Locator achievementLevelDropdown;
    private final Locator districtLevelOption;
    private final Locator uploadFileFirst;
    private final Locator uploadFileSecond;
    private final Locator declarationText;
    private final Locator incompleteWarningText;
    private final Locator declarationCheckbox;

    // =======================
    // PAYMENT PAGE
    // =======================
    private final Locator payButton;
    private final Locator payOnlineLabel;
    private final Locator paymentSuccessMessage;
    private final Locator myApplicationLink;

    // =======================
    // PAYMENT – IFRAME
    // =======================
    private final FrameLocator paymentIframe;
    private final Locator contactNumberInput;
    private final Locator phonePeOption;

    
    
 // Add Application button
    private final Locator addApplicationButton;

    // Create Application button
    private final Locator createApplicationButton;

    // Create Manually button
    private final Locator createManuallyButton;
    
    private final Locator selectedOptionCross;

    
    

    // =======================
    // CONSTRUCTOR
    // =======================
    public FillApplication(Page page) {
        this.page = page;

        visitTab = page.locator("//span[text()='My Visits']");
        campaignName = page.locator("[class='card-content-header-text fw-bolder mb-0 text-wrap pointer text-truncate']");
        submittedDate = page.locator("[class='card_detail-value']").first();
        paidAmount = page.locator("[class='card_detail-value']").nth(1);
        serviceIcon = page.locator("//img[@alt='campaign-icon']");
        serviceName = page.locator("//span[text()='Application']");
        location = page.locator("[class='card-content-sub_header-text category-text-interest-card text-wrap']");
        courseName = page.locator("[class='font-normal card-content-sub_header-text category-text-interest-card text-truncate text-wrap']");
        statusTag = page.locator("[class='interest_status-tag']");

        chooseSlotText = page.locator("[class='fw-500 font-18 text-primary mb-50']");
        currentMonth = page.locator("[class='flatpickr-current-month']");
        availableDate = page.locator("span.flatpickr-day:not(.flatpickr-disabled)").first();
        firstTimeSlot = page.locator("//li[@class='time-slot-box list-group-item']").first();
        thankYouPopUpExitIcon = page.locator("//img[@alt='closeIcon Ask us']");

        lastName = page.locator("#lastName");
        dateOfBirth = page.locator("#dateOfBirth");
        genderDropdown = page.locator(".react-select__input-container");
        maleOption = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Male").setExact(true));
        sendOtpButton = page.locator("//button[text()='Send OTP']").first();
        emailOtp = page.locator("#email_otp");
        verifyOtpButton = page.locator("//button[text()='Verify OTP']").first();
        aadhar = page.locator("#aadhar");
        mobileOtp = page.locator("#mobile_otp");
        nextButton = page.locator(".next-btn");

        boardOfExamination = page.locator("#boardOfExamination");
        centralBoardOption = page.getByText("Central Board of Secondary");
        selectBoardText = page.getByText("Select Board of Examination");
        subOption = page.locator(".sub-option");
        bandScoreOption = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Band Score (Language Tests)"));
        companyName = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Company Name"));
        jobTitle = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Job Title"));
        saveAndNextButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save & Next next-arrow"));
        jeeMainOption = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("JEE Main"));
        reactSelectControlInput = page.locator(".react-select__control.css-13cymwt-control .react-select__input-container");

        selectedValuesDisplay = page.locator(".selected-values-display");
        allSubOptions = page.locator(".sub-option");
        achievementLevelDropdown = page.locator(".react-select__input-container");
        districtLevelOption = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("District Level"));
        uploadFileFirst = page.locator("//span[text()='Upload file']").first();
        uploadFileSecond = page.locator("//span[text()='Upload file']").nth(1);
        declarationText = page.getByText("I hereby declare that all the");
        incompleteWarningText = page.getByText("Please complete all required");
        declarationCheckbox = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("I hereby declare that all the"));

        payButton = page.locator("//button[text()=' Pay']").first();
        payOnlineLabel = page.locator("label").filter(new Locator.FilterOptions().setHasText("Pay Online"));
        paymentSuccessMessage = page.locator(".payment-success-main-cont");
        myApplicationLink = page.locator("//a[text()='My Application.']");

        paymentIframe = page.frameLocator("iframe");
        contactNumberInput = paymentIframe.getByTestId("contactNumber");
        phonePeOption = paymentIframe.getByTestId("screen-container")
                .locator("div")
                .filter(new Locator.FilterOptions().setHasText("PhonePe"))
                .nth(2);
        
        
        
        addApplicationButton=page.locator("//button[text()='Add Application ']");
        createApplicationButton=page.locator("//button[text()='Create Application']");
        createManuallyButton=page.locator("//button[text()='Create Manually']");
        
        this.selectedOptionCross = page.locator(".selected-option-cross");


    }

    // =======================
    // PAYMENT POPUP
    // =======================
    public Locator paymentSuccessButton(Page popup) {
        return popup.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Success"));
    }

    // =======================
    // GETTERS – ALL LOCATORS
    // =======================
    public Locator visitTab() { return visitTab; }
    public Locator campaignName() { return campaignName; }
    public Locator submittedDate() { return submittedDate; }
    public Locator paidAmount() { return paidAmount; }
    public Locator serviceIcon() { return serviceIcon; }
    public Locator serviceName() { return serviceName; }
    public Locator location() { return location; }
    public Locator courseName() { return courseName; }
    public Locator statusTag() { return statusTag; }

    public Locator chooseSlotText() { return chooseSlotText; }
    public Locator currentMonth() { return currentMonth; }
    public Locator availableDate() { return availableDate; }
    public Locator firstTimeSlot() { return firstTimeSlot; }
    public Locator thankYouPopUpExitIcon() { return thankYouPopUpExitIcon; }

    public Locator lastName() { return lastName; }
    public Locator dateOfBirth() { return dateOfBirth; }
    public Locator genderDropdown() { return genderDropdown; }
    public Locator maleOption() { return maleOption; }
    public Locator sendOtpButton() { return sendOtpButton; }
    public Locator emailOtp() { return emailOtp; }
    public Locator verifyOtpButton() { return verifyOtpButton; }
    public Locator aadhar() { return aadhar; }
    public Locator mobileOtp() { return mobileOtp; }
    public Locator nextButton() { return nextButton; }

    public Locator boardOfExamination() { return boardOfExamination; }
    public Locator centralBoardOption() { return centralBoardOption; }
    public Locator selectBoardText() { return selectBoardText; }
    public Locator subOption() { return subOption; }
    public Locator bandScoreOption() { return bandScoreOption; }
    public Locator companyName() { return companyName; }
    public Locator jobTitle() { return jobTitle; }
    public Locator saveAndNextButton() { return saveAndNextButton; }
    public Locator jeeMainOption() { return jeeMainOption; }
    public Locator reactSelectControlInput() { return reactSelectControlInput; }

    public Locator selectedValuesDisplay() { return selectedValuesDisplay; }
    public Locator allSubOptions() { return allSubOptions; }
    public Locator achievementLevelDropdown() { return achievementLevelDropdown; }
    public Locator districtLevelOption() { return districtLevelOption; }
    public Locator uploadFileFirst() { return uploadFileFirst; }
    public Locator uploadFileSecond() { return uploadFileSecond; }
    public Locator declarationText() { return declarationText; }
    public Locator incompleteWarningText() { return incompleteWarningText; }
    public Locator declarationCheckbox() { return declarationCheckbox; }

    public Locator payButton() { return payButton; }
    public Locator payOnlineLabel() { return payOnlineLabel; }
    public Locator paymentSuccessMessage() { return paymentSuccessMessage; }
    public Locator myApplicationLink() { return myApplicationLink; }

    public FrameLocator paymentIframe() { return paymentIframe; }
    public Locator contactNumberInput() { return contactNumberInput; }
    public Locator phonePeOption() { return phonePeOption; }
    public Locator addApplicationButton() {return addApplicationButton;}
    public Locator createApplicationButton() {return createApplicationButton;}
    public Locator createManuallyButton() {return createManuallyButton;}
    public Locator selectedOptionCross() {return selectedOptionCross;
    }

    
    
}
