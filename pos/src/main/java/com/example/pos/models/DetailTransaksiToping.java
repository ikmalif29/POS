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
public class DetailTransaksiToping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetailTransaksiToping;
    private Integer hargaToping;

    @ManyToOne
    @JoinColumn(name = "detail_transaksi_id", referencedColumnName = "idDetailTransaksi", nullable = false)
    private DetailTransaksi detailTransaksi;

    @ManyToOne
    @JoinColumn(name = "toping_id", referencedColumnName = "id", nullable = false)
    private Toping toping;
}
