package com.example.pda.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pda.MainActivity;
import com.example.pda.R;
import com.example.pda.utils.ColorConsole;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Khởi tạo Logger
        ColorConsole.init(this);
        ColorConsole.Start();
        ColorConsole.Info("Khởi chạy ứng dụng - Mở màn hình Login");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initEvents();
    }

    private void initViews() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        ColorConsole.Info("Ánh xạ View hoàn tất");
    }

    private void initEvents() {
        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            ColorConsole.Info("Người dùng nhấn nút Đăng nhập");

            if (username.isEmpty() || password.isEmpty()) {
                // Log trạng thái thiếu thông tin
                ColorConsole.Warn("Vui lòng nhập đầy đủ thông tin");
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                // Log trạng thái thành công kèm theo tên tài khoản
                ColorConsole.Success("Đăng nhập thành công với tài khoản: " + username);
                
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Ví dụ: Log khi người dùng nhấn giữ nút Login để chia sẻ file log (bí mật để debug)
        btnLogin.setOnLongClickListener(v -> {
            ColorConsole.Info("Kích hoạt tính năng chia sẻ File Log");
            ColorConsole.shareLogFile(this);
            return true;
        });
    }
}
