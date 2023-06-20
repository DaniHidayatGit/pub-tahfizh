package com.tugasakhir.dao.user;

import com.tugasakhir.configuration.DBHandler;
import com.tugasakhir.configuration.Response;
import com.tugasakhir.model.user.UserRequest;
import com.tugasakhir.util.Helpers;
import com.tugasakhir.util.jwt.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;

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
            userRequest.setUser_password(bCryptPasswordEncoder.encode(userRequest.getUser_password()));
            Object[] obj = {
                    userRequest.getUser_name(),
                    userRequest.getUser_password(),
                    userRequest.getUser_active(),
                    userRequest.getRole_id(),
                    userRequest.getMail(),
                    userRequest.getFull_name(),
                    userRequest.getPhone(),
                    SessionUtil.getUserData(request).getUser_name()
            };
            String result = ExecuteUpdateCallPostgres("user_func_insert", obj);
            if(!result.equals("USER BERHASIL DIBUAT")){
                log.error(log_template_error, "insertUser", result, new java.util.Date(), request.getRemoteAddr(), Helpers.toJson(userRequest));
                return Response.response(result, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            log.info(log_template_response, "insertUser", new java.util.Date(), request.getRemoteAddr(), result);
            return Response.response(result, HttpStatus.OK);
        } catch (RuntimeException e){
            log.error(log_template_error, "insertUser", e.getMessage(), new java.util.Date(), request.getRemoteAddr(), Helpers.toJson(userRequest));
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
