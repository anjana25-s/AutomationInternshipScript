package com.promilo.automation.internship.assignment;

public class SignUpUtility {

   
    // Generate Random Email ID
   public static String generateRandomEmail() {
        return "test_" + System.currentTimeMillis() +
                (int)(Math.random() * 100) + "@yopmail.com";
    }

    
    //  Generate Random Mobile Number (Starts with 900002)
     public static String generateRandomMobile() {
        String prefix = "900002";  
        StringBuilder mobile = new StringBuilder(prefix);

        // Remaining 4 random digits → Total = 10 digits
        for (int i = 0; i < 4; i++) {
            mobile.append((int)(Math.random() * 10));
        }

        return mobile.toString();
        }

     // OTP - Always Fixed Value 9999
    public static String getFixedOtp() {
    	
    	String otp="9999";
		return otp;
    }
    
    // Generate Invalid OTP 
    public static String generateInvalidOtp() {
        String otp;
        do {
            int randomOtp = (int) (Math.random() * 9000) + 1000; 
            otp = String.valueOf(randomOtp);
        } while (otp.equals(getFixedOtp()));
        return otp;
    
}
}