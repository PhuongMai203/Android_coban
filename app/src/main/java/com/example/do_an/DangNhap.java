package com.example.do_an;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DangNhap extends AppCompatActivity {
    private static final String DEFAULT_EMAIL = "1";
    private static final String DEFAULT_PASSWORD = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);

        final EditText emailEditText = findViewById(R.id.dn_email);
        final EditText passwordEditText = findViewById(R.id.dn_pass);

        Button btnDangNhap = findViewById(R.id.btn_dn);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.equals(DEFAULT_EMAIL) && password.equals(DEFAULT_PASSWORD)) {
                    // Đăng nhập thành công
                    Intent intent = new Intent(DangNhap.this, MainActivity.class);
                    Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(DangNhap.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
