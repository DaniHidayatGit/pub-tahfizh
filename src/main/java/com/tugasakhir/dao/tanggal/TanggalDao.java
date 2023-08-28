package com.tugasakhir.dao.tanggal;

import com.tugasakhir.model.BuildTanggal;

import java.util.List;

public interface TanggalDao {
    List<BuildTanggal> beforeMonth(Integer totalHariBulanSebelumnya, Integer day);
    List<BuildTanggal> thisMonth(Integer totalHariBulanIni, String bulan, String tahun);
    List<BuildTanggal> afterMonth(Integer totalSize);
}
