package com.tugasakhir.dao.menu;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface MasterMenuDao {
    ResponseEntity<?> getMenuJson(HttpServletRequest request);

    ResponseEntity<?> getAllMenuJson();
}
