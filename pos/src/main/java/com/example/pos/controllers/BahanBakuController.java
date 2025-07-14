package com.example.pos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.pos.dto.GenericResponse;
import com.example.pos.dto.RequestBahanBakuDto;
import com.example.pos.services.bahan.BahanBakuService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/bahanbaku")
public class BahanBakuController {
    @Autowired
    private BahanBakuService bahanBakuService;

    @PostMapping("/add")
    public ResponseEntity<Object> addBahanBaku(@RequestBody RequestBahanBakuDto requestBahanBakuDto) {
        try {
            return ResponseEntity.ok()
                    .body(GenericResponse.success(bahanBakuService.addBahanBaku(requestBahanBakuDto)));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(GenericResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllBahanBaku() {
        try {
            return ResponseEntity.ok().body(GenericResponse.success(bahanBakuService.getAllStokBahanBaku()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(GenericResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/get-all-log")
    public ResponseEntity<Object> getMethodName() {
        try {
            return ResponseEntity.ok().body(GenericResponse.success(bahanBakuService.getAllLogStokBahanBaku()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(GenericResponse.error(e.getMessage()));
        }
    }
    

}
