package com.example.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pos.models.ResepToping;

@Repository
public interface ResepTopingRepository extends JpaRepository<ResepToping, Integer>{
    
}
