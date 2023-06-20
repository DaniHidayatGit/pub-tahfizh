package com.tugasakhir.dao.dao;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface Dao {
    ResponseEntity<?> get(HttpServletRequest request);
    ResponseEntity<?> post(HttpServletRequest request);
    ResponseEntity<?> update(HttpServletRequest request);
    ResponseEntity<?> delete(HttpServletRequest request);
}
