package com.promilo.automation.mentorship.datavalidation.objects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DataValidationObjects {

    private final Page page;

    // ===================== HEADER & HERO SECTION =====================
    private final Locator growYourBrandWithPopularPersonalities;
    private final Locator secureABrandEndorsement;
    private final Locator enableUpdatesImportantInformationWhatsapp;
    private final Locator byProceedingAheadYouExpresslyAgreeToPromilo;
    private final Locator alreadyHaveAnAccountLogin;

    // ===================== MIDDLE CONTENT =====================
    private final Locator thanksForGivingYourInformation;
    private final Locator otpVerification;
    private final Locator enterThe4DigitVerificationCode;
    private final Locator stillCantFindTheOtp;
    private final Locator descriptionElementsList;
    private final Locator descriptionValidation1;
    private final Locator descriptionValidation2;

    // ===================== THANK YOU POPUP =====================
    private final Locator thankYouForRegisteringRequestingBrandEndorsement;
    private final Locator easyAccessToYourSelections;

    // ===================== MY INTEREST CARD =====================
    private final Locator mentorName;
    private final Locator mentorData;
    private final Locator experienceLabel;
    private final Locator experienceValue;
    private final Locator locationValue;
    private final Locator serviceName;

    public DataValidationObjects(Page page) {
        this.page = page;

        // ---------- HEADER / HERO ----------
        this.growYourBrandWithPopularPersonalities =
                page.locator("//div[@class='d-block d-md-none ']");
        
        this.secureABrandEndorsement =
                page.locator("//h3[@class='feature-content-header']");
        
        this.enableUpdatesImportantInformationWhatsapp =
                page.locator("//label[text()='Enable updates & important information on Whatsapp']");
        
        this.byProceedingAheadYouExpresslyAgreeToPromilo =
                page.locator("//p[text()='By proceeding ahead you expressly agree to the Promilo']");
        
        this.alreadyHaveAnAccountLogin =
                page.locator("[class='text-center']");

        // ---------- MIDDLE CONTENT ----------
        this.thanksForGivingYourInformation =
                page.locator("[class='text-primary mb-2 text-center']");
        
        this.otpVerification =
                page.locator("//h5[text()='OTP Verification']");
        
        this.enterThe4DigitVerificationCode =
                page.locator("//p[@class=' text-center']");
        
        this.stillCantFindTheOtp =
                page.locator("//p[text()='Still can’t find the OTP?']");
        
        this.descriptionElementsList =
                page.locator("[class='description text-center fw-400 font-13']");

        // ---------- THANK YOU POPUP ----------
        this.thankYouForRegisteringRequestingBrandEndorsement =
                page.locator("[class='ThankYou-message text-center text-blue-600 pt-2 ']");
        
        this.easyAccessToYourSelections =
                page.locator("[class='text-center ThankYou-footer']");

        // ---------- MY INTEREST CARD ----------
        this.mentorName =
                page.locator("(//h5[@class='title mb-0'])[1]");
        
        this.mentorData =
                page.locator("(//p[@class='color-grey mb-0'])[1]");
        
        this.experienceLabel =
                page.locator("(//span[text()='Experience'])[1]");
        
        this.experienceValue =
                page.locator("(//span[@class='color-grey'])[1]");
        
        this.locationValue =
                page.locator("(//span[@class='location-text'])[1]");
        
        this.serviceName =
                page.locator("(//span[@class='service-name'])[1]");
        this.descriptionValidation1=
        		page.locator("[class='text-center mx-1 font-16']").first();
        
        this.descriptionValidation2=
        		page.locator("[class='text-center mx-1 font-16']").nth(1);
    }

    // ---------------- GETTERS ----------------
    public Locator growYourBrandWithPopularPersonalities() { return growYourBrandWithPopularPersonalities; }
    public Locator secureABrandEndorsement() { return secureABrandEndorsement; }
    public Locator enableUpdatesImportantInformationWhatsapp() { return enableUpdatesImportantInformationWhatsapp; }
    public Locator byProceedingAheadYouExpresslyAgreeToPromilo() { return byProceedingAheadYouExpresslyAgreeToPromilo; }
    public Locator alreadyHaveAnAccountLogin() { return alreadyHaveAnAccountLogin; }

    public Locator thanksForGivingYourInformation() { return thanksForGivingYourInformation; }
    public Locator otpVerification() { return otpVerification; }
    public Locator enterThe4DigitVerificationCode() { return enterThe4DigitVerificationCode; }
    public Locator stillCantFindTheOtp() { return stillCantFindTheOtp; }
    public Locator descriptionElementsList() { return descriptionElementsList; }
    public Locator descriptionValidation1() {return descriptionValidation1;}
    public Locator descriptionValidation2() {return descriptionValidation2;}

    public Locator thankYouForRegisteringRequestingBrandEndorsement() { return thankYouForRegisteringRequestingBrandEndorsement; }
    public Locator easyAccessToYourSelections() { return easyAccessToYourSelections; }

    public Locator mentorName() { return mentorName; }
    public Locator mentorData() { return mentorData; }
    public Locator experienceLabel() { return experienceLabel; }
    public Locator experienceValue() { return experienceValue; }
    public Locator locationValue() { return locationValue; }
    public Locator serviceName() { return serviceName; }
}
