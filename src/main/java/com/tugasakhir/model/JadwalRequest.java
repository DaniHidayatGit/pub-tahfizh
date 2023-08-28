package com.tugasakhir.model;

import lombok.Data;

@Data
public class JadwalRequest {
    private String jadwal_id;
    private String waktu;
    private String mahasiswa_id;
    private String is_deleted;
}
