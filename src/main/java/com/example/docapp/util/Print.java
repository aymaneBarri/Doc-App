package com.example.docapp.util;

import com.example.docapp.models.Visite;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import java.awt.Desktop;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class Print {

    public static void print(Visite visite) {
        String url = System.getProperty("user.dir") + "\\src\\main\\resources\\com\\example\\docapp\\ordonnance.pdf";
        try {
            PdfWriter pdfWriter = new PdfWriter(url);
            PdfDocument pdfdoc = new PdfDocument(pdfWriter);
            String path = System.getProperty("user.dir") + "\\src\\main\\resources\\com\\example\\docapp\\images\\logoprint.png";
            System.out.println(path);

            Paragraph p = new Paragraph("Date de prescription:  " + visite.getVisit_date());
            Paragraph t = new Paragraph("Ordonnance");
            t.setBold();
            t.setFontSize(30.0F);
            t.setTextAlignment(TextAlignment.CENTER);
            p.setBold();
            p.setTextAlignment(TextAlignment.CENTER);
            Paragraph para = new Paragraph(visite.getPrescription());
            ImageData d = ImageDataFactory.create(path);
            Image img = new Image(d);
            img.scaleToFit(500.0F,50.0F);
            img.setHorizontalAlignment(HorizontalAlignment.CENTER);
            para.setFontSize(15.0F);
            img.setMargins(10.0F, 10.0F, 10.0F, 10.0F);
            para.setMargins(50.0F, 50.0F, 10.0F, 50.0F);
            t.setMargins(50.0F, 10.0F, 10.0F, 10.0F);
            p.setMargins(10.0F, 10.0F, 10.0F, 10.0F);
            para.setHorizontalAlignment(HorizontalAlignment.CENTER);
            Document doc = new Document(pdfdoc);
            doc.add(img);
            doc.add(p);
            doc.add(t);
            doc.add(para);
            doc.close();

            System.out.println("printed");
        } catch (FileNotFoundException/* | MalformedURLException*/ e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }


        try {

            File pdfFile = new File(url);
            if (pdfFile.exists()) {

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.out.println("Awt Desktop is not supported!");
                }

            } else {
                System.out.println("File is not exists!");
            }

            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
