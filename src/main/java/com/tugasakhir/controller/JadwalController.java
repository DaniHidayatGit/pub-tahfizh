package com.tugasakhir.controller;

import com.tugasakhir.dao.jadwal.JadwalDao;
import com.tugasakhir.model.JadwalRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "JADWAL")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequestMapping("/jadwal")
@RequiredArgsConstructor
public class JadwalController {
    private final JadwalDao dao;
    @GetMapping("/getJadwal")
    public ResponseEntity<?> getJadwal(
            @RequestParam(required = false, defaultValue = "") String jadwal_id,
            @RequestParam(required = false, defaultValue = "") String day,
            @RequestParam(required = false, defaultValue = "") String month,
            @RequestParam(required = false, defaultValue = "") String year,
            @RequestParam(required = false, defaultValue = "") String mahasiswa_id,
            HttpServletRequest request
    ){
        return dao.getJadwal(jadwal_id, day, month, year, mahasiswa_id, request);
    }

    @PostMapping("/updateJadwal")
    public ResponseEntity<?> updateJadwal(
            @RequestBody JadwalRequest jadwalRequest,
            HttpServletRequest request
    ){
        return dao.updateJadwal(jadwalRequest, request);
    }

}
