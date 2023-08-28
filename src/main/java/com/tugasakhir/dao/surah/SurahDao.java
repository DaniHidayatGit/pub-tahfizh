package com.tugasakhir.dao.surah;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface SurahDao {
    ResponseEntity<?> getSurah(String surah, String seq, String mahasiswa_id, HttpServletRequest request);
}
