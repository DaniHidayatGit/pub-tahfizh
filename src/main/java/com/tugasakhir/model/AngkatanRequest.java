package com.tugasakhir.model;

import lombok.Data;

@Data
public class AngkatanRequest {
    private String id;
    private String angkatan;
    private String nama_angkatan;
    private Boolean is_active;
}
