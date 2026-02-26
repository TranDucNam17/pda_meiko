package com.example.pda;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pda.ui.kiemke.QuanLyKiemKeActivity;
import com.example.pda.ui.login.LoginActivity;
import com.example.pda.ui.nhapkho.QuanLyNhapKhoActivity;
import com.example.pda.ui.xuatkho.QuanLyXuatKhoActivity;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {
    private MaterialCardView btnInbound;
    private MaterialCardView btnOutbound;
    private MaterialCardView btnInventory;
    private ImageView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initEvents();
    }

    private void initViews() {
        btnInbound = findViewById(R.id.btnInbound);
        btnOutbound = findViewById(R.id.btnOutbound);
        btnInventory = findViewById(R.id.btnInventory);
        btnLogout = findViewById(R.id.btnLogout);

    }

    private void initEvents() {
        btnInbound.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuanLyNhapKhoActivity.class);
            startActivity(intent);
        });

        btnOutbound.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuanLyXuatKhoActivity.class);
            startActivity(intent);
        });

        btnInventory.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuanLyKiemKeActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}