package com.promilo.automation.resources;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AIVoiceBotAutoCloser {

    private static final String AI_BOT_CLOSE_ICON =
            "//img[@src='/assets/closeMiliIcon-96dcded9.svg']";

    private static volatile boolean isRunning = false;

    /**
     * Starts background watcher that closes AI voice bot whenever it appears.
     * Call this ONCE after page creation.
     */
    public static void start(Page page) {

        if (isRunning) return; // prevent multiple threads
        isRunning = true;

        Thread botWatcher = new Thread(() -> {
            while (true) {
                try {
                    Locator closeIcon = page.locator(AI_BOT_CLOSE_ICON);

                    if (closeIcon.count() > 0 && closeIcon.first().isVisible()) {
                        closeIcon.first().click();
                        page.waitForTimeout(300);
                        System.out.println("🤖 AI Voice Bot auto-closed");
                    }

                    Thread.sleep(2000); // check every 2 seconds
                } catch (Exception ignored) {
                }
            }
        });

        botWatcher.setDaemon(true); // stops when test ends
        botWatcher.start();
    }
}
