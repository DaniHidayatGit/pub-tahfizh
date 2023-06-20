package com.tugasakhir.controller;

import com.tugasakhir.dao.menu.MasterMenuDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "MENU")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MasterMenuDao masterMenuDao;

    @Operation(summary = "GET MENU")
    @GetMapping("/buildmenu")
    public ResponseEntity<?> getMenuJson(HttpServletRequest request) {
        return masterMenuDao.getMenuJson(request);
    }
}
