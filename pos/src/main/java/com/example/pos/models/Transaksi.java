package com.example.pos.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaksi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime tanggalTransaksi;
    private Integer totalHarga;
    private Integer uangBayar;
    private Integer uangKembalian;

    @ManyToOne
    @JoinColumn(name = "kasir_id", referencedColumnName = "id", nullable = false)
    private Users usersKasir;
}
