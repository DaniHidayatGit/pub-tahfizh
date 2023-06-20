package com.tugasakhir.util;

import com.tugasakhir.util.jwt.JwtTokenResponse;
import com.tugasakhir.util.jwt.SessionUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Slf4j
@Service
@Data
public class FileStorageService {

    private final String log_template_enter = "ENTER -- PROCESSES: {} -- DATE: {} -- IP: {} -- Param: {}";
    private final String log_template_error = "ERROR -- PROCESSES: {} -- MSG: {} -- DATE: {} -- IP: {} -- Param: {}";
    private final String getLog_template_response = "EXIT -- PROCESS: {} -- DATE: {} -- IP: {} -- response: {}";
    private final Path fileStorageLocation;

    private final String urlLocation;

    @Autowired
    public FileStorageService(Environment env) {
        this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir"))
                .toAbsolutePath().normalize();

        this.urlLocation = env.getProperty("app.file.urlgambar");

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String[] fileNameParts = fileName.split("\\.");
        return fileNameParts[fileNameParts.length - 1];
    }

    public String storeFileBa(MultipartFile file, String formDokumen) {
        String logParam = "Form Dokumen : " + formDokumen;
        log.info(log_template_enter, "Store Pdf To Storage", new Date(), "", logParam);

        // Normalize file name
        String fileName =
                formDokumen + new Date().getTime() + "." + getFileExtension(file.getOriginalFilename());
        if (!getFileExtension(file.getOriginalFilename()).equals("pdf")){
            return "Gagal";
        }
        try {
            // Check if the filename contains invalid characters
            if (fileName.contains("..")) {
                String error = "Sorry! Filename contains invalid path sequence " + fileName;
                log.error(log_template_error, "Store Pdf To Storage", error, new Date(), "", logParam);
                throw new RuntimeException(error);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info(getLog_template_response, "Store Pdf To Storage", new Date(), "", fileName);
            return fileName;
        } catch (IOException ex) {
            String error = "Could not store file " + fileName + ". Please try again!";
            log.error(log_template_error, "Store Pdf To Storage", error, new Date(), "", logParam);
            throw new RuntimeException(error, ex);
        }
    }

    public String storeFile(MultipartFile file, String formDokumen) {
        String logParam = "Form Dokumen : " + formDokumen;
        log.info(log_template_enter, "Store Pdf To Storage", new Date(), "", logParam);
        // Normalize file name
        String fileName =
                formDokumen + new Date().getTime() + "." + getFileExtension(file.getOriginalFilename());
        try {
            // Check if the filename contains invalid characters
            if (fileName.contains("..")) {
                String error = "Sorry! Filename contains invalid path sequence " + fileName;
                log.error(log_template_error, "Store Pdf To Storage", error, new Date(), "", logParam);
                throw new RuntimeException(error);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            log.info(getLog_template_response, "Store Pdf To Storage", new Date(), "", fileName);
            return fileName;
        } catch (IOException ex) {
            String error = "Could not store file " + fileName + ". Please try again!";
            log.error(log_template_error, "Store Pdf To Storage", error, new Date(), "", logParam);
            throw new RuntimeException(error, ex);
        }
    }

    public Resource loadFileAsResource(String fileName) throws MalformedURLException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MalformedURLException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MalformedURLException("File not found " + fileName);
        }
    }

    public String insertFileToStorage(MultipartFile file, HttpServletRequest request) {
        log.info(log_template_enter, "Insert File", new Date(), "", "");
        JwtTokenResponse jwtResponse = SessionUtil.getUserData(request);
        String username = jwtResponse.getUser_name();
        String name = jwtResponse.getUser_name().split("\\.")[1];

        String fileName = name + "-" + new Date().getTime() + "-" + file.getOriginalFilename();

        String ext = getFileExtension(file.getOriginalFilename());

        try {
            assert ext != null;
            if (ext.equals("pdf") || ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png")) {
                if (fileName.contains("..")) {
                    String error = "Sorry! Filename contains invalid path sequence " + fileName;
                    log.error(log_template_error, "Insert File", error, new Date(), "", fileName);
                    throw new RuntimeException(error);
                }
                Path targetLocation = this.fileStorageLocation.resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                log.info(getLog_template_response, "insertFile", new Date(), "", fileName);
                return fileName;
            } else {
                throw new RuntimeException("Gagal File Extension Tidak Sesuai") ;
            }
        } catch (IOException ex) {
            String error = "Could not store file " + fileName + ". Please try again!";
            log.error(log_template_error, "insertFile", error, new Date(), "", fileName);
            throw new RuntimeException(error, ex);
        }
    }

    public String save(MultipartFile file, String jenis){
        try{
            String hasildata = "";
            if (!file.isEmpty()) {
                hasildata = this.urlLocation + jenis+ new Date().getTime() + "." + getFileExtension(file.getOriginalFilename());
                Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(jenis+ new Date().getTime() + "." + getFileExtension(file.getOriginalFilename())));
            }

            return hasildata;

        } catch (IOException e) {
            throw new RuntimeException(("Could not store the file. Error: " + e.getMessage()));
        }
    }
}