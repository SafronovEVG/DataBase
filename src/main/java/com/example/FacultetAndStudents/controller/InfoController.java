package com.example.FacultetAndStudents.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {
    @Value("${server.port}")
    private String port;

    @GetMapping("/port")
    public ResponseEntity<String> getPort() {
        return ResponseEntity.ok(port);
    }
}
