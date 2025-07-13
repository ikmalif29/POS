package com.example.pos.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.pos.dto.GenericResponse;
import com.example.pos.dto.RequestKasirAddDto;
import com.example.pos.services.kasir.KasirService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    KasirService kasirService;

    @PostMapping("/add-kasir")
    public ResponseEntity<Object> postMethodName(@RequestBody RequestKasirAddDto requestKasirAddDto) {
        try{
            return ResponseEntity.ok().body(GenericResponse.success(kasirService.addKasir(requestKasirAddDto)));
        }catch(ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(500).body(GenericResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/get-all-kasir")
    public ResponseEntity<Object> getMethodName() {
        try{
            return ResponseEntity.ok().body(GenericResponse.success(kasirService.getAllKasir()));
        }catch(Exception e){
            return ResponseEntity.status(500).body(GenericResponse.error(e.getMessage()));
        }
    }


    @PutMapping("/delete-kasir/{id}")
    public ResponseEntity<Object> putMethodName(@PathVariable Integer id) {
        try{
            return ResponseEntity.ok().body(GenericResponse.success(kasirService.deleteSoftKasir(id)));
        }catch(Exception e){
            return ResponseEntity.status(500).body(GenericResponse.error(e.getMessage()));
        }
    }
}
