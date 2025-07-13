package com.example.pos.services.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.pos.dto.RequestForgetPassDto;
import com.example.pos.dto.RequestResetPassDto;
import com.example.pos.enums.StatusEnums;
import com.example.pos.models.Users;
import com.example.pos.repository.UserRepository;
import com.example.pos.services.email.EmailServiceImpl;

@Service
public class ForgetPassServiceImpl implements ForgetPassService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String email;

    @Override
    public void sendEmailForgetPass(RequestForgetPassDto requestForgetPassDto) {
        email = requestForgetPassDto.getEmail();
        Users user = userRepository.findByEmail(requestForgetPassDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isVerified() == false || user.getStatus() != StatusEnums.ACTIVE) {
            throw new RuntimeException("Email not verified");
        }

        String kodeOtp = UUID.randomUUID().toString().substring(0, 8);
        LocalDateTime expiresKode = LocalDateTime.now().plusMinutes(5);
        user.setExpiresKodeOtp(expiresKode);
        user.setKodeOtp(kodeOtp);
        userRepository.save(user);

        emailService.sendKodeOtp(user.getEmail(), kodeOtp);
    }

    @Override
    public void resetPass(RequestResetPassDto requestResetPassDto) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isVerified() == false || user.getStatus() != StatusEnums.ACTIVE) {
            throw new RuntimeException("Email not verified");
        }

        if (user.getExpiresKodeOtp().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Kode OTP expired");
        }

        if (!user.getKodeOtp().equals(requestResetPassDto.getKodeOtp())) {
            throw new RuntimeException("Kode OTP not match");
        }

        user.setPassword(passwordEncoder.encode(requestResetPassDto.getNewPassword()));
        user.setExpiresKodeOtp(null);
        user.setKodeOtp(null);
        userRepository.save(user);
    }
}
