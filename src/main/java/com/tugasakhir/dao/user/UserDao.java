package com.tugasakhir.dao.user;

import com.tugasakhir.model.UserRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserDao {
    ResponseEntity<?> insertUser(UserRequest userRequest, HttpServletRequest request);
    ResponseEntity<?> getUser(String user_id, HttpServletRequest request);
}
