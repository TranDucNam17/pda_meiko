package com.example.pda.ui.nhapkho.po;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pda.R;

public class NhapKhoPoDetailActivity extends AppCompatActivity {

    private ImageView btnBack;
    private CheckBox cbSelectAll;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_kho_po_detail);

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
                Toast.makeText(this, "Đã chọn tất cả", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đã bỏ chọn tất cả", Toast.LENGTH_SHORT).show();
            }
            // Logic to check/uncheck all items in list would go here
        });

        btnSubmit.setOnClickListener(v -> {
            Toast.makeText(this, "Đang thực hiện nhập kho...", Toast.LENGTH_SHORT).show();
            // Business logic for submission
        });
    }
}