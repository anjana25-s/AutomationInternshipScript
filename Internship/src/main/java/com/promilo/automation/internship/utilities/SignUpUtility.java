package com.promilo.automation.internship.utilities;

import java.util.Random;

public class SignUpUtility {

    // Generate Random Email ID
    public static String generateRandomEmail() {
        return "test_" + System.currentTimeMillis()
                + (int)(Math.random() * 100) + "@yopmail.com";
    }

    // Generate Random Mobile Number
    public static String generateRandomMobile() {
        String prefix = "900002";
        StringBuilder mobile = new StringBuilder(prefix);

        for (int i = 0; i < 4; i++) {
            mobile.append((int)(Math.random() * 10));
        }
        return mobile.toString();
    }

    // OTP
    public static String getFixedOtp() {
        return "9999";
    }

    public static String generateInvalidOtp() {
        String otp;
        do {
            otp = String.valueOf((int)(Math.random() * 9000) + 1000);
        } while (otp.equals(getFixedOtp()));
        return otp;
    }

    public static String generateRandomPassword() {
        String chars = "Abc@123XYZ";
        StringBuilder password = new StringBuilder();
        Random r = new Random();

        for (int i = 0; i < 10; i++) { // ✅ length = 10
            password.append(chars.charAt(r.nextInt(chars.length())));
        }
        return password.toString();
    }
}
