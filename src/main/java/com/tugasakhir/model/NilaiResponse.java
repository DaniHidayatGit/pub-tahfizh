package com.tugasakhir.model;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class NilaiResponse {
    String waktu;
    List<LinkedHashMap<String, Object>> list;
}
