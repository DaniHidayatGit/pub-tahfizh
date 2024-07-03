package com.tugasakhir.dao.nilai;

import com.tugasakhir.configuration.DBHandler;
import com.tugasakhir.configuration.Response;
import com.tugasakhir.model.MasterNilaiRequest;
import com.tugasakhir.model.PenilaianDetailRequest;
import com.tugasakhir.model.PenilaianRequest;
import com.tugasakhir.util.Helpers;
import com.tugasakhir.util.ObjectMapper;
import com.tugasakhir.util.mapper.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;

import static com.tugasakhir.util.Helpers.getInteger;
import static com.tugasakhir.util.Helpers.getString;

@Slf4j
@Service
@Transactional
public class NilaiDaoImpl extends DBHandler implements NilaiDao {

    public NilaiDaoImpl(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
    private final String log_template_enter = "\u001B[34m" + "ENTER -- PROCESSES: {} -- DATE: {} -- IP: {} -- Param: {}" + "\u001B[0m";
    private final String log_template_sout = "\u001B[34m" + "SOUT -- {}" + "\u001B[0m";
    private final String log_template_response = "\u001B[32m" + "EXIT -- PROCESS: {} -- DATE: {} -- IP: {} -- Response: {}" + "\u001B[0m";
    private final String log_template_error = "\u001B[31m" + "ERROR -- PROCESS: {} -- MSG: {} -- DATE: {} -- IP: {} -- Param: {}"+ "\u001B[0m";

    @Override
    public ResponseEntity<?> getMasterNilai(String nilai_id, String is_active, HttpServletRequest request) {
        try {
            Object[] obj = {nilai_id, is_active};
            List<LinkedHashMap<String, String>> linkedHashMaps = ExecuteCallPostgre("func_master_nilai_get", obj, new Mapper());
            return Response.response(linkedHashMaps, HttpStatus.OK);
        } catch (RuntimeException e){
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> updateMasterNilai(MasterNilaiRequest masterNilaiRequest, HttpServletRequest request) {
        try {
            Object[] obj = {
                    masterNilaiRequest.getNilai_id(),
                    masterNilaiRequest.getNama_penilaian().toUpperCase(),
                    masterNilaiRequest.getIs_active()
            };
            String msg = ExecuteUpdateCallPostgres("func_master_nilai_action", obj);
            if(!msg.equals("Success")){
                return Response.response(msg, HttpStatus.BAD_REQUEST);
            }
            return Response.response("Berhasil mengupdate master nilai", HttpStatus.OK);
        } catch (RuntimeException e){
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getNilai(String surah_id, String tanggal, String mahasiswa_id, HttpServletRequest request) {
        try {
            Object[] obj = {surah_id, tanggal, mahasiswa_id};
            List<LinkedHashMap<String, Object>> linkedHashMaps = ExecuteCallPostgre("func_nilai_get", obj, new ObjectMapper());
            for(LinkedHashMap<String, Object> linkedHashMap : linkedHashMaps){
                Object[] objDetail = {
                        Helpers.getString(linkedHashMap.get("penilaian_id"))
                };
                List<LinkedHashMap<String, Object>> linkedHashMaps2 = ExecuteCallPostgre("func_nilai_get_detail", objDetail, new ObjectMapper());
                linkedHashMap.put("details", linkedHashMaps2);
            }
            return Response.response(linkedHashMaps, HttpStatus.OK);
        } catch (RuntimeException e){
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> updateNilai(List<PenilaianRequest> penilaianRequest, HttpServletRequest request) {
        try {
            for(PenilaianRequest e : penilaianRequest){
                if(e.getIs_lulus() == null)
                    throw new RuntimeException("Mohon inputkan keterangan Lulus/Tidak Lulus!");
                if(e.getDetails().isEmpty())
                    throw new RuntimeException("Mohon inputkan detail penilaian!");

                Object[] obj = {
                        e.getPenilaian_id(),
                        e.getMahasiswa_id(),
                        e.getWaktu(),
                        e.getSurah_seq(),
                        e.getSurah_awalan(),
                        e.getSurah_akhir(),
                        e.getIs_lulus(),
                        e.getKeterangan()
                };
                String penilaian_id = ExecuteUpdateCallPostgres("func_penilaian_action", obj);

                for(PenilaianDetailRequest f : e.getDetails()){
                    if(!Helpers.isInteger(f.getNilai()))
                        throw new RuntimeException("Mohon inputkan angka pada kolom detail penilaian!");

                    if(getInteger(f.getNilai())> 100)
                        throw new RuntimeException("Nilai tidak boleh melebihi 100!");

                    Object[] obj2 = {
                            getInteger(penilaian_id),
                            getInteger(f.getNilai_id()),
                            getInteger(f.getNilai()),
                            e.getKeterangan()
                    };
                    String msg2 = ExecuteUpdateCallPostgres("func_penilaian_detail_action", obj2);
                    if(!msg2.equals("Success")){
                        return Response.response(msg2, HttpStatus.BAD_REQUEST);
                    }
                }
                if(e.getIs_lulus()) {
                    int total_nilai = 0;
                    for(PenilaianDetailRequest f : e.getDetails()){
                        total_nilai += getInteger(f.getNilai());
                    }
                    if(total_nilai/e.getDetails().size() < 75)
                        throw new RuntimeException("Minimal nilai status Lulus yaitu 75");
                } else {
                    int total_nilai = 0;
                    for(PenilaianDetailRequest f : e.getDetails()){
                        total_nilai += getInteger(f.getNilai());
                    }
                    if(total_nilai/e.getDetails().size() < 75)
                        throw new RuntimeException("Maksimal nilai status Tidak Lulus yaitu 74");
                }

            }
            return Response.response("Berhasil melakukan penilaian", HttpStatus.OK);
        } catch (RuntimeException e){
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
