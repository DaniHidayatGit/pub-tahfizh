package com.tugasakhir.dao.mahasiswa;

import com.tugasakhir.configuration.DBQueryHandler;
import com.tugasakhir.configuration.Response;
import com.tugasakhir.model.AngkatanRequest;
import com.tugasakhir.model.MahasiswaRequest;
import com.tugasakhir.util.mapper.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;

import static com.tugasakhir.util.Helpers.*;

@Slf4j
@Service
@Transactional
public class MahasiswaDaoImpl extends DBQueryHandler implements MahasiswaDao {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public MahasiswaDaoImpl(DataSource dataSource, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.setDataSource(dataSource);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    private final String log_template_enter = "\u001B[34m" + "ENTER -- PROCESSES: {} -- DATE: {} -- IP: {} -- Param: {}" + "\u001B[0m";
    private final String log_template_sout = "\u001B[34m" + "SOUT -- {}" + "\u001B[0m";
    private final String log_template_response = "\u001B[32m" + "EXIT -- PROCESS: {} -- DATE: {} -- IP: {} -- Response: {}" + "\u001B[0m";
    private final String log_template_error = "\u001B[31m" + "ERROR -- PROCESS: {} -- MSG: {} -- DATE: {} -- IP: {} -- Param: {}"+ "\u001B[0m";

    @Override
    public ResponseEntity<?> getAngkatan(String id, String angkatan, String nama_angkatan, String is_active, HttpServletRequest request) {
        Connection con = Connect();
        try {
            Object[] obj = {
                    id,
                    angkatan,
                    nama_angkatan,
                    is_active
            };
            List<LinkedHashMap<String, String>> linkedHashMaps = ExecuteCallPostgre(con,"public.func_angkatan_get", obj, new Mapper());
            Commit(con);
            return Response.response(linkedHashMaps, HttpStatus.OK);
        } catch (RuntimeException e){
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        } finally {
            Close(con);
        }
    }

    @Override
    public ResponseEntity<?> updateAngkatan(AngkatanRequest angkatanRequest, HttpServletRequest request) {
        Connection con = Connect();
        try {
            if(angkatanRequest.getAngkatan().isEmpty() || angkatanRequest.getNama_angkatan().isEmpty())
                throw new RuntimeException("Data harus diisi!");

            if(!(angkatanRequest.getAngkatan() != null && angkatanRequest.getAngkatan().matches("[0-9]+")))
                throw new RuntimeException("Angkatannya harus berupa angka!");

            Object[] obj = {
                    angkatanRequest.getId(),
                    angkatanRequest.getAngkatan(),
                    angkatanRequest.getNama_angkatan(),
                    angkatanRequest.getIs_active()
            };
            String msg = CallFunctionRetString(con,"func_angkatan_action", obj);
            if(!msg.equals("Success")){
                throw new RuntimeException(msg);
            }
            Commit(con);
            if(angkatanRequest.getId().isEmpty())
                return Response.response("Data berhasil disimpan", HttpStatus.OK);
            else
                return Response.response("Data berhasil diubah", HttpStatus.OK);
        } catch (RuntimeException e){
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        } finally {
            Close(con);
        }
    }

    @Override
    public ResponseEntity<?> getMahasiswa(String angkatan, String mahasiswa_id, String nama_mahasiswa, String is_active, HttpServletRequest request) {
        Connection con = Connect();
        try {
            Object[] obj = {
                    angkatan,
                    mahasiswa_id,
                    nama_mahasiswa,
                    is_active
            };
            List<LinkedHashMap<String, String>> linkedHashMaps = ExecuteCallPostgre(con, "func_mahasiswa_get", obj, new Mapper());
            Commit(con);
            return Response.response(linkedHashMaps, HttpStatus.OK);
        } catch (RuntimeException e){
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        } finally {
            Close(con);
        }
    }

    @Override
    public ResponseEntity<?> updateMahasiswa(MahasiswaRequest mahasiswaRequest, HttpServletRequest request) {
        Connection con = Connect();
        try {
            if(!isValidEmail(mahasiswaRequest.getEmail()))
                throw new RuntimeException("Mohon berikan inputan email yang valid!");

            Object[] obj = {
                    mahasiswaRequest.getMahasiswa_id(),
                    mahasiswaRequest.getNama_mahasiswa(),
                    mahasiswaRequest.getEmail(),
                    mahasiswaRequest.getNomor_hp(),
                    mahasiswaRequest.getIs_active(),
                    mahasiswaRequest.getAngkatan(),
                    "1"
            };
            String msg = CallFunctionRetString(con,"func_mahasiswa_action", obj);
            if(!msg.equals("Success")){
                throw new RuntimeException(msg);
            }
            Commit(con);
            return Response.response(msg, HttpStatus.OK);
        } catch (RuntimeException e){
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        } finally {
            Close(con);
        }
    }
}
