package com.tugasakhir.dao.jadwal;

import com.tugasakhir.model.JadwalRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface JadwalDao {
    ResponseEntity<?> getJadwal(String jadwal_id, String day, String month, String year, String mahasiswa_id, HttpServletRequest request);
    ResponseEntity<?> updateJadwal(JadwalRequest jadwalRequest, HttpServletRequest request);
}
