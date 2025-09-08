package visualtesting;

import com.microsoft.playwright.*;
import org.testng.annotations.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VisualTest2 {

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

        // Navigate and take actual screenshot
        page.navigate("https://stage.promilo.com/");
        Thread.sleep(5000);

        File actualFile = new File(screenshotsFolder + File.separator + "homepage_actual.png");
        page.screenshot(new Page.ScreenshotOptions().setPath(actualFile.toPath()).setFullPage(true));
        System.out.println("Actual screenshot saved at: " + actualFile.getAbsolutePath());

        // Compare images and get difference percentage
        double diffPercentage = calculateDifferencePercentage(expectedFile, actualFile, 20);
        System.out.println("📌 Difference percentage: " + String.format("%.2f", diffPercentage) + "%");

        openImage(actualFile.getAbsolutePath());
    }

    private double calculateDifferencePercentage(File expectedFile, File actualFile, int tolerance) throws IOException {
        BufferedImage expected = convertToRGB(ImageIO.read(expectedFile));
        BufferedImage actual = convertToRGB(ImageIO.read(actualFile));

        int width = expected.getWidth();
        int height = expected.getHeight();

        if (width != actual.getWidth() || height != actual.getHeight()) {
            System.out.println("⚠️ Images have different dimensions! Resizing actual to match expected...");
            actual = resizeImage(actual, width, height);
        }

        long diffPixels = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isDifferent(expected.getRGB(x, y), actual.getRGB(x, y), tolerance)) {
                    diffPixels++;
                }
            }
        }

        double totalPixels = width * height;
        System.out.println("📌 Total differing pixels: " + diffPixels);
        System.out.println("📌 Total pixels in image : " + totalPixels);

        return (diffPixels * 100.0) / totalPixels;
    }

    private BufferedImage convertToRGB(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        newImage.getGraphics().drawImage(image, 0, 0, null);
        return newImage;
    }

    private BufferedImage resizeImage(BufferedImage original, int targetWidth, int targetHeight) {
        BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        resized.getGraphics().drawImage(original.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
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
