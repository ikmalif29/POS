package com.example.pos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DetailTransaksi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetailTransaksi;
    private Integer jumlah;
    private Integer hargaProduk;
    private Integer subTotal;

    @ManyToOne
    @JoinColumn(name = "varian_id", referencedColumnName = "id", nullable = false)
    private Produk varian;

    @ManyToOne
    @JoinColumn(name = "transaksi_id", referencedColumnName = "id", nullable = false)
    private Transaksi transaksi;
}
