package com.tugasakhir.dao.user;

import com.tugasakhir.model.user.UserRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserDao {
    ResponseEntity<?> insertUser(UserRequest userRequest, HttpServletRequest request);
}
