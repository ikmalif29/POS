package com.example.pos.services.bahan;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.math3.analysis.function.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pos.dto.RequestBahanBakuDto;
import com.example.pos.dto.ResponseLogStokDto;
import com.example.pos.dto.ResponseStokBahanBakuDto;
import com.example.pos.models.BahanBaku;
import com.example.pos.models.LogStok;
import com.example.pos.repository.BahanBakuRepository;
import com.example.pos.repository.LogStokRepository;

@Service
public class BahanBakuServiceImpl implements BahanBakuService {
    @Autowired
    BahanBakuRepository bahanBakuRepository;

    @Autowired
    LogStokRepository logStokRepository;

    @Override
    public String addBahanBaku(RequestBahanBakuDto requestBahanBakuDto) {
        BahanBaku bahanBaku = bahanBakuRepository.findByNamaIgnoreCase(requestBahanBakuDto.getNamaBahanBaku());
        if (bahanBaku != null) {
            bahanBaku.setStok(
                    bahanBaku.getStok().add(
                            BigDecimal.valueOf(requestBahanBakuDto.getStok())));

            bahanBakuRepository.save(bahanBaku);
            saveLogStok(bahanBaku);
        } else {
            throw new RuntimeException("Bahan baku not found");
        }

        String satuan = bahanBaku.getSatuan().toLowerCase();
        BigDecimal stok = bahanBaku.getStok();
        String status;

        if (stok.compareTo(BigDecimal.ZERO) == 0) {
            status = "Habis";
        } else if ((satuan.equals("gram") && stok.compareTo(BigDecimal.valueOf(100)) < 0) ||
                (satuan.equals("buah") && stok.compareTo(BigDecimal.valueOf(50)) < 0) ||
                (satuan.equals("butir") && stok.compareTo(BigDecimal.valueOf(100)) < 0)) {
            status = "Menipis";
        } else {
            status = "Tersedia";
        }

        bahanBaku.setStatus(status);
        bahanBakuRepository.save(bahanBaku);

        return bahanBaku.getNama();
    }

    @Override
    public List<ResponseStokBahanBakuDto> getAllStokBahanBaku() {
        List<BahanBaku> bahanBakus = bahanBakuRepository.findAll();
        return bahanBakus.stream().map(bahanBaku -> ResponseStokBahanBakuDto.builder()
                .namaBahanBaku(bahanBaku.getNama())
                .stok(bahanBaku.getStok().intValue())
                .status(bahanBaku.getStatus())
                .build()).toList();
    }

    @Override
    public List<ResponseLogStokDto> getAllLogStokBahanBaku() {
        List<LogStok> logStoks = logStokRepository.findAll();
        return logStoks.stream().map(logStok -> ResponseLogStokDto.builder()
                .jenis(logStok.getJenis())
                .jumlah(logStok.getJumlah())
                .tanggal(logStok.getTanggal())
                .responseBahanBaku(ResponseStokBahanBakuDto.builder()
                        .namaBahanBaku(logStok.getBahanBaku().getNama())
                        .satuan(logStok.getBahanBaku().getSatuan())
                        .stok(logStok.getBahanBaku().getStok().intValue())
                        .status(logStok.getBahanBaku().getStatus())
                        .build())
                .build()).toList();
    }

    private LogStok saveLogStok(BahanBaku bahanBaku) {
        LogStok logStok = LogStok.builder()
                .bahanBaku(bahanBaku)
                .jenis("Masuk")
                .jumlah(bahanBaku.getStok().intValue())
                .tanggal(LocalDateTime.now())
                .build();
        logStokRepository.save(logStok);
        return logStok;
    }

}
