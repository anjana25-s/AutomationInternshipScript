package com.promilo.automation.internship.utilities;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestAccountStore {

    private static final String FILE_PATH = "target/test-account.properties";

    // Save credentials to file
    public static void save(String username, String password) {
        try {
            Properties props = new Properties();
            props.setProperty("username", username);
            props.setProperty("password", password);

            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();

            try (FileOutputStream fos = new FileOutputStream(file)) {
                props.store(fos, "Saved test account");
            }

            System.out.println("✅ Signup account saved successfully: " + username);
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to save account", e);
        }
    }

    // Load credentials from file
    public static Map<String, String> get() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return null;

        try (FileInputStream fis = new FileInputStream(file)) {
            Properties props = new Properties();
            props.load(fis);
            Map<String, String> acc = new HashMap<>();
            acc.put("username", props.getProperty("username"));
            acc.put("password", props.getProperty("password"));
            return acc;
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to load saved account", e);
        }
    }
}
