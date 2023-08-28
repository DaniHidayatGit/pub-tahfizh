package com.tugasakhir.dao.mahasiswa;

import com.tugasakhir.model.AngkatanRequest;
import com.tugasakhir.model.MahasiswaRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface MahasiswaDao {
    ResponseEntity<?> getAngkatan(String id, String angkatan, String nama_angkatan, String is_active, HttpServletRequest request);
    ResponseEntity<?> updateAngkatan(AngkatanRequest angkatanRequest, HttpServletRequest request);
    ResponseEntity<?> getMahasiswa(String angkatan, String mahasiswa_id, String nama_mahasiswa, String is_active, HttpServletRequest request);
    ResponseEntity<?> updateMahasiswa(MahasiswaRequest mahasiswaRequest, HttpServletRequest request);
}
