package com.tugasakhir.model;

import lombok.Data;

import java.util.List;

/**
 * @author : Dani Hidayat
 * @email : dani.hidayat@iconpln.co.id
 * @date : 05/09/2023
 */
@Data
public class JadwalRequestList {

    private String jadwal_id;
    private String waktu;
    private String is_deleted;
    private List<String> mahasiswa_id;
}
