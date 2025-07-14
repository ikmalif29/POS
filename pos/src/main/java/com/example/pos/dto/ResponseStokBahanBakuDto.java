package com.example.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseStokBahanBakuDto {
    private String namaBahanBaku;
    private String satuan;
    private Integer stok;
    private String status;
}
