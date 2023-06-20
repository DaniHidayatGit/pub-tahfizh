package com.tugasakhir.controller;

import com.tugasakhir.dao.dao.Dao;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "CONTROLLER METHOD")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RestController
@RequestMapping("/controller")
@RequiredArgsConstructor
public class Controller {
    private final Dao dao;
    @GetMapping("/get")
    public ResponseEntity<?> get(HttpServletRequest request){
        return dao.get(request);
    }
    @PostMapping("/post")
    public ResponseEntity<?> post(HttpServletRequest request){
        return dao.post(request);
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest request){
        return dao.update(request);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(HttpServletRequest request){
        return dao.delete(request);
    }
}
