package com.example.pos.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.pos.enums.RoleUsersEnums;
import com.example.pos.enums.StatusEnums;
import com.example.pos.models.Users;
import com.example.pos.repository.UserRepository;

@Component
public class InitialDataLoader implements ApplicationRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.findAll().isEmpty()) {
            Users user = Users.builder()
            .nama("Admin")
            .email("adminpos@gmail.com")
            .password(passwordEncoder.encode("mimin123"))
            .role(RoleUsersEnums.ADMIN)
            .isVerified(true)
            .status(StatusEnums.ACTIVE)
            .build();
            userRepository.save(user);
        }
    }    
}
