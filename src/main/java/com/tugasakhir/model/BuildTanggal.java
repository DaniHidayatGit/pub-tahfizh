package com.tugasakhir.model;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class BuildTanggal {
    Integer tanggal;
    Boolean active;
    String flag;
    List<LinkedHashMap<String, String>> listMahasiswa;
}
