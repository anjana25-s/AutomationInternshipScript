package com.promilo.automation.resources;

import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PDFOCRUtility {

    /**
     * Extracts text from all pages of a PDF using OCR
     * 
     * @param pdfPath full path to the PDF file
     * @return extracted text from PDF
     * @throws Exception
     */
    public static String extractTextFromPDF(String pdfPath) throws Exception {
        StringBuilder sb = new StringBuilder();

        // Load PDF using try-with-resources to ensure closing
        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata"); // adjust if needed
            tesseract.setLanguage("eng");

            for (int page = 0; page < document.getNumberOfPages(); page++) {
                try {
                    BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300); // render page as image
                    String text = tesseract.doOCR(image);
                    sb.append(text).append("\n");
                } catch (TesseractException e) {
                    System.err.println("OCR failed on page " + page + ": " + e.getMessage());
                }
            }
        }

        return sb.toString();
    }

    /**
     * Validates if a PDF contains expected text using OCR
     * 
     * @param pdfPath full path to the PDF file
     * @param expectedText text to check for
     * @return true if PDF contains expected text
     * @throws Exception
     */
    public static boolean validatePDFContains(String pdfPath, String expectedText) throws Exception {
        String pdfText = extractTextFromPDF(pdfPath);
        return pdfText != null && pdfText.toLowerCase().contains(expectedText.toLowerCase());
    }
}
