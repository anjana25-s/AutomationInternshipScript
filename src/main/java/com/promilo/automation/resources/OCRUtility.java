package com.promilo.automation.resources;

import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRUtility {

    /**
     * Extract text from an image file using Tesseract OCR
     * @param imagePath full path to the image
     * @return extracted text
     * @throws Exception
     */
    public static String extractTextFromImage(String imagePath) throws Exception {
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata"); // adjust as needed
        tesseract.setLanguage("eng");

        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            return tesseract.doOCR(image);
        } catch (TesseractException e) {
            throw new Exception("OCR failed for image: " + imagePath, e);
        }
    }
}
