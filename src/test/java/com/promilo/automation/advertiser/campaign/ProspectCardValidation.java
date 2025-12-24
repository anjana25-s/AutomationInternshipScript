package com.promilo.automation.advertiser.campaign;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Base64;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

import org.json.JSONObject;

public class ProspectCardValidation extends Baseclass {

    @Test(dependsOnMethods = "com.promilo.automation.emailnotifications.jobapply.UserInitiatesApplyingForJob.applyForJobTestFromExcel")
    public void ProspectCardValidationTest() throws InterruptedException, IOException {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Filter Functionality | Data Driven");

        String excelPath = Paths.get(
                System.getProperty("user.dir"),
                "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx"
        ).toString();

        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }

        test.info("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {

            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);

            if (!"FilterFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("🔐 Executing: " + testCaseId + " | Email: " + email);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            Thread.sleep(3000);

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
            login.loginPasswordField().fill("Karthik@88");
            login.signInButton().click();

            System.out.println("Logged in using: fewer-produce@qtvjnqv9.mailosaur.net / Karthik@88");

            AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
            myaccount.hamburger().click();
            myaccount.myAccount().click();

            AdverstiserMyaccount prospectClick = new AdverstiserMyaccount(page);
            Thread.sleep(5000);
            prospectClick.myProspect().click();

            AdvertiserProspects prospect = new AdvertiserProspects(page);
            prospect.Jobs().click();
            Thread.sleep(4000);

            // Candidate Interest Count
            Assert.assertTrue(prospect.CandidateIntrestcount().first().isVisible(),
                    "Candidate Interest Count should be visible");

            String interestText = prospect.CandidateIntrestcount().first().textContent().trim();
            String numberOnly = interestText.replaceAll("[^0-9]", "");
            System.out.println("Candidate Interest Count: " + interestText + " | Extracted Number: " + numberOnly);

            // Profile Name
            String actualName = prospect.ProfileName().first().textContent().trim();
            System.out.println("Profile Name: " + actualName);

            String normalizedActual = actualName.replaceAll("\\s+", "").toLowerCase();
            String normalizedExpected = "Aarav Sharma".replaceAll("\\s+", "").toLowerCase();

            Assert.assertEquals(
                    normalizedActual,
                    normalizedExpected,
                    "❌ Profile Name mismatch! Expected: Aarav Sharma | Actual: " + actualName
            );

            // Campaign Status
            Assert.assertTrue(prospect.CmpaignStatus().first().isVisible(), "Campaign Status should be visible");
            String campaignStatusFull = prospect.CmpaignStatus().first().textContent().trim();
            String campaignStatus = campaignStatusFull.split(":")[1].trim();
            Assert.assertEquals(campaignStatus, "Active", "Campaign Status is not Active");
            System.out.println("Extracted Campaign Status: " + campaignStatus);

            // Campaign Info Assertions
            Assert.assertTrue(prospect.campaignInfo().first().isVisible(), "Campaign Info should be visible");
            String campaignInfoText = prospect.campaignInfo().first().textContent().trim();

            String interestShownPrefix = "Interest Shown on";
            String interestShownDate = "";
            if (campaignInfoText.contains(interestShownPrefix)) {
                int startIndex = campaignInfoText.indexOf(interestShownPrefix) + interestShownPrefix.length();
                int endIndex = campaignInfoText.indexOf("Meeting Status") > 0 ? campaignInfoText.indexOf("Meeting Status") : campaignInfoText.length();
                interestShownDate = campaignInfoText.substring(startIndex, endIndex).trim();
            }

            String meetingStatusPrefix = "Meeting Status";
            String meetingStatus = "";
            if (campaignInfoText.toLowerCase().contains(meetingStatusPrefix.toLowerCase())) {
                int statusStart = campaignInfoText.toLowerCase().indexOf(meetingStatusPrefix.toLowerCase()) + meetingStatusPrefix.length();
                int statusEnd = campaignInfoText.length();
                meetingStatus = campaignInfoText.substring(statusStart, statusEnd).trim();
            }
            Assert.assertTrue(meetingStatus.toLowerCase().contains("pending"), "❌ Meeting Status mismatch! Expected to contain 'Pending' | Actual: " + meetingStatus);

            Assert.assertTrue(campaignInfoText.contains("English"), "❌ Preferred Language mismatch!");
            Assert.assertTrue(campaignInfoText.contains("N/A"), "❌ Industry mismatch!");

            // Profile Card
            Assert.assertTrue(prospect.ProfileCard().first().isVisible(), "Profile Card should be visible");

            // Skills Section
            Assert.assertTrue(prospect.SkillsSection().first().isVisible(), "Skills section should be visible");

            String skills = prospect.SkillsSection().first().textContent().trim();
            String normalizedSkills = skills.replaceAll("[^a-zA-Z]", "").toLowerCase();
            String[] expectedSkills = {
                    "Playwright","Selenium Webdriver","Java","Javascript","Python","TestNG","JUnit","Jenkins",
                    "Docker","Postman","REST Assured","MySQL","MongoDB","GIT","Github","Communication",
                    "Problem-Solving","Debugging"
            };
            boolean atLeastOneSkillFound = false;
            for (String skill : expectedSkills) {
                String normalizedExpected1 = skill.replaceAll("[^a-zA-Z]", "").toLowerCase();
                if (normalizedSkills.contains(normalizedExpected1)) {
                    atLeastOneSkillFound = true;
                    break;
                }
            }
            Assert.assertTrue(atLeastOneSkillFound, "❌ Skills mismatch! Expected at least one matching skill. Actual Skills: " + skills);

            // Other Profile Fields
            Assert.assertTrue(prospect.Source().first().isVisible(), "Source should be visible");

            // Meeting Date
            Assert.assertTrue(prospect.MeetingDate().first().isVisible(), "Meeting Date should be visible");
            String meetingDate = prospect.MeetingDate().first().textContent().trim();
            Pattern p = Pattern.compile("(\\d{1,2})");
            Matcher m = p.matcher(meetingDate);
            String displayedDayStr = "";
            if (m.find()) displayedDayStr = m.group(1);
            else Assert.fail("❌ Could not extract the day from Meeting Date: " + meetingDate);
            int displayedDay = Integer.parseInt(displayedDayStr);
            int storedDay = Integer.parseInt(Baseclass.selectedDate.trim());
            Assert.assertEquals(displayedDay, storedDay, "❌ Meeting Date mismatch! Stored Date: " + storedDay + " | Displayed Day: " + displayedDay);

            // Meeting Time
            Assert.assertTrue(prospect.MeetingTime().first().isVisible(), "Meeting Time should be visible");
            String meetingTime = prospect.MeetingTime().first().textContent().trim();
            Pattern timePattern = Pattern.compile("(\\d{1,2}:\\d{2}\\s?[AP]M)");
            Matcher timeMatcher = timePattern.matcher(meetingTime);
            String displayedTime = "";
            if (timeMatcher.find()) displayedTime = timeMatcher.group(1).replaceAll("\\s+", " ").trim();
            else Assert.fail("❌ Could not extract time from Meeting Time: " + meetingTime);
            String storedTime = Baseclass.selectedTime.replaceAll("\\s+", " ").trim();
            Assert.assertEquals(displayedTime, storedTime, "❌ Meeting Time mismatch! Stored Time: " + storedTime + " | Displayed: " + displayedTime);

            // Candidate Details and User Profile
            Assert.assertTrue(prospect.CandidateDetails().first().isVisible(), "Candidate Details should be visible");
            Assert.assertTrue(prospect.UserProfile().first().isVisible(), "User Profile should be visible");

            // Resume Preview
            Thread.sleep(2000);
            page.locator("//span[text()='Uploaded Resume']").first().click();
            prospect.uploadedResume().first().click();
            Thread.sleep(3000);

            // 1) Capture Screenshot
            byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            String base64Image = Base64.getEncoder().encodeToString(screenshotBytes);

            // IMPORTANT: URL encode base64 for OCR.space
            String encodedBase64 = java.net.URLEncoder.encode(base64Image, java.nio.charset.StandardCharsets.UTF_8);

            // 2) Send to OCR.space
            String previewText = "";
            try {
                String apiKey = "K89550115588957";

                URL url = new URL("https://api.ocr.space/parse/image");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("apikey", apiKey);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                String postData = "base64Image=data:image/png;base64," + encodedBase64
                                + "&language=eng"
                                + "&isOverlayRequired=false";

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(postData.getBytes());
                }

                String jsonResponse = new String(conn.getInputStream().readAllBytes());
                System.out.println("🔍 OCR Response: " + jsonResponse);

                JSONObject json = new JSONObject(jsonResponse);

                if (!json.has("ParsedResults")) {
                    Assert.fail("❌ OCR.space did not return ParsedResults. Response: " + jsonResponse);
                }

                previewText = json.getJSONArray("ParsedResults")
                        .getJSONObject(0)
                        .getString("ParsedText")
                        .replaceAll("\\s+", " ")
                        .trim();

                System.out.println("✔ OCR Extracted: " + previewText);

            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail("❌ OCR failed: " + e.getMessage());
            }

