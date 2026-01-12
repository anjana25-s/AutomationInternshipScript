package com.automation.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestAccountStore {

    private static final String FILE_PATH = "target/test-account.properties";

    // ================= SAVE =================
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

            System.out.println("✅ Test account saved to file");

        } catch (Exception e) {
            throw new RuntimeException("Failed to save test account", e);
        }
    }

    // ================= LOAD =================
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
            throw new RuntimeException("Failed to load test account", e);
        }
    }

    // ================= CHECK =================
    public static boolean exists() {
        return new File(FILE_PATH).exists();
    }
}


