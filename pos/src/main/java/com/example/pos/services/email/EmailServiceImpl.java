package com.example.pos.services.email;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.pos.enums.StatusEnums;
import com.example.pos.models.Users;
import com.example.pos.repository.UserRepository;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserRepository userRepository;

    @Value("${app.verification.url}")
    private String verificationUrl;

    @Override
    public void sendVerifiedKasir(String email) {
        try{
            String emailBody = loadVerificationTemplate(email);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Verifikasi Your Email - POS");
            helper.setText(emailBody, true);
            javaMailSender.send(message); 
        }catch(Exception e){
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private String loadVerificationTemplate(String email) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/verification-email.html");
        String content = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
        String verificationLink = String.format("%s?email=%s", verificationUrl, email);
        return content.replace("{{nama}}", email)
                .replace("{{verificationUrl}}", verificationLink);
    }

    @Override
    public void sendEmailVerification(String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if(user.isVerified() == true){
            throw new RuntimeException("Email already verified");
        }

        user.setVerified(true);
        user.setStatus(StatusEnums.ACTIVE);
        userRepository.save(user);
    }
}
