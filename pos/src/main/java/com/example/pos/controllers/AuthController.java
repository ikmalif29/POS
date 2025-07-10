package com.example.pos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.pos.dto.GenericResponse;
import com.example.pos.dto.RequestLoginDto;
import com.example.pos.dto.RequestRegistDto;
import com.example.pos.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> postMethodName(@RequestBody RequestLoginDto requestLoginDto) {
        try{
            return ResponseEntity.ok().body(GenericResponse.success(userService.login(requestLoginDto)));
        }catch(ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(500).body(GenericResponse.error(e.getMessage()));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<Object> postMethodName(@RequestBody RequestRegistDto requestRegistDto) {
        try{
            return ResponseEntity.ok().body(GenericResponse.success(userService.register(requestRegistDto)));
        }catch(ResponseStatusException e){
            return ResponseEntity.ok().body(GenericResponse.error(e.getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(500).body(GenericResponse.error(e.getMessage()));
        }
    }
    
}
