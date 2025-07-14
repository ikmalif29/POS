package com.example.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pos.models.Produk;

@Repository
public interface ProdukRepository extends JpaRepository<Produk, Integer> {
    
}
