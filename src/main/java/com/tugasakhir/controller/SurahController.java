package com.tugasakhir.controller;

import com.tugasakhir.dao.surah.SurahDao;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "SURAH")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequestMapping("/surah")
@RequiredArgsConstructor
public class SurahController {
    private final SurahDao dao;
    @GetMapping("/getSurah")
    public ResponseEntity<?> getSurah(
            @RequestParam(required = false, defaultValue = "") String surah,
            @RequestParam(required = false, defaultValue = "") String seq,
            @RequestParam(required = false, defaultValue = "") String mahasiswa_id,
            HttpServletRequest request
    ){
        return dao.getSurah(surah, seq, mahasiswa_id, request);
    }
}
