package com.tugasakhir.controller;

import com.tugasakhir.dao.user.UserDao;
import com.tugasakhir.model.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "USER")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserDao userDao;
    @Operation(summary = "CREATE USER")
    @PostMapping("/create")
    public ResponseEntity<?> createUser(
            @RequestBody UserRequest userRequest,
            HttpServletRequest request
    ){
        return userDao.insertUser(userRequest, request);
    }

    @Operation(summary = "UPDATE USER")
    @PostMapping("/update")
    public ResponseEntity<?> updateUser(
            @RequestBody UserRequest userRequest,
            HttpServletRequest request
    ){
        return userDao.updateUser(userRequest, request);
    }
    @Operation(summary = "DELETE USER")
    @PostMapping("/delete")
    public ResponseEntity<?> updateUser(
            @RequestParam String user_name,
            HttpServletRequest request
    ){
        return userDao.deleteUser(user_name, request);
    }
    @Operation(summary = "GET USER")
    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(
            @RequestParam(required = false, defaultValue = "") String user_id,
            HttpServletRequest request
    ){
        return userDao.getUser(user_id, request);
    }
}
