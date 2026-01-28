package visualtesting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class VisualTestingWithDiffPercentage {

    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    private final String screenshotsFolder = "C:\\Temp\\Bandicam";

    @BeforeClass
    public void setUp() {
        File folder = new File(screenshotsFolder);
        if (!folder.exists()) folder.mkdirs();

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
    }

    @Test
    public void homePageVisualTest() throws IOException, InterruptedException {
        File expectedFile = new File("C:\\Users\\Admin\\Downloads\\homePage.png");
        if (!expectedFile.exists()) throw new RuntimeException("❌ Expected image not found!");

        page.navigate("https://stage.promilo.com/");
        Thread.sleep(5000);

        File actualFile = new File(screenshotsFolder + File.separator + "homepage_actual.png");
        page.screenshot(new Page.ScreenshotOptions().setPath(actualFile.toPath()).setFullPage(true));
        System.out.println("Actual screenshot saved at: " + actualFile.getAbsolutePath());

        // Highlight differences with red rectangles only
        double diffPercentage = highlightDifferencesWithBlocks(expectedFile, actualFile, 20, 10);
        System.out.println("✅ Differences highlighted on actual screenshot.");

        openImage(actualFile.getAbsolutePath());
    }

    private double highlightDifferencesWithBlocks(File expectedFile, File actualFile, int tolerance, int blockSize) throws IOException {
        BufferedImage expected = convertToRGB(ImageIO.read(expectedFile));
        BufferedImage actual = convertToRGB(ImageIO.read(actualFile));

        int width = expected.getWidth();
        int height = expected.getHeight();

        if (width != actual.getWidth() || height != actual.getHeight()) {
            System.out.println("⚠️ Images have different dimensions! Resizing actual to match expected...");
            actual = resizeImage(actual, width, height);
        }

        long diffPixels = 0;
        Graphics2D g = actual.createGraphics();
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(3));

        for (int y = 0; y < height; y += blockSize) {
            for (int x = 0; x < width; x += blockSize) {
                boolean blockDiffers = false;

                outer:
                for (int by = 0; by < blockSize && (y + by) < height; by++) {
                    for (int bx = 0; bx < blockSize && (x + bx) < width; bx++) {
                        if (isDifferent(expected.getRGB(x + bx, y + by), actual.getRGB(x + bx, y + by), tolerance)) {
                            blockDiffers = true;
                            diffPixels++;
                            break outer; // stop checking pixels inside this block
                        }
                    }
                }

                if (blockDiffers) {
                    // Draw rectangle around differing block
                    g.drawRect(x, y, blockSize, blockSize);
                }
            }
        }

        g.dispose();
        ImageIO.write(actual, "png", actualFile);

        double totalPixels = width * height;
        double diffPercentage = (diffPixels * 100.0) / totalPixels;

        System.out.println("--------------------------------------------------");
        System.out.println("📌 Total differing pixels: " + diffPixels);
        System.out.println("📌 Total pixels in image : " + totalPixels);
        System.out.println("📌 Difference percentage: " + String.format("%.2f", diffPercentage) + "%");
        System.out.println("--------------------------------------------------");

        return diffPercentage;
    }

    private BufferedImage convertToRGB(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    private BufferedImage resizeImage(BufferedImage original, int targetWidth, int targetHeight) {
        Image tmp = original.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private boolean isDifferent(int rgb1, int rgb2, int tolerance) {
        int r1 = (rgb1 >> 16) & 0xFF;
        int g1 = (rgb1 >> 8) & 0xFF;
        int b1 = rgb1 & 0xFF;

        int r2 = (rgb2 >> 16) & 0xFF;
        int g2 = (rgb2 >> 8) & 0xFF;
        int b2 = rgb2 & 0xFF;

        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2) > tolerance;
    }

    private void openImage(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "", "\"" + path + "\""});
            } else {
                System.out.println("❌ Image file not found: " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        page.close();
        context.close();
        browser.close();
        playwright.close();
    }
}
