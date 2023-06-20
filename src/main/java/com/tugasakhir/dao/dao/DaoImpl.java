package com.tugasakhir.dao.dao;

import com.tugasakhir.configuration.DBHandler;
import com.tugasakhir.configuration.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
public class DaoImpl extends DBHandler implements Dao {


    public DaoImpl(DataSource dataSource){
        this.setDataSource(dataSource);
    }
    private final String log_template_enter = "\u001B[34m" + "ENTER -- PROCESSES: {} -- DATE: {} -- IP: {} -- Param: {}" + "\u001B[0m";
    private final String log_template_sout = "\u001B[34m" + "SOUT -- {}" + "\u001B[0m";
    private final String log_template_response = "\u001B[32m" + "EXIT -- PROCESS: {} -- DATE: {} -- IP: {} -- Response: {}" + "\u001B[0m";
    private final String log_template_error = "\u001B[31m" + "ERROR -- PROCESS: {} -- MSG: {} -- DATE: {} -- IP: {} -- Param: {}"+ "\u001B[0m";

    @Override
    public ResponseEntity<?> get(HttpServletRequest request) {
        log.info(log_template_enter, "get", new java.util.Date(), request.getRemoteAddr(), "");
        try {

            log.info(log_template_sout, "SAYA ANAK MAMA");

            log.info(log_template_response, "get", new java.util.Date(), request.getRemoteAddr(), "Success");
            return Response.response("Success", HttpStatus.OK);
        } catch (RuntimeException e){
            log.error(log_template_error, "get", e.getMessage(), new java.util.Date(), request.getRemoteAddr(), "");
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> post(HttpServletRequest request) {
        log.info(log_template_enter, "post", new java.util.Date(), request.getRemoteAddr(), "");
        try {

            log.info(log_template_response, "post", new java.util.Date(), request.getRemoteAddr(), "Success");
            return Response.response("Success", HttpStatus.OK);
        } catch (RuntimeException e){
            log.error(log_template_error, "post", e.getMessage(), new java.util.Date(), request.getRemoteAddr(), "");
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> update(HttpServletRequest request) {
        log.info(log_template_enter, "update", new java.util.Date(), request.getRemoteAddr(), "");
        try {

            log.info(log_template_response, "update", new java.util.Date(), request.getRemoteAddr(), "Success");
            return Response.response("Success", HttpStatus.OK);
        } catch (RuntimeException e){
            log.error(log_template_error, "update", e.getMessage(), new java.util.Date(), request.getRemoteAddr(), "");
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> delete(HttpServletRequest request) {
        log.info(log_template_enter, "delete", new java.util.Date(), request.getRemoteAddr(), "");
        try {

            log.info(log_template_response, "delete", new java.util.Date(), request.getRemoteAddr(), "Success");
            return Response.response("Success", HttpStatus.OK);
        } catch (RuntimeException e){
            log.error(log_template_error, "delete", e.getMessage(), new java.util.Date(), request.getRemoteAddr(), "");
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
