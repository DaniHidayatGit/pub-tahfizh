package com.tugasakhir.controller;

import com.tugasakhir.dao.nilai.NilaiDao;
import com.tugasakhir.model.MasterNilaiRequest;
import com.tugasakhir.model.PenilaianRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "NILAI")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequestMapping("/nilai")
@RequiredArgsConstructor
public class NilaiController {
    private final NilaiDao dao;

    @GetMapping("/getMasterNilai")
    public ResponseEntity<?> getMasterNilai(
            @RequestParam(required = false) String nilai_id,
            @RequestParam(required = false) String is_active,
            HttpServletRequest request
    ){
        return dao.getMasterNilai(nilai_id, is_active, request);
    }
    @PostMapping("/updateMasterNilai")
    public ResponseEntity<?> updateMasterNilai(
            @RequestBody MasterNilaiRequest masterNilaiRequest,
            HttpServletRequest request
    ){
        return dao.updateMasterNilai(masterNilaiRequest, request);
    }

    @GetMapping("/getNilai")
    public ResponseEntity<?> getNilai(
            @RequestParam(required = false) String surah_id,
            @RequestParam(required = false) String tanggal,
            @RequestParam(required = false) String mahasiswa_id,
            HttpServletRequest request
    ){
        return dao.getNilai(surah_id, tanggal, mahasiswa_id, request);
    }

    @PostMapping("/updateNilai")
    public ResponseEntity<?> updateNilai(
            @RequestBody List<PenilaianRequest> penilaianRequest,
            HttpServletRequest request
    ){
        return dao.updateNilai(penilaianRequest, request);
    }
}
