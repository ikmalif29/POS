package com.example.pos.services.kasir;

import java.util.List;

import com.example.pos.dto.RequestKasirAddDto;
import com.example.pos.models.Users;

public interface KasirService {
        String addKasir(RequestKasirAddDto requestKasirAddDto);
        List<Users> getAllKasir();
        String deleteSoftKasir(Integer id);
}
