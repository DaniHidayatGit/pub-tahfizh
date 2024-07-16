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
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.tugasakhir.util.jwt.JwtTokenResponse;
import com.tugasakhir.util.jwt.SessionUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Tag(name = "SERTIFIKAT")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequestMapping("/sertifikat")
@RequiredArgsConstructor
public class SertifikatController {

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadCertificate(
            HttpServletRequest request
    ) throws IOException {
        JwtTokenResponse response = SessionUtil.getUserData(request);
        String nama_mahasiswa = response.getFull_name();
         // Baca template PDF
        ClassPathResource pdfResource = new ClassPathResource("template.pdf");
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
        Paragraph paragraph = new Paragraph(nama_mahasiswa)
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
        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename(nama_mahasiswa + "-certificate.pdf")
                .build();

        headers.setContentDisposition(contentDisposition);
        headers.add("Access-Control-Expose-Headers", "Content-Disposition");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(byteArrayOutputStream.toByteArray());
    }
}
