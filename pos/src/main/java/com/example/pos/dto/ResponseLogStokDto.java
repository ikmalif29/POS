package com.example.pos.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseLogStokDto {
    private String jenis;
    private Integer jumlah;
    private LocalDateTime tanggal;
    private ResponseStokBahanBakuDto responseBahanBaku;
}
