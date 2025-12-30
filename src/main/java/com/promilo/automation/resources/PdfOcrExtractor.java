package com.promilo.automation.resources;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.util.Base64;

public class PdfOcrExtractor {

    public static String extractTextFromBase64Pdf(String base64Pdf) {
        StringBuilder resultText = new StringBuilder();

        try {
            byte[] pdfBytes = Base64.getDecoder().decode(base64Pdf);

            PDDocument document = PDDocument.load(pdfBytes);
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
            tesseract.setLanguage("eng");

            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300);

                String pageText = tesseract.doOCR(image);

                resultText.append("\n--- Page ").append(page + 1).append(" ---\n");
                resultText.append(pageText);
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultText.toString();
    }
}
