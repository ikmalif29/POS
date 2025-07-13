package com.example.pos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.pos.dto.GenericResponse;
import com.example.pos.dto.RequestForgetPassDto;
import com.example.pos.dto.RequestLoginDto;
import com.example.pos.dto.RequestResetPassDto;
import com.example.pos.services.email.EmailServiceImpl;
import com.example.pos.services.user.ForgetPassService;
import com.example.pos.services.user.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private ForgetPassService forgetPassService;

    @PostMapping("/login")
    public ResponseEntity<Object> postMethodName(@RequestBody RequestLoginDto requestLoginDto) {
        System.out.println("asaksk");
        try {
            return ResponseEntity.ok().body(GenericResponse.success(userService.login(requestLoginDto)));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(GenericResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/verified")
    public ResponseEntity<Object> postMethodName(@RequestParam String email) {
        try {
            emailService.sendEmailVerification(email);
            return ResponseEntity.ok().body(GenericResponse.success(null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(GenericResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> postMethodName(@RequestBody RequestForgetPassDto requestForgetPassDto) {
        try{
            forgetPassService.sendEmailForgetPass(requestForgetPassDto);
            return ResponseEntity.ok().body(GenericResponse.success(null));
        }catch(ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(500).body(GenericResponse.error(e.getMessage()));
        }
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<Object> postMethodName(@RequestBody RequestResetPassDto requestResetPassDto) {
        try{
            forgetPassService.resetPass(requestResetPassDto);
            return ResponseEntity.ok().body(GenericResponse.success(null));
        }catch(ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(GenericResponse.error(e.getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(500).body(GenericResponse.error(e.getMessage()));
        }
    }
    
}
