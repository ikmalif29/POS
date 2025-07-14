package com.example.pos.init;

import com.example.pos.enums.RoleUsersEnums;
import com.example.pos.enums.StatusEnums;
import com.example.pos.models.*;
import com.example.pos.repository.*;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitialDataLoader {

    private final UserRepository userRepository;
    private final ProdukRepository produkRepository;
    private final TopingRepository topingRepository;
    private final BahanBakuRepository bahanBakuRepository;
    private final ResepRepository resepRepository;
    private final ResepTopingRepository resepTopingRepository;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadInitialData() {
        // 👤 USER
        if (userRepository.count() == 0) {
            userRepository.save(
                    Users.builder()
                            .nama("Admin")
                            .email("adminpos@gmail.com")
                            .password(passwordEncoder.encode("mimin123"))
                            .role(RoleUsersEnums.ADMIN)
                            .isVerified(true)
                            .status(StatusEnums.ACTIVE)
                            .build());
        }

        // 🍞 PRODUK
        if (produkRepository.count() == 0) {
            produkRepository.saveAll(List.of(
                    Produk.builder().nama("Martabak Pandan").harga(new BigDecimal("15000")).build(),
                    Produk.builder().nama("Martabak Coklat").harga(new BigDecimal("16000")).build(),
                    Produk.builder().nama("Martabak Original").harga(new BigDecimal("14000")).build()));
        }

        // 🧀 TOPING
        if (topingRepository.count() == 0) {
            topingRepository.saveAll(List.of(
                    Toping.builder().nama("Keju").harga(new BigDecimal("5000")).build(),
                    Toping.builder().nama("Pisang").harga(new BigDecimal("3000")).build(),
                    Toping.builder().nama("Coklat").harga(new BigDecimal("4000")).build()));
        }

        // 🧂 BAHAN BAKU
        if (bahanBakuRepository.count() == 0) {
            bahanBakuRepository.saveAll(List.of(
                    BahanBaku.builder().nama("Tepung").satuan("gram").stok(new BigDecimal("10000")).status("Tersedia").build(),
                    BahanBaku.builder().nama("Gula").satuan("gram").stok(new BigDecimal("5000")).status("Tersedia").build(),
                    BahanBaku.builder().nama("Telur").satuan("butir").stok(new BigDecimal("100")).status("Tersedia").build(),
                    BahanBaku.builder().nama("Keju").satuan("gram").stok(new BigDecimal("2000")).status("Tersedia").build(),
                    BahanBaku.builder().nama("Pisang").satuan("buah").stok(new BigDecimal("50")).status("Tersedia").build(),
                    BahanBaku.builder().nama("Coklat").satuan("gram").stok(new BigDecimal("1500")).status("Tersedia").build()));
        }

        // 📋 RESEP DASAR
        if (resepRepository.count() == 0) {
            List<BahanBaku> bahanList = bahanBakuRepository.findAll();
            for (BahanBaku bahan : bahanList) {
                switch (bahan.getNama().toLowerCase()) {
                    case "tepung" -> resepRepository.save(Resep.builder()
                            .bahanBaku(bahan)
                            .jumlah(new BigDecimal("100"))
                            .satuan("gram")
                            .build());
                    case "gula" -> resepRepository.save(Resep.builder()
                            .bahanBaku(bahan)
                            .jumlah(new BigDecimal("50"))
                            .satuan("gram")
                            .build());
                    case "telur" -> resepRepository.save(Resep.builder()
                            .bahanBaku(bahan)
                            .jumlah(new BigDecimal("1"))
                            .satuan("butir")
                            .build());
                }
            }
        }

        // 🍫 RESEP TOPING
        if (resepTopingRepository.count() == 0) {
            Toping keju = topingRepository.findByNama("Keju");
            Toping pisang = topingRepository.findByNama("Pisang");
            Toping coklat = topingRepository.findByNama("Coklat");

            BahanBaku kejuBahan = bahanBakuRepository.findByNamaIgnoreCase("Keju");
            BahanBaku pisangBahan = bahanBakuRepository.findByNamaIgnoreCase("Pisang");
            BahanBaku coklatBahan = bahanBakuRepository.findByNamaIgnoreCase("Coklat");

            resepTopingRepository.saveAll(List.of(
                    ResepToping.builder()
                            .toping(coklat)
                            .bahanBaku(coklatBahan)
                            .jumlah(1)
                            .satuan("gram")
                            .build(),
                    ResepToping.builder()
                            .toping(pisang)
                            .bahanBaku(pisangBahan)
                            .jumlah(1)
                            .satuan("buah")
                            .build(),
                    ResepToping.builder()
                            .toping(keju)
                            .bahanBaku(kejuBahan)
                            .jumlah(1)
                            .satuan("gram")
                            .build()));
        }

        System.out.println("✅ Initial data successfully loaded.");
    }
}
