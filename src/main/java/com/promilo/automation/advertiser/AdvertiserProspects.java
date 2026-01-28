package com.promilo.automation.advertiser;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AdvertiserProspects {

    private Page page;

    private final Locator ProfileCard;
    private final Locator commentButton;
    private final Locator DelegateButton;
    private final Locator ApproveButton;
    private final Locator RejectButton;
    private final Locator Reschedule;
    private final Locator ShortlistButton;
    private final Locator MeetingDate;
    private final Locator MeetingTime;
    private final Locator ProfileName;
    private final Locator ProfileLocation;
    private final Locator ThreedDotOption;
    private final Locator ExcelOption;
    private final Locator RequestforNewDownload;
    private final Locator PreviuosButton;
    private final Locator NextButton;
    private final Locator ExitIcon;
    private final Locator CandidateDetails;
    private final Locator campaignInfo;
    private final Locator UserProfile;
    private final Locator uploadedResume;
    private final Locator viewProfile;
    private final Locator SkillsSection;
    private final Locator CandidateIntrestcount;
    private final Locator ActivelyLookingforJobs;
    private final Locator CmpaignStatus;
    private final Locator Source;
    private final Locator ActiveOn;
    private final Locator Update;
    private final Locator Intrest;
    private final Locator CallbackOrTalktoExpert;
    private final Locator Jobs;
    private final Locator sumbitButton;
    private final Locator DelegateSaveButton;
    private final Locator CommentTextfield;
    private final Locator SendButton;
    private final Locator CommentCount;
    private final Locator EditComment;

    private final Locator AllDropdown;
    private final Locator All;
    private final Locator NewLeads;
    private final Locator Pendings;
    private final Locator Approved;
    private final Locator Completed;
    private final Locator ResceduleRequests;
    private final Locator Rejected;
    private final Locator Expired;
    private final Locator Cancelled;

    private final Locator FilterResult;
    private final Locator CreateFolder;
    private final Locator FolderName;
    private final Locator FolderDescription;
    private final Locator CreateButton;
    private final Locator DeleteFolder;
    private final Locator FolderCheckbox;
    private final Locator ViewDetails;
    private final Locator RenameFolder;
    private final Locator FolderAcces;
    private final Locator ShareFolderButton;
    private final Locator shortlistedbutton;
    private final Locator EditcommentButton;

    public AdvertiserProspects(Page page) {
        this.page = page;

        this.Jobs = page.locator("//a[text()='Jobs']");
        this.ProfileCard = page.locator("//div[@class='profile me-1 pointer my-acceptance-profile-pic-wrapper']");
        this.commentButton = page.locator("//span[@class='comment-text']");
        this.DelegateButton = page.locator("//span[text()='Delegate']");
        this.ApproveButton = page.locator("//span[text()='Approve']");
        this.RejectButton = page.locator("//span[text()='Reject']");
        this.Reschedule = page.locator("//span[text()='Reschedule']");
        this.ShortlistButton = page.locator("//span[text()='Shortlist']");
        this.MeetingDate = page.locator("//div[@class='student mr-1 border-right-solid']");
        this.MeetingTime = page.locator("//div[@class='student']");
        this.ProfileName = page.locator("//h2[@class='font-16 fw-bold mb-0 pt-50']");
        this.ProfileLocation = page.locator("p[class='location fw-400 font-12 mb-0']");
        this.ThreedDotOption = page.locator("button[class='three-dots']");
        this.ExcelOption = page.locator("img[class='pointer']");
        this.RequestforNewDownload = page.locator("//button[text()='Request for new Download']");
        this.PreviuosButton = page.locator("//button[text()='Previous']");
        this.NextButton = page.locator("//button[text()='Next']");
        this.ExitIcon = page.locator("img[alt='Close']");
        this.CandidateDetails = page.locator("div[class='col-5 dotted-border-right']");
        this.campaignInfo = page.locator("div[class='col-4 dotted-border-right']");
        this.UserProfile = page.locator("div[class='col-3']");
        this.uploadedResume = page.locator("//span[text()='Uploaded Resume']");
        this.SkillsSection = page.locator("div[class='row ps-1 mt-1']");
        this.CandidateIntrestcount = page.locator("//div[@class='person d-flex flex-nowrap']");
        this.ActivelyLookingforJobs = page.locator("p[class='mb-25 mx-25 d-flex flex-nowrap text-black']");
        this.CmpaignStatus = page.locator("//p[@class='mb-25 text-nowrap'][1]");
        this.Source = page.locator("//p[@class='mb-25 text-nowrap'][2]");
        this.ActiveOn = page.locator("//p[@class='mb-25 text-nowrap'][3]");
        this.Update = page.locator("//p[@class='mb-25 text-nowrap'][4]");
        this.Intrest = page.locator("//a[text()='Interest']");
        this.CallbackOrTalktoExpert = page.locator("//a[text()='Callback/ Talk to Expert']");
        this.viewProfile = page.locator("//span[text()='View Profile']");
        this.sumbitButton = page.locator("button[type='button']");
        this.DelegateSaveButton = page.locator("//input[@type='submit']");
        this.CommentTextfield = page.locator("//input[@placeholder='Add your comment']");
        this.SendButton = page.locator("//p[text()='Send']");
        this.CommentCount = page.locator("//div[@class='comment-count']");
        this.FilterResult = page.locator("//h1[text()='MY PROSPECT']");
        this.CreateFolder = page.locator("//button[text()='Create Folder']");
        this.FolderName = page.locator("//input[@placeholder='e.g. Management prospect']");
        this.FolderDescription = page.locator("input[placeholder='eg. All media running in march month']");
        this.CreateButton = page.locator("//div[text()='Create']");
        this.DeleteFolder = page.locator("//button[text()='Delete']");
        this.FolderCheckbox = page.locator("//input[@class='form-check-input']");
        this.ViewDetails = page.locator("//div[text()='View details']");
        this.RenameFolder = page.locator("//div[text()='Rename']");
        this.FolderAcces = page.locator("//div[text()='Access']");
        this.ShareFolderButton = page.locator("//div[text()='Delete']");
        this.shortlistedbutton = page.locator("//button[text()='Shortlisted']");
        this.EditComment = page.locator("div[class='comment-item']");
        this.All = page.locator("//div[text()='All']");
        this.NewLeads = page.locator("//div[text()='New Leads']");
        this.Pendings = page.locator("//div[text()='Pendings']");
        this.Approved = page.locator("//div[text()='Approved']");
        this.Completed = page.locator("//div[text()='Completed']");
        this.ResceduleRequests = page.locator("//div[text()='Reschedule Requests']");
        this.Rejected = page.locator("//div[text()='Rejected']");
        this.Expired = page.locator("//div[text()='Expired']");
        this.Cancelled = page.locator("//div[text()='Cancelled']");
        this.AllDropdown = page.locator("button[class='dropdown-toggle btn btn-primary']");
        this.EditcommentButton = page.locator("img[class='edit-button CursorPointer']");
    }

    public Locator ProfileCard() { return ProfileCard; }
    public Locator commentButton() { return commentButton; }
    public Locator DelegateButton() { return DelegateButton; }
    public Locator ApproveButton() { return ApproveButton; }
    public Locator Reschedule() { return Reschedule; }
    public Locator ShortlistButton() { return ShortlistButton; }
    public Locator MeetingDate() { return MeetingDate; }
    public Locator MeetingTime() { return MeetingTime; }
    public Locator ProfileName() { return ProfileName; }
    public Locator ProfileLocation() { return ProfileLocation; }
    public Locator ThreedDotOption() { return ThreedDotOption; }
    public Locator ExcelOption() { return ExcelOption; }
    public Locator RequestforNewDownload() { return RequestforNewDownload; }
    public Locator PreviuosButton() { return PreviuosButton; }
    public Locator NextButton() { return NextButton; }
    public Locator ExitIcon() { return ExitIcon; }
    public Locator CandidateDetails() { return CandidateDetails; }
    public Locator campaignInfo() { return campaignInfo; }
    public Locator UserProfile() { return UserProfile; }
    public Locator uploadedResume() { return uploadedResume; }
    public Locator SkillsSection() { return SkillsSection; }
    public Locator CandidateIntrestcount() { return CandidateIntrestcount; }
    public Locator ActivelyLookingforJobs() { return ActivelyLookingforJobs; }
    public Locator CmpaignStatus() { return CmpaignStatus; }
    public Locator Source() { return Source; }
    public Locator ActiveOn() { return ActiveOn; }
    public Locator Update() { return Update; }
    public Locator Intrest() { return Intrest; }
    public Locator CallbackOrTalktoExpert() { return CallbackOrTalktoExpert; }
    public Locator viewProfile() { return viewProfile; }
    public Locator AllDropdown() { return AllDropdown; }
    public Locator Jobs() { return Jobs; }
    public Locator RejectButton() { return RejectButton; }
    public Locator sumbitButton() { return sumbitButton; }
    public Locator DelegateSaveButton() { return DelegateSaveButton; }
    public Locator CommentTextfield() { return CommentTextfield; }
    public Locator SendButton() { return SendButton; }
    public Locator CommentCount() { return CommentCount; }
    public Locator FilterResult() { return FilterResult; }
    public Locator CreateFolder() { return CreateFolder; }
    public Locator FolderName() { return FolderName; }
    public Locator FolderDescription() { return FolderDescription; }
    public Locator CreateButton() { return CreateButton; }
    public Locator DeleteFolder() { return DeleteFolder; }
    public Locator FolderCheckbox() { return FolderCheckbox; }
    public Locator ViewDetails() { return ViewDetails; }
    public Locator RenameFolder() { return RenameFolder; }
    public Locator FolderAcces() { return FolderAcces; }
    public Locator ShareFolderButton() { return ShareFolderButton; }
    public Locator shortlistedbutton() { return shortlistedbutton; }
    public Locator All() { return All; }
    public Locator NewLeads() { return NewLeads; }
    public Locator Pendings() { return Pendings; }
    public Locator Completed() { return Completed; }
    public Locator Approved() { return Approved; }
    public Locator ResceduleRequests() { return ResceduleRequests; }
    public Locator Rejected() { return Rejected; }
    public Locator Expired() { return Expired; }
    public Locator Cancelled() { return Cancelled; }
    public Locator EditComment() { return EditComment; }
    public Locator EditcommentButton() { return EditcommentButton; }
}
