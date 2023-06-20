package com.tugasakhir.controller;

import com.tugasakhir.configuration.Response;
import com.tugasakhir.dao.login.LoginDao;
import com.tugasakhir.model.login.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "LOGIN")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginDao loginDao;
    private final AuthenticationManager authenticationManager;
    @Operation(summary = "LOGIN")
    @PostMapping("/login")
    public ResponseEntity<?> doLogin(
            @RequestBody LoginRequest loginRequest,
             HttpServletRequest request
    ){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUser_name(), loginRequest.getUser_password()));
            return loginDao.doLogin(loginRequest, request);
        } catch (RuntimeException e){
            return Response.response("USER TIDAK DITEMUKAN", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "SESSION END")
    @GetMapping("/logout")
    public ResponseEntity<?> doLogout(
            HttpServletRequest request
    ){
        return loginDao.doLogout(request);
    }
}
