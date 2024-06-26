package com.tugasakhir.model;

import lombok.Data;

@Data
public class MahasiswaRequest {
    private String mahasiswa_id;
    private String nama_mahasiswa;
    private String email;
    private String nomor_hp;
    private Boolean is_active;
    private String angkatan;
}
