package com.promilo.automation.pageobjects.myresume;

import java.nio.file.Paths;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Hamburger {

    private final Page page;

    private final Locator Mystuff;
    private final Locator MyAccount;
    private final Locator MyResume;
    private final Locator UploadCV;
    private final Locator AutoFillWithAI;
    private final Locator ManuallyUpdate;
    private final Locator ResumePreview;
    private final Locator EditOption;
    private final Locator AddHeadline;
    private final Locator BasicDetailsFirstName;
    private final Locator AddEmployment;
    private final Locator AddEducation;
    private final Locator AddITskills;
    private final Locator AddProject;
    private final Locator AddSocialProfile;
    private final Locator AddWorksample;
    private final Locator AddWhitePaper;
    private final Locator AddPatent;
    private final Locator AddCertificate;
    private final Locator AddPresentation;
    private final Locator AddCareerProfile;
    private final Locator AddPersonalDetails;
    private final Locator resumedeleteIcon;
    private final Locator letAIDoTheWorkText;

    private final Locator CameraeditIcon;
    private final Locator proletImage;
    private final Locator uploadImageInput;
    private final Locator cropButton;

    private final Locator EditProfileIcon;
    private final Locator LinkTab;
    private final Locator ProfilePictureUrlInput;
    private final Locator SaveButton;
    private final Locator CropButton;

    private final Locator cameraOption;
    private final Locator captureButton;

    public Hamburger(Page page) {
        this.page = page;

        this.Mystuff = page.locator("//span[text()='My Stuff']");
        this.MyAccount = page.locator("//button[text()='My Account ']");
        this.MyResume = page.locator("//a[text()='My Resume']");
        this.UploadCV = page.locator("//label[@for='upload-resume']");
        this.AutoFillWithAI = page.locator("//button[text()='Auto Fill with AI']");
        this.ManuallyUpdate = page.locator("//button[text()='Manually Update']");
        this.ResumePreview = page.locator("//div[text()='Preview']");
        this.EditOption = page.locator("//span/img[@alt='Edit-option' and contains(@class, 'edit-option-heading')]");
        this.AddHeadline = page.locator("//span[text()='ADD HEADLINE']");
        this.BasicDetailsFirstName = page.locator("//input[@placeholder='First Name']");
        this.AddEmployment = page.locator("//span[text()='ADD EMPLOYMENT']");
        this.AddEducation = page.locator("//span[text()='ADD EDUCATION']");
        this.AddITskills = page.locator("//span[text()='ADD IT SKILLS']");
        this.AddProject = page.locator("//span[text()='ADD PROJECT']");
        this.AddSocialProfile = page.locator("//h5[contains(., 'Social Profile')]/span//span[text()='ADD']");
        this.AddWorksample = page.locator("//h5[contains(., 'Work Sample')]/span//span[text()='ADD']");
        this.AddWhitePaper = page.locator("//h5[contains(., 'White Paper / Research Publication / Journal Entry')]/span//span[text()='ADD']");
        this.AddPatent = page.locator("//h5[contains(., 'Patent')]/span//span[text()='ADD']");
        this.AddCertificate = page.locator("//h5[contains(., 'Certificate')]/span//span[text()='ADD']");
        this.AddPresentation = page.locator("//h5[contains(., 'Presentation')]/span//span[text()='ADD']");
        this.AddCareerProfile = page.locator("//span[text()='ADD CAREER PROFILE']");
        this.AddPersonalDetails = page.locator("//span[text()='PERSONAL DETAILS']");
        this.resumedeleteIcon = page.locator("//img[@alt='close']");
        this.letAIDoTheWorkText = page.locator("//strong[normalize-space()='Let AI do the work']");

        this.CameraeditIcon = page.locator("//img[@alt='Edit']");
        this.proletImage = page.locator("//img[@alt='prolet']");
        this.uploadImageInput = page.locator("#upload-img");
        this.cropButton = page.locator("//button[text()='Crop']");

        this.EditProfileIcon = page.locator("//img[@alt='Edit']");
        this.LinkTab = page.locator("//div[text()='Link']");
        this.ProfilePictureUrlInput = page.locator("input[placeholder='Enter Valid Url.']");
        this.SaveButton = page.locator("//input[@type='submit' and @value='SAVE']");
        this.CropButton = page.locator("//button[text()='Crop']");

        this.cameraOption = page.locator("//div[text()='Camera']");
        this.captureButton = page.locator("(//img[@alt='Camera'])[1]");
    }

    public Locator Mystuff() { return Mystuff; }
    public Locator MyAccount() { return MyAccount; }
    public Locator MyResume() { return MyResume; }
    public void UploadCV(String filePath) {
        UploadCV.setInputFiles(Paths.get(filePath));
    }
    public Locator AutoFillWithAI() { return AutoFillWithAI; }
    public Locator ManuallyUpdate() { return ManuallyUpdate; }
    public Locator ResumePreview() { return ResumePreview; }
    public Locator EditOption() { return EditOption; }
    public Locator AddHeadline() { return AddHeadline; }
    public Locator BasicDetailsFirstName() { return BasicDetailsFirstName; }
    public Locator AddEmployment() { return AddEmployment; }
    public Locator AddEducation() { return AddEducation; }
    public Locator AddITskills() { return AddITskills; }
    public Locator AddProject() { return AddProject; }
    public Locator AddSocialProfile() { return AddSocialProfile; }
    public Locator AddWorksample() { return AddWorksample; }
    public Locator AddWhitePaper() { return AddWhitePaper; }
    public Locator AddPatent() { return AddPatent; }
    public Locator AddCertificate() { return AddCertificate; }
    public Locator AddPresentation() { return AddPresentation; }
    public Locator AddCareerProfile() { return AddCareerProfile; }
    public Locator AddPersonalDetails() { return AddPersonalDetails; }
    public Locator resumedeleteIcon() { return resumedeleteIcon; }
    public Locator letAIDoTheWorkText() { return letAIDoTheWorkText; }

    public Locator CameraeditIcon() { return CameraeditIcon; }
    public Locator proletImage() { return proletImage; }
    public Locator uploadImageInput() { return uploadImageInput; }
    public Locator cropButton() { return cropButton; }

    public Locator EditProfileIcon() { return EditProfileIcon; }
    public Locator LinkTab() { return LinkTab; }
    public Locator ProfilePictureUrlInput() { return ProfilePictureUrlInput; }
    public Locator SaveButton() { return SaveButton; }
    public Locator CropButton() { return CropButton; }

    public Locator cameraOption() { return cameraOption; }
    public Locator captureButton() { return captureButton; }
}
