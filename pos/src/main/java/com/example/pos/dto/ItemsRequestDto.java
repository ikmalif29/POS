package com.example.pos.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemsRequestDto {
    private Integer idProduk;
    private Integer jumlah;
    private List<ItemsTopingRequestDto> itemToping;
}
