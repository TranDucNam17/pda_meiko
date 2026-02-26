package com.example.pda.ui.nhapkho.nvl;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pda.R;

public class NhapKhoNvlDetailActivity extends AppCompatActivity {

    private ImageView btnBack;
    private CheckBox cbSelectAll;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_kho_nvl_detail);

        initViews();
        initEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        cbSelectAll = findViewById(R.id.cbSelectAll);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    private void initEvents() {
        btnBack.setOnClickListener(v -> finish());

        cbSelectAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Đã chọn tất cả NVL", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đã bỏ chọn tất cả NVL", Toast.LENGTH_SHORT).show();
            }
        });

        btnSubmit.setOnClickListener(v -> {
            Toast.makeText(this, "Đang xử lý nhập kho NVL...", Toast.LENGTH_SHORT).show();
        });
    }
}