package com.automation.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class TestAccountSave {

    private static final String FOLDER = "test-data";
    private static final String FILE_PATH = FOLDER + "/account-data.json";

    // Ensure folder exists
    private static void ensureFolder() {
        File dir = new File(FOLDER);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // Load file safely
    private static Map<String, Object> loadFile() {
        try {
            FileReader reader = new FileReader(FILE_PATH);
            return new Gson().fromJson(reader, Map.class);
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    // Save new user account
    public static void saveAccount(String email, String password) {
        try {
            ensureFolder();
            Map<String, Object> data = loadFile();

            data.put("email", email);
            data.put("password", password);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(FILE_PATH);
            gson.toJson(data, writer);
            writer.close();

            System.out.println("✔ Saved Account: " + email);

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to save account", e);
        }
    }

    // Load saved account
    public static Map<String, String> loadLastAccount() {
        Map<String, Object> data = loadFile();

        if (!data.containsKey("email")) return null;

        Map<String, String> acc = new HashMap<>();
        acc.put("email", (String) data.get("email"));
        acc.put("password", (String) data.get("password"));

        return acc;
    }

    // Save internship
    public static void saveAppliedInternship(String internshipName) {
        try {
            ensureFolder();
            Map<String, Object> data = loadFile();

            data.put("internshipName", internshipName);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(FILE_PATH);
            gson.toJson(data, writer);
            writer.close();

            System.out.println("✔ Saved Internship: " + internshipName);

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to save internship", e);
        }
    }

    // Load internship
    public static String loadAppliedInternship() {
        Map<String, Object> data = loadFile();
        return (String) data.get("internshipName");
    }
}

