package com.example.pos.services.email;

public interface EmailService {
    void sendVerifiedKasir(String email);
    void sendEmailVerification(String email);
    void sendKodeOtp(String email, String kodeOtp);
}
