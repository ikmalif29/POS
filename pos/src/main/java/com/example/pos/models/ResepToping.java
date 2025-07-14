package com.example.pos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResepToping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer jumlah;
    private String satuan;
    
    @ManyToOne
    @JoinColumn(name = "resep_id", referencedColumnName = "id", nullable = false)
    private Toping toping;

    @ManyToOne
    @JoinColumn(name = "bahan_baku_id", referencedColumnName = "id", nullable = false)
    private BahanBaku bahanBaku;
}
