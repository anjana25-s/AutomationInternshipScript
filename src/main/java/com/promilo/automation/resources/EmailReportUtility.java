package com.promilo.automation.resources;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Utility to email the latest TestNG emailable report (dynamic HTML file).
 */
public class EmailReportUtility {

    // Sender details
    private static final String FROM_EMAIL = "karthiktestuser42@gmail.com";
    private static final String APP_PASSWORD = "wihf lvuh gqkx bbdf";

    // Recipients
    private static final String[] RECIPIENTS = {
            "srishtiv@promilo.com",
            "tango@promilo.com",
            "chetanas@promilo.com"
    };

    public static void sendReport() {
        try {
            // -------------------- Get latest emailable report --------------------
            Path testOutputDir = Paths.get(System.getProperty("user.dir"), "test-output");
            if (!Files.exists(testOutputDir)) {
                System.err.println("❌ test-output directory not found.");
                return;
            }

            Path latestReport = Files.list(testOutputDir)
                    .filter(f -> f.getFileName().toString().startsWith("emailable-report") && f.toString().endsWith(".html"))
                    .max(Comparator.comparingLong(f -> {
                        try {
                            return Files.getLastModifiedTime(f).toMillis();
                        } catch (Exception e) {
                            return 0L;
                        }
                    }))
                    .orElse(null);

            if (latestReport == null) {
                System.err.println("❌ No emailable report found in test-output");
                return;
            }

            System.out.println("✅ Sending latest report: " + latestReport.toAbsolutePath());

            // -------------------- SMTP configuration --------------------
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.connectiontimeout", "20000");
            props.put("mail.smtp.timeout", "20000");
            props.put("mail.smtp.writetimeout", "20000");

            // -------------------- Create session --------------------
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                }
            });
            session.setDebug(true);

            // -------------------- Build email --------------------
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));

            InternetAddress[] recipientAddresses = new InternetAddress[RECIPIENTS.length];
            for (int i = 0; i < RECIPIENTS.length; i++) {
                recipientAddresses[i] = new InternetAddress(RECIPIENTS[i]);
            }
            message.setRecipients(Message.RecipientType.TO, recipientAddresses);

            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            message.setSubject("📄 Promilo Automation TestNG Report - " + timestamp);

            // -------------------- Email body --------------------
            MimeBodyPart bodyPart = new MimeBodyPart();
            String bodyHtml = "<p>Hello Team,</p>"
                    + "<p>Attached is the <b>latest TestNG emailable report</b>.</p>"
                    + "<p>Execution Time: <b>" + timestamp + "</b></p>"
                    + "<p>Regards,<br/>Automation Framework</p>";
            bodyPart.setContent(bodyHtml, "text/html");

            // -------------------- Attachment --------------------
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(latestReport.toFile());

            // Combine body + attachment
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);

            // -------------------- Send email --------------------
            Transport.send(message);
            System.out.println("✅ TestNG emailable report emailed successfully.");

        } catch (Exception e) {
            System.err.println("❌ Failed to send TestNG report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
