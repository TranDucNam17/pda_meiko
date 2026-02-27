package com.example.pda.ui.kiemke.barcode;

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

public class KiemKeBarcodeDetailActivity extends AppCompatActivity {

    private ImageView btnBack;
    private ViewGroup containerData;
    private CheckBox checkAll;
    private TextView tvSelectedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiem_ke_barcode_detail);

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
                
                // Sự kiện click vào thẻ để xem chi tiết (tránh click vào CheckBox và Edit)
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

        TextView tvWarehouseCode = dialog.findViewById(R.id.tvWarehouseCode);
        EditText edtLotNumber = dialog.findViewById(R.id.edtLotNumber);
        EditText edtShelfCode = dialog.findViewById(R.id.edtShelfCode);
        EditText edtAuditDate = dialog.findViewById(R.id.edtAuditDate);
        EditText edtExpiryDate = dialog.findViewById(R.id.edtExpiryDate);
        EditText edtNote = dialog.findViewById(R.id.edtNote);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        // Giả lập dữ liệu cũ
        if (tvWarehouseCode != null) tvWarehouseCode.setText("Mã kho: KHO-THANH-PHAM");
        edtLotNumber.setText("LOT-2024-001");
        edtShelfCode.setText("GD-ZONE-A1");
        edtAuditDate.setText("2024-05-20");
        edtExpiryDate.setText("2026-01-10");

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
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String date = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    editText.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showFullInfoDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_full_info_kiem_ke);
        dialog.setCancelable(true);

        setupRow(dialog.findViewById(R.id.rowShelf), "Mã Giá đỡ", "GD-ZONE-A1");
        setupRow(dialog.findViewById(R.id.rowNvl), "Mã NVL", "NVL-MEIKO-PCB-01");
        setupRow(dialog.findViewById(R.id.rowDescription), "Mô tả SP 1", "Bản mạch in linh kiện điện tử");
        setupRow(dialog.findViewById(R.id.rowUnit), "Đơn vị", "Cái (PCS)");
        setupRow(dialog.findViewById(R.id.rowAuditDate), "Ngày kiểm kê", "2024-05-20");
        setupRow(dialog.findViewById(R.id.rowLotNumber), "Số Lô", "SN-888999");
        setupRow(dialog.findViewById(R.id.rowImportDate), "Ngày nhập", "2024-01-10");
        setupRow(dialog.findViewById(R.id.rowWarehouse), "Kho", "KHO-THANH-PHAM");
        setupRow(dialog.findViewById(R.id.rowExpiry), "HSD", "2026-01-10");
        setupRow(dialog.findViewById(R.id.rowPoNo), "PoNo", "PO-MEIKO-2024-01");
        setupRow(dialog.findViewById(R.id.rowDept), "BP đặt", "PHONG-SAN-XUAT-1");

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