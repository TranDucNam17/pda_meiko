package com.example.pda.ui.xuatkho;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pda.R;
import com.google.android.material.card.MaterialCardView;

public class QuanLyXuatKhoActivity extends AppCompatActivity {

    private ImageView btnBack;
    private MaterialCardView cardXuatNvl;
    private MaterialCardView cardXuatLot;
    private MaterialCardView cardXuatLabel;
    private MaterialCardView cardXuatShelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_xuat_kho);

        initViews();
        initEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        cardXuatNvl = findViewById(R.id.cardXuatNvl);
        cardXuatLot = findViewById(R.id.cardXuatLot);
        cardXuatLabel = findViewById(R.id.cardXuatLabel);
        cardXuatShelf = findViewById(R.id.cardXuatShelf);
    }

    private void initEvents() {
        btnBack.setOnClickListener(v -> finish());

        cardXuatNvl.setOnClickListener(v -> Toast.makeText(this, "Xuất theo NVL", Toast.LENGTH_SHORT).show());
        cardXuatLot.setOnClickListener(v -> Toast.makeText(this, "Xuất theo Lô/Lot", Toast.LENGTH_SHORT).show());
        cardXuatLabel.setOnClickListener(v -> Toast.makeText(this, "Xuất theo Tem", Toast.LENGTH_SHORT).show());
        cardXuatShelf.setOnClickListener(v -> Toast.makeText(this, "Xuất theo Kệ", Toast.LENGTH_SHORT).show());
    }
}