package com.tugasakhir.dao.nilai;


import com.tugasakhir.model.MasterNilaiRequest;
import com.tugasakhir.model.PenilaianRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface NilaiDao {
    ResponseEntity<?> getMasterNilai(String nilai_id, String is_active, HttpServletRequest request);
    ResponseEntity<?> updateMasterNilai(MasterNilaiRequest masterNilaiRequest, HttpServletRequest request);
    ResponseEntity<?> getNilai(String surah_id, String tanggal, String mahasiswa_id, HttpServletRequest request);

    ResponseEntity<?> updateNilai(List<PenilaianRequest> penilaianRequest, HttpServletRequest request);
}
