package com.tugasakhir.model;

import lombok.Data;

@Data
public class MasterNilaiRequest {
    private String nilai_id;
    private String nama_penilaian;
    private Boolean is_active;
}
