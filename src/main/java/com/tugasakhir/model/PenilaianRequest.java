package com.tugasakhir.model;

import lombok.Data;

import java.util.List;

@Data
public class PenilaianRequest {
    private String penilaian_id;
    private String mahasiswa_id;
    private String waktu;
    private String surah_seq;
    private String juz;
    private Integer surah_awalan;
    private Integer surah_akhir;
    private Boolean is_lulus;
    private String keterangan;
    private List<PenilaianDetailRequest> details;
}
