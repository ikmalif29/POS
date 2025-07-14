package com.example.pos.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaksiResponse {
    private String namaKasir;
    private LocalDateTime tanggalTransaksi;
    private List<ItemsRequestDto> item;
    private Integer totalHarga;
    private Integer uangBayar;
    private Integer uangKembalian;
}
