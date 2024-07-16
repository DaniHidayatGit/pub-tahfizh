package com.tugasakhir.dao.user;

import com.tugasakhir.configuration.DBHandler;
import com.tugasakhir.configuration.Response;
import com.tugasakhir.model.UserRequest;
import com.tugasakhir.util.Helpers;
import com.tugasakhir.util.jwt.JwtTokenResponse;
import com.tugasakhir.util.jwt.SessionUtil;
import com.tugasakhir.util.mapper.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;

import static com.tugasakhir.util.Helpers.*;

@Slf4j
@Service
@Transactional
public class UserDaoImpl extends DBHandler implements UserDao {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserDaoImpl(DataSource dataSource, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.setDataSource(dataSource);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private final String log_template_enter = "\u001B[34m" + "ENTER -- PROCESSES: {} -- DATE: {} -- IP: {} -- Param: {}" + "\u001B[0m";
    private final String log_template_sout = "\u001B[34m" + "SOUT -- {}" + "\u001B[0m";
    private final String log_template_response = "\u001B[32m" + "EXIT -- PROCESS: {} -- DATE: {} -- IP: {} -- Response: {}" + "\u001B[0m";
    private final String log_template_error = "\u001B[31m" + "ERROR -- PROCESS: {} -- MSG: {} -- DATE: {} -- IP: {} -- Param: {}"+ "\u001B[0m";


    @Override
    public ResponseEntity<?> insertUser(UserRequest userRequest, HttpServletRequest request) {
        log.info(log_template_enter, "insertUser", new java.util.Date(), request.getRemoteAddr(), Helpers.toJson(userRequest));
        try {
            if (userRequest.getUser_name() == null || userRequest.getUser_name().isEmpty() ||
                    userRequest.getUser_password() == null || userRequest.getUser_password().isEmpty() ||
                    userRequest.getUser_active() == null ||
                    userRequest.getRole_id() == null ||
                    userRequest.getMail() == null || userRequest.getMail().isEmpty() ||
                    userRequest.getFull_name() == null || userRequest.getFull_name().isEmpty() ||
                    userRequest.getPhone() == null || userRequest.getPhone().isEmpty()) {
                throw new RuntimeException("Mohon isi semua data!");
            }

            isValidTextCombo(userRequest.getFull_name(), "");

            userRequest.setUser_password(bCryptPasswordEncoder.encode(userRequest.getUser_password()));
            JwtTokenResponse response = SessionUtil.getUserData(request);
            Object[] obj = {
                    userRequest.getUser_name(),
                    userRequest.getUser_password(),
                    userRequest.getRole_id(),
                    userRequest.getMail(),
                    userRequest.getUser_active(),
                    response.getUser_name(),
                    userRequest.getFull_name(),
                    userRequest.getPhone(),
                    "alamat",
                    "foto"
            };
            String result = ExecuteUpdateCallPostgres("user_func_create", obj);
            if(!result.equals("USER BERHASIL DIBUAT")){
                log.error(log_template_error, "insertUser", result, new java.util.Date(), request.getRemoteAddr(), Helpers.toJson(userRequest));
                return Response.response(result, HttpStatus.BAD_REQUEST);
            }
            log.info(log_template_response, "insertUser", new java.util.Date(), request.getRemoteAddr(), result);
            return Response.response("Data berhasil disimpan!", HttpStatus.OK);
        } catch (RuntimeException e){
            log.error(log_template_error, "insertUser", e.getMessage(), new java.util.Date(), request.getRemoteAddr(), Helpers.toJson(userRequest));
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<?> updateUser(UserRequest userRequest, HttpServletRequest request) {
        log.info(log_template_enter, "updateUser", new java.util.Date(), request.getRemoteAddr(), Helpers.toJson(userRequest));
        try {
            JwtTokenResponse response = SessionUtil.getUserData(request);
            userRequest.setUser_password(bCryptPasswordEncoder.encode(userRequest.getUser_password()));
            Object[] obj = {
                    userRequest.getUser_name(),
                    userRequest.getUser_password(),
                    userRequest.getRole_id(),
                    userRequest.getMail(),
                    userRequest.getUser_active(),
                    response.getUser_name(),
                    userRequest.getFull_name(),
                    userRequest.getPhone(),
                    "alamat",
                    "foto"
            };
            String result = ExecuteUpdateCallPostgres("user_func_update", obj);
            if(!result.equals("Success")){
                log.error(log_template_error, "updateUser", result, new java.util.Date(), request.getRemoteAddr(), Helpers.toJson(userRequest));
                return Response.response(result, HttpStatus.BAD_REQUEST);
            }
            log.info(log_template_response, "updateUser", new java.util.Date(), request.getRemoteAddr(), result);
            return Response.response("Data berhasil disimpan!", HttpStatus.OK);
        } catch (RuntimeException e){
            log.error(log_template_error, "updateUser", e.getMessage(), new java.util.Date(), request.getRemoteAddr(), Helpers.toJson(userRequest));
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> deleteUser(String user_name, HttpServletRequest request) {
        log.info(log_template_enter, "deleteUser", new java.util.Date(), request.getRemoteAddr(), user_name);
        try {
            Object[] obj = {
                    user_name
            };
            String result = ExecuteUpdateCallPostgres("user_func_delete", obj);
            if(!result.equals("Success")){
                log.error(log_template_error, "deleteUser", result, new java.util.Date(), request.getRemoteAddr(), user_name);
                return Response.response(result, HttpStatus.BAD_REQUEST);
            }
            log.info(log_template_response, "deleteUser", new java.util.Date(), request.getRemoteAddr(), result);
            return Response.response("User berhasil didelete", HttpStatus.OK);
        } catch (RuntimeException e){
            log.error(log_template_error, "deleteUser", e.getMessage(), new java.util.Date(), request.getRemoteAddr(), user_name);
            return Response.response(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getUser(String user_id, HttpServletRequest request) {
        try {
            Object[] obj = {user_id};
            List<LinkedHashMap<String, String>> linkedHashMaps = ExecuteCallPostgre("user_func_getuser", obj, new Mapper());
            return Response.response(linkedHashMaps, HttpStatus.OK);
        } catch (RuntimeException e){
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