            // ------------------ 3) Extract Text from Uploaded PDF ------------------
            String uploadedFilePath = "C:\\Users\\Admin\\Downloads\\Updated_Resume_With_Location.pdf";
            String resumeText = "";

            try {
                if (uploadedFilePath.endsWith(".pdf")) {
                    PDDocument document = PDDocument.load(new File(uploadedFilePath));
                    PDFTextStripper pdfStripper = new PDFTextStripper();
                    resumeText = pdfStripper.getText(document);
                    document.close();
                } else if (uploadedFilePath.endsWith(".docx")) {
                    FileInputStream fis = new FileInputStream(uploadedFilePath);
                    XWPFDocument document = new XWPFDocument(fis);
                    XWPFWordExtractor extractor = new XWPFWordExtractor(document);
                    resumeText = extractor.getText();
                    extractor.close();
                    document.close();
                } else {
                    Assert.fail("❌ Unsupported resume file format: " + uploadedFilePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail("❌ Failed to extract uploaded resume text: " + e.getMessage());
            }

            resumeText = resumeText.replaceAll("\\s+", " ").trim();

            // ------------------ 4) Compare OCR Text with PDF Text (Word by Word) ------------------
            Set<String> pdfWords = extractWords(resumeText);
            Set<String> ocrWords = extractWords(previewText);

            long matchCount = pdfWords.stream().filter(ocrWords::contains).count();
            double matchPercentage = (matchCount * 100.0) / pdfWords.size();

            System.out.println("🔍 Total PDF Words: " + pdfWords.size());
            System.out.println("🔍 Total OCR Words: " + ocrWords.size());
            System.out.println("🔍 Matching Words: " + matchCount);
            System.out.println("📊 OCR to PDF Match Percentage: " + matchPercentage + "%");

            // PASS if match > 40%
            Assert.assertTrue(matchPercentage > 40,
                    "❌ OCR text similarity too low! (" + matchPercentage + "%)");

            // ------------------ 5) Save Full Page Screenshot ------------------
            String previewPagePath = "C:\\Users\\Admin\\Downloads\\preview_fullpage.png";
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(previewPagePath))
                    .setFullPage(true)
            );

            System.out.println("✔ Full page screenshot saved: " + previewPagePath);

            // Close Preview and View Profile
            page.locator("//button[@class='btn-close']").click();
            prospect.viewProfile().first().click();
            Locator profileHeading = page.locator("//h5[text()='Profile ']"); 
            profileHeading.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            Assert.assertTrue(profileHeading.isVisible(), "'Profile' heading should be visible");
        }
    }

    // ------------------ Helper Method ------------------
    private Set<String> extractWords(String text) {
        if (text == null) return new HashSet<>();
        return Arrays.stream(text.toLowerCase().replaceAll("[^a-z0-9 ]", " ").split("\\s+"))
                .filter(w -> w.length() > 2)
                .collect(Collectors.toSet());
    }
}
