package com.example.pda.ui.kiemke.giado;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pda.R;

import java.util.Calendar;

public class KiemKeGiaDoDetailActivity extends AppCompatActivity {

    private ImageView btnBack;
    private ViewGroup containerData;
    private CheckBox checkAll;
    private TextView tvSelectedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiem_ke_giado_detail);

        initViews();
        initEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        containerData = findViewById(R.id.containerData);
        checkAll = findViewById(R.id.checkAll);
        tvSelectedCount = findViewById(R.id.tvSelectedCount);
    }

    private void initEvents() {
        btnBack.setOnClickListener(v -> finish());

        if (checkAll != null) {
            checkAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (containerData != null) {
                    for (int i = 0; i < containerData.getChildCount(); i++) {
                        View itemView = containerData.getChildAt(i);
                        CheckBox cb = itemView.findViewById(R.id.checkBox);
                        if (cb != null) cb.setChecked(isChecked);
                    }
                    updateSelectedCount();
                }
            });
        }

        if (containerData != null) {
            for (int i = 0; i < containerData.getChildCount(); i++) {
                View child = containerData.getChildAt(i);
                child.setOnClickListener(v -> showFullInfoDialog());

                CheckBox cb = child.findViewById(R.id.checkBox);
                if (cb != null) {
                    cb.setOnClickListener(v -> updateSelectedCount());
                }

                View btnEdit = child.findViewById(R.id.btnEdit);
                if (btnEdit != null) {
                    btnEdit.setOnClickListener(v -> showEditDialog());
                }
            }
        }
    }

    private void updateSelectedCount() {
        int count = 0;
        if (containerData != null) {
            for (int i = 0; i < containerData.getChildCount(); i++) {
                CheckBox cb = containerData.getChildAt(i).findViewById(R.id.checkBox);
                if (cb != null && cb.isChecked()) count++;
            }
        }
        if (tvSelectedCount != null) {
            tvSelectedCount.setText("Đã chọn: " + count);
        }
    }

    private void showEditDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_kiem_ke);
        dialog.setCancelable(true);

        EditText edtLotNumber = dialog.findViewById(R.id.edtLotNumber);
        EditText edtShelfCode = dialog.findViewById(R.id.edtShelfCode);
        EditText edtAuditDate = dialog.findViewById(R.id.edtAuditDate);
        EditText edtExpiryDate = dialog.findViewById(R.id.edtExpiryDate);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        edtAuditDate.setOnClickListener(v -> showDatePicker(edtAuditDate));
        edtExpiryDate.setOnClickListener(v -> showDatePicker(edtExpiryDate));

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnConfirm.setOnClickListener(v -> {
            Toast.makeText(this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void showDatePicker(EditText editText) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    editText.setText(date);
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showFullInfoDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_full_info_kiem_ke);
        dialog.setCancelable(true);

        setupRow(dialog.findViewById(R.id.rowShelf), "Mã Giá đỡ", "GD-X01");
        setupRow(dialog.findViewById(R.id.rowNvl), "Mã NVL", "NVL-MEIKO-GD-99");
        setupRow(dialog.findViewById(R.id.rowDescription), "Mô tả SP 1", "Linh kiện giá đỡ mạch");
        setupRow(dialog.findViewById(R.id.rowUnit), "Đơn vị", "PCS");
        setupRow(dialog.findViewById(R.id.rowAuditDate), "Ngày kiểm kê", "2024-05-21");
        setupRow(dialog.findViewById(R.id.rowLotNumber), "Số Lô", "SN-SHELF-01");
        setupRow(dialog.findViewById(R.id.rowImportDate), "Ngày nhập", "2024-02-15");
        setupRow(dialog.findViewById(R.id.rowWarehouse), "Kho", "KHO-VAT-TU");
        setupRow(dialog.findViewById(R.id.rowExpiry), "HSD", "2026-01-01");
        setupRow(dialog.findViewById(R.id.rowPoNo), "PoNo", "PO-SHELF-2024");
        setupRow(dialog.findViewById(R.id.rowDept), "BP đặt", "KHO-LOGISTICS");

        Button btnClose = dialog.findViewById(R.id.btnClose);
        if (btnClose != null) btnClose.setOnClickListener(v -> dialog.dismiss());
        
        ImageView btnCloseIcon = dialog.findViewById(R.id.btnCloseIcon);
        if (btnCloseIcon != null) btnCloseIcon.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void setupRow(View rowView, String label, String value) {
        if (rowView != null) {
            TextView tvLabel = rowView.findViewById(R.id.tvLabel);
            TextView tvValue = rowView.findViewById(R.id.tvValue);
            tvLabel.setText(label);
            tvValue.setText(value);
        }
    }
}