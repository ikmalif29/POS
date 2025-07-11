package com.example.pos.models;

import com.example.pos.enums.RoleUsersEnums;
import com.example.pos.enums.StatusEnums;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nama;
    private String email;
    private String password;
    private boolean isVerified;
    
    @Enumerated(EnumType.STRING)
    private StatusEnums status;

    @Enumerated(EnumType.STRING)
    private RoleUsersEnums role;
}
