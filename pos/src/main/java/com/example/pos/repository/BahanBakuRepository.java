package com.example.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pos.models.BahanBaku;

@Repository
public interface BahanBakuRepository extends JpaRepository<BahanBaku, Integer>{
    BahanBaku findByNamaIgnoreCase(String nama);
}
