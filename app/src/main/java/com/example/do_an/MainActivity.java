package com.example.do_an;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button btn_nhanvien = findViewById(R.id.btn_nhanvien);
        Button btn_luongnv=findViewById(R.id.btn_luongnv);
        Button btn_baocao=findViewById(R.id.btn_baocao);

        btn_nhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang activity quản lý nhân viên (ActivityQuanLyNhanVien)
                Intent intent = new Intent(MainActivity.this, QLNhanVien.class);
                startActivity(intent);
            }
        });
        btn_luongnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang activity quản lý nhân viên (ActivityQuanLyNhanVien)
                Intent intent = new Intent(MainActivity.this, LuongNV.class);
                startActivity(intent);
            }
        });
        btn_baocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng sang activity quản lý nhân viên (ActivityQuanLyNhanVien)
                Intent intent = new Intent(MainActivity.this, Thongke.class);
                startActivity(intent);
            }
        });
    }
}