package com.example.pos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pos.enums.RoleUsersEnums;
import com.example.pos.models.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
    List<Users> findByRole(RoleUsersEnums role);
}
