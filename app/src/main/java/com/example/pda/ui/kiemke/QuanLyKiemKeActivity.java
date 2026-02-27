package com.example.pda.ui.kiemke;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pda.R;
import com.example.pda.ui.kiemke.barcode.KiemKeBarcodeDetailActivity;
import com.example.pda.ui.kiemke.giado.KiemKeGiaDoDetailActivity;
import com.example.pda.ui.kiemke.kho.KiemKeKhoDetailActivity;
import com.example.pda.ui.kiemke.lot.KiemKeLotDetailActivity;
import com.example.pda.ui.kiemke.nvl.KiemKeNvlDetailActivity;
import com.google.android.material.card.MaterialCardView;

public class QuanLyKiemKeActivity extends AppCompatActivity {

    private ImageView btnBack;
    private MaterialCardView cardKiemKeBarcode;
    private MaterialCardView cardKiemKeGiaDo;
    private MaterialCardView cardKiemKeNvl;
    private MaterialCardView cardKiemKeLot;
    private MaterialCardView cardKiemKeKho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_kiem_ke);

        initViews();
        initEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        cardKiemKeBarcode = findViewById(R.id.cardKiemKeBarcode);
        cardKiemKeGiaDo = findViewById(R.id.cardKiemKeGiaDo);
        cardKiemKeNvl = findViewById(R.id.cardKiemKeNvl);
        cardKiemKeLot = findViewById(R.id.cardKiemKeLot);
        cardKiemKeKho = findViewById(R.id.cardKiemKeKho);
    }

    private void initEvents() {
        btnBack.setOnClickListener(v -> finish());

        cardKiemKeBarcode.setOnClickListener(v -> {
            Intent intent = new Intent(this, KiemKeBarcodeDetailActivity.class);
            startActivity(intent);
        });

        cardKiemKeGiaDo.setOnClickListener(v -> {
            Intent intent = new Intent(this, KiemKeGiaDoDetailActivity.class);
            startActivity(intent);
        });

        cardKiemKeNvl.setOnClickListener(v -> {
            Intent intent = new Intent(this, KiemKeNvlDetailActivity.class);
            startActivity(intent);
        });

        cardKiemKeLot.setOnClickListener(v -> {
            Intent intent = new Intent(this, KiemKeLotDetailActivity.class);
            startActivity(intent);
        });

        cardKiemKeKho.setOnClickListener(v -> {
            Intent intent = new Intent(this, KiemKeKhoDetailActivity.class);
            startActivity(intent);
        });
    }
}