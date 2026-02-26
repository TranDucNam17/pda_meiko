package com.example.pda.ui.nhapkho;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pda.R;
import com.example.pda.ui.nhapkho.invoice.NhapKhoInvoiceDetailActivity;
import com.example.pda.ui.nhapkho.nvl.NhapKhoNvlDetailActivity;
import com.example.pda.ui.nhapkho.po.NhapKhoPoDetailActivity;
import com.google.android.material.card.MaterialCardView;

public class QuanLyNhapKhoActivity extends AppCompatActivity {

    private ImageView btnBack;
    private MaterialCardView cardPo;
    private MaterialCardView cardMaterial;
    private MaterialCardView cardInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nhap_kho);

        initViews();
        initEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        cardPo = findViewById(R.id.cardPo);
        cardMaterial = findViewById(R.id.cardMaterial);
        cardInvoice = findViewById(R.id.cardInvoice);
    }

    private void initEvents() {
        btnBack.setOnClickListener(v -> finish());

        cardPo.setOnClickListener(v -> {
            Intent intent = new Intent(this, NhapKhoPoDetailActivity.class);
            startActivity(intent);
        });

        cardMaterial.setOnClickListener(v -> {
            Intent intent = new Intent(this, NhapKhoNvlDetailActivity.class);
            startActivity(intent);
        });

        cardInvoice.setOnClickListener(v -> {
            Intent intent = new Intent(this, NhapKhoInvoiceDetailActivity.class);
            startActivity(intent);
        });
    }
}