package com.example.pda.ui.kiemke.barcode;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pda.R;

public class KiemKeBarcodeDetailActivity extends AppCompatActivity {

    private ImageView btnBack;
    private View btnEditSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiem_ke_barcode_detail);

        initViews();
        initEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnEditSample = findViewById(R.id.btnEdit); 
    }

    private void initEvents() {
        btnBack.setOnClickListener(v -> finish());

        if (btnEditSample != null) {
            btnEditSample.setOnClickListener(v -> showEditDialog());
        }
    }

    private void showEditDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_kiem_ke_barcode);
        dialog.setCancelable(true);

        EditText edtLot = dialog.findViewById(R.id.edtEditLot);
        EditText edtShelf = dialog.findViewById(R.id.edtEditShelf);
        EditText edtExpiryDate = dialog.findViewById(R.id.edtEditExpiryDate);
        EditText edtWarehouse = dialog.findViewById(R.id.edtEditWarehouse);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSave = dialog.findViewById(R.id.btnSave);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String newLot = edtLot.getText().toString().trim();
            if (!newLot.isEmpty()) {
                Toast.makeText(this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Vui lòng nhập mã Lot", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}