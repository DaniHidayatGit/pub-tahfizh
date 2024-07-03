package com.tugasakhir.controller;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class CertificateController {

    @GetMapping("/download/certificate/")
    public ResponseEntity<byte[]> downloadCertificate(
    ) throws IOException {
        String name = "Dani Hidayat";

        // Ambil nama dari database
//        User user = userService.findById(userId);
//        String name = user.getName();

        // Baca template PDF
        ClassPathResource pdfResource = new ClassPathResource("template_2.pdf");
        PdfReader pdfReader = new PdfReader(pdfResource.getInputStream());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfReader, pdfWriter);

        // Dapatkan ukuran halaman
        Rectangle pageSize = pdfDocument.getFirstPage().getPageSize();
        float pageWidth = pageSize.getWidth();
        float pageHeight = pageSize.getHeight();

        // Tentukan posisi y untuk menempatkan nama di tengah vertikal (atur sesuai kebutuhan)
        float yPosition = pageHeight / 2 + 20; // Misalnya, menambah offset vertikal

        Color textColor = new DeviceRgb(213, 175, 91); // Warna #d5af5b

        // Muat font khusus
        ClassPathResource fontResource = new ClassPathResource("ITCEDSCR.ttf");
        PdfFont font = PdfFontFactory.createFont(fontResource.getPath(), PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

        // Tambahkan nama ke PDF pada posisi yang ditentukan
        Paragraph paragraph = new Paragraph(name)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(70)
                .setFont(font)
                .setFontColor(textColor);

        // Menggunakan Canvas untuk menempatkan teks dengan alignment yang tepat
        Canvas canvas = new Canvas(pdfDocument.getFirstPage(), pageSize);
        canvas.showTextAligned(paragraph, pageWidth / 2, yPosition, TextAlignment.CENTER);

        canvas.close();
        pdfDocument.close();

        // Mengatur header untuk respons
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "certificate.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(byteArrayOutputStream.toByteArray());
    }
}
