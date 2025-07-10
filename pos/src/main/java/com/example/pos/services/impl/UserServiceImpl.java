package com.example.pos.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.pos.dto.RequestLoginDto;
import com.example.pos.dto.RequestRegistDto;
import com.example.pos.dto.ResponseLoginDto;
import com.example.pos.enums.RoleUsers;
import com.example.pos.jwt.JwtUtil;
import com.example.pos.models.Users;
import com.example.pos.repository.UserRepository;
import com.example.pos.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public ResponseLoginDto login(RequestLoginDto requestLoginDto) {
        Users user = userRepository.findByEmail(requestLoginDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(requestLoginDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password not match");
        }

        String token = jwtUtil.generateToken(user);

        return ResponseLoginDto.builder()
                .nama(user.getNama())
                .email(user.getEmail())
                .role(user.getRole().name())
                .token(token)
                .build();
    }

    @Override
    public String register(RequestRegistDto requestRegistDto) {
        Users user = userRepository.findByEmail(requestRegistDto.getEmail()).orElse(null);
        if (user != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exist");
        }

        Users newUser = Users.builder()
                .nama(requestRegistDto.getNama())
                .email(requestRegistDto.getEmail())
                .password(passwordEncoder.encode(requestRegistDto.getPassword()))
                .role(RoleUsers.KASIR)
                .build();

        userRepository.save(newUser);

        return "Register Succes";
    }


}
