package com.tugasakhir.dao.login;

import com.tugasakhir.model.LoginRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface LoginDao {
    ResponseEntity<?> doLogin(LoginRequest loginRequest, HttpServletRequest request);
    ResponseEntity<?> doLogout(HttpServletRequest request);

}
