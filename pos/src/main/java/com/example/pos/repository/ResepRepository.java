package com.example.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pos.models.Resep;

@Repository
public interface ResepRepository extends JpaRepository<Resep, Integer>{
    Resep findByBahanBakuId(Integer id);
}
