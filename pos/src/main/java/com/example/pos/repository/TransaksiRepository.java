package com.example.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pos.models.Transaksi;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, Integer> {
    
}
