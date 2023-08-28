package com.tugasakhir.dao.mahasiswa;

import com.tugasakhir.configuration.DBHandler;
import com.tugasakhir.configuration.Response;
import com.tugasakhir.model.AngkatanRequest;
import com.tugasakhir.model.MahasiswaRequest;
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

@Slf4j
@Service
@Transactional
public class MahasiswaDaoImpl extends DBHandler implements MahasiswaDao {
    public MahasiswaDaoImpl(DataSource dataSource){
        this.setDataSource(dataSource);
    }
    private final String log_template_enter = "\u001B[34m" + "ENTER -- PROCESSES: {} -- DATE: {} -- IP: {} -- Param: {}" + "\u001B[0m";
    private final String log_template_sout = "\u001B[34m" + "SOUT -- {}" + "\u001B[0m";
    private final String log_template_response = "\u001B[32m" + "EXIT -- PROCESS: {} -- DATE: {} -- IP: {} -- Response: {}" + "\u001B[0m";
    private final String log_template_error = "\u001B[31m" + "ERROR -- PROCESS: {} -- MSG: {} -- DATE: {} -- IP: {} -- Param: {}"+ "\u001B[0m";

    @Override
    public ResponseEntity<?> getAngkatan(String id, String angkatan, String nama_angkatan, String is_active, HttpServletRequest request) {
        try {
            Object[] obj = {
                    id,
                    angkatan,
                    nama_angkatan,
                    is_active
            };
            List<LinkedHashMap<String, String>> linkedHashMaps = ExecuteCallPostgre("func_angkatan_get", obj, new Mapper());
            return Response.response(linkedHashMaps, HttpStatus.OK);
        } catch (RuntimeException e){
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateAngkatan(AngkatanRequest angkatanRequest, HttpServletRequest request) {
        try {
            Object[] obj = {
                    angkatanRequest.getId(),
                    angkatanRequest.getAngkatan(),
                    angkatanRequest.getNama_angkatan(),
                    angkatanRequest.getIs_active()
            };
            String msg = ExecuteUpdateCallPostgres("func_angkatan_action", obj);
            if(!msg.equals("Success")){
                return Response.response(msg, HttpStatus.BAD_REQUEST);
            }
            return Response.response(msg, HttpStatus.OK);
        } catch (RuntimeException e){
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getMahasiswa(String angkatan, String mahasiswa_id, String nama_mahasiswa, String is_active, HttpServletRequest request) {
        try {
            Object[] obj = {
                    angkatan,
                    mahasiswa_id,
                    nama_mahasiswa,
                    is_active
            };
            List<LinkedHashMap<String, String>> linkedHashMaps = ExecuteCallPostgre("func_mahasiswa_get", obj, new Mapper());
            return Response.response(linkedHashMaps, HttpStatus.OK);
        } catch (RuntimeException e){
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateMahasiswa(MahasiswaRequest mahasiswaRequest, HttpServletRequest request) {
        try {
            Object[] obj = {
                    mahasiswaRequest.getMahasiswa_id(),
                    mahasiswaRequest.getNama_mahasiswa(),
                    mahasiswaRequest.getEmail(),
                    mahasiswaRequest.getNomor_hp(),
                    mahasiswaRequest.getIs_active(),
                    mahasiswaRequest.getAngkatan(),
                    "1",
                    mahasiswaRequest.getIs_deleted()
            };
            String msg = ExecuteUpdateCallPostgres("func_mahasiswa_action", obj);
            if(!msg.equals("Success")){
                return Response.response(msg, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return Response.response(msg, HttpStatus.OK);
        } catch (RuntimeException e){
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
