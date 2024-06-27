package com.tugasakhir.controller;

import com.tugasakhir.dao.mahasiswa.MahasiswaDao;
import com.tugasakhir.model.AngkatanRequest;
import com.tugasakhir.model.MahasiswaRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "MAHASISWA")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequestMapping("/mahasiswa")
@RequiredArgsConstructor
public class MahasiswaController {
    private final MahasiswaDao dao;
    @GetMapping("/getAngkatan")
    public ResponseEntity<?> getAngkatan(
        @RequestParam(required = false, defaultValue = "") String id,
        @RequestParam(required = false, defaultValue = "") String angkatan,
        @RequestParam(required = false, defaultValue = "") String nama_angkatan,
        @RequestParam(required = false, defaultValue = "") String is_active,
        HttpServletRequest request
    ) {
        return dao.getAngkatan(id, angkatan, nama_angkatan, is_active, request);
    }
    @PostMapping("/updateAngkatan")
    public ResponseEntity<?> updateAngkatan(
            @RequestBody AngkatanRequest angkatanRequest,
            HttpServletRequest request
    ){
        return dao.updateAngkatan(angkatanRequest, request);
    }

    @GetMapping("/getMahasiswa")
    public ResponseEntity<?> getMahasiswa(
            @RequestParam(required = false, defaultValue = "") String angkatan,
            @RequestParam(required = false, defaultValue = "") String mahasiswa_id,
            @RequestParam(required = false, defaultValue = "") String nama_mahasiswa,
            @RequestParam(required = false, defaultValue = "") String is_active,
            @RequestParam(required = false, defaultValue = "") String cek_jadwal,
            HttpServletRequest request
    ){
        if(cek_jadwal.equals("undefined"))
            cek_jadwal = "";
        return dao.getMahasiswa(angkatan, mahasiswa_id, nama_mahasiswa, is_active, cek_jadwal, request);
    }
    @PostMapping("/updateMahasiswa")
    public ResponseEntity<?> updateMahasiswa(
            @RequestBody MahasiswaRequest mahasiswaRequest,
            HttpServletRequest request
    ){
        return dao.updateMahasiswa(mahasiswaRequest, request);
    }

}
