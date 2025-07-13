package com.example.pos.services.kasir;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.pos.dto.RequestKasirAddDto;
import com.example.pos.enums.RoleUsersEnums;
import com.example.pos.enums.StatusEnums;
import com.example.pos.models.Users;
import com.example.pos.repository.UserRepository;
import com.example.pos.services.email.EmailServiceImpl;

@Service
public class KasirServiceImpl implements KasirService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailServiceImpl emailService;

    @Override
    public String addKasir(RequestKasirAddDto requestKasirAddDto) {
        if(userRepository.findByEmail(requestKasirAddDto.getEmail()).isPresent()){
            throw new RuntimeException("Email already exist");
        }

        Users user = Users.builder()
        .nama(requestKasirAddDto.getNama())
        .email(requestKasirAddDto.getEmail())
        .password(passwordEncoder.encode(requestKasirAddDto.getPassword()))
        .role(RoleUsersEnums.KASIR)
        .isVerified(false)
        .status(StatusEnums.PENDING)
        .build();
        userRepository.save((user));

        emailService.sendVerifiedKasir(user.getEmail());
        return "waiting for verification from the cashier";
    }

    @Override
    public List<Users> getAllKasir() {
        List<Users> kasir = userRepository.findByRole(RoleUsersEnums.KASIR);

        if(kasir.isEmpty()){
            throw new RuntimeException("Kasir not found");
        }

        return kasir;
    }

    @Override
    public String deleteSoftKasir(Integer id) {
        Users kasir = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Kasir not found"));

        if(kasir.getRole() != RoleUsersEnums.KASIR){
            throw new RuntimeException("Kasir not found");
        }

        kasir.setStatus(StatusEnums.INACTIVE);
        kasir.setVerified(false);
        userRepository.save(kasir);
        return "Kasir deleted";
    }
}
