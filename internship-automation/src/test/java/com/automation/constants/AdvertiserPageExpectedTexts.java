package com.automation.constants;

public class AdvertiserPageExpectedTexts {

    // ================= COMMON BUTTONS =================
    public static final String DONE_BUTTON = "Done";
    public static final String ACCEPT_REQUEST_BUTTON = "Accept Request";
    public static final String CANCEL_REQUEST_BUTTON = "Cancel Request";
    public static final String REJECT_BUTTON = "Reject";

    // ================= MEETING STATUS =================
    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_ACCEPTED = "Accepted";
    public static final String STATUS_REJECTED = "Rejected";

    // ================= APPROVE SUCCESS =================
    public static final String APPROVE_SUCCESS_MESSAGE =
            "Your meeting is approved, Please join at the scheduled time.";

    // ================= RESCHEDULE REQUEST POPUP =================
    public static final String RESCHEDULE_HEADER =
            "Reschedule the Meeting";

    public static final String RESCHEDULE_SUB_HEADER =
            "User has requested reschedule . Please accept the request for this slot.";

    public static final String RESCHEDULE_NOTE =
            "Accepting or cancelling the reschedule request will also result in the acceptance or cancellation of the original meeting.";

    // ================= RESCHEDULE ACCEPT SUCCESS =================
    public static final String RESCHEDULE_ACCEPT_SUCCESS_MESSAGE =
            "Thanks for Accepting this reschedule request. Your meeting is confirmed.";

    // ================= RESCHEDULE REJECT SUCCESS =================
    public static final String RESCHEDULE_REJECT_SUCCESS_TITLE =
            "Rejected";

    public static final String RESCHEDULE_REJECT_SUCCESS_MESSAGE =
            "You have Successfully rejected user's reschedule request. Your meeting is rejected";

    // ================= INTEREST REJECT CONFIRM =================
    public static final String INTEREST_REJECT_CONFIRM_TITLE =
            "Are you sure?";

    public static final String INTEREST_REJECT_CONFIRM_MESSAGE =
            "You want to reject Interest request.";
}
