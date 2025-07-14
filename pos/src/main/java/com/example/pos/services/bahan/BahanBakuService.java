package com.example.pos.services.bahan;

import java.util.List;

import com.example.pos.dto.RequestBahanBakuDto;
import com.example.pos.dto.ResponseLogStokDto;
import com.example.pos.dto.ResponseStokBahanBakuDto;

public interface BahanBakuService {
    String addBahanBaku(RequestBahanBakuDto requestBahanBakuDto);

    List<ResponseStokBahanBakuDto> getAllStokBahanBaku();
    List<ResponseLogStokDto> getAllLogStokBahanBaku();
}
