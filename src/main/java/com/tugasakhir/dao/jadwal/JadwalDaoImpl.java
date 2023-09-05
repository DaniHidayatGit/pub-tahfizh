package com.tugasakhir.dao.jadwal;

import com.tugasakhir.configuration.DBHandler;
import com.tugasakhir.configuration.DBQueryHandler;
import com.tugasakhir.configuration.Response;
import com.tugasakhir.model.JadwalRequest;
import com.tugasakhir.model.JadwalRequestList;
import com.tugasakhir.util.mapper.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@Transactional
public class JadwalDaoImpl extends DBQueryHandler implements JadwalDao {
    public JadwalDaoImpl(DataSource dataSource){
        this.setDataSource(dataSource);
    }
    private final String log_template_enter = "\u001B[34m" + "ENTER -- PROCESSES: {} -- DATE: {} -- IP: {} -- Param: {}" + "\u001B[0m";
    private final String log_template_sout = "\u001B[34m" + "SOUT -- {}" + "\u001B[0m";
    private final String log_template_response = "\u001B[32m" + "EXIT -- PROCESS: {} -- DATE: {} -- IP: {} -- Response: {}" + "\u001B[0m";
    private final String log_template_error = "\u001B[31m" + "ERROR -- PROCESS: {} -- MSG: {} -- DATE: {} -- IP: {} -- Param: {}"+ "\u001B[0m";
    @Override
    public ResponseEntity<?> getJadwal(String jadwal_id, String day, String month, String year, String mahasiswa_id, HttpServletRequest request) {
        Connection con = Connect();
        try {
            Object[] obj = {jadwal_id, day, month, year, mahasiswa_id};
            List<LinkedHashMap<String, String>> linkedHashMaps = ExecuteCallPostgre(con, "func_jadwal_get", obj, new Mapper());
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
    public ResponseEntity<?> updateJadwal(JadwalRequest jadwalRequest, HttpServletRequest request) {
        Connection con = Connect();
        try {
            Object[] obj = {
                    jadwalRequest.getJadwal_id(),
                    jadwalRequest.getWaktu(),
                    jadwalRequest.getMahasiswa_id(),
                    jadwalRequest.getIs_deleted()
            };
            String msg = CallFunctionRetString(con,"func_jadwal_action", obj);
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

    @Override
    public ResponseEntity<?> updateJadwalList(JadwalRequestList jadwalRequest, HttpServletRequest request) {
        Connection con = Connect();
        try {
            for(String e : jadwalRequest.getMahasiswa_id()){
                Object[] obj = {
                        jadwalRequest.getJadwal_id(),
                        jadwalRequest.getWaktu(),
                        e,
                        jadwalRequest.getIs_deleted()
                };
                String msg = CallFunctionRetString(con, "func_jadwal_action", obj);
                if(!msg.equals("Success")){
                    throw new RuntimeException(msg);
                }
            }
            Commit(con);
            return Response.response("Success", HttpStatus.OK);
        } catch (RuntimeException e){
            Rollback(con);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        } finally {
            Close(con);
        }
    }
}
