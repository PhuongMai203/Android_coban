package com.example.do_an;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.do_an.model.NhanVien;

public class ChiTietNV extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtMa, txtTenNV, txtChucVu, txtSdt, txtPhongBan, txtLuongcb;
    private Button btnSua, btnLuu;
    private NhanVien nhanVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiet_nv);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Thông tin chi tiết");
        toolbar.setBackgroundColor(Color.parseColor("#FFFFD8E4"));
        setSupportActionBar(toolbar);

        nhanVien = (NhanVien) getIntent().getSerializableExtra("NhanVien");

        txtMa = findViewById(R.id.txtma);
        txtTenNV = findViewById(R.id.txtname);
        txtChucVu = findViewById(R.id.txtcv);
        txtSdt = findViewById(R.id.txtsdt);
        txtPhongBan = findViewById(R.id.txtpb);
        txtLuongcb = findViewById(R.id.txtlcb);
        btnSua = findViewById(R.id.btn_sua);
        btnLuu = findViewById(R.id.btnluu);
        ImageView ImgAvt = findViewById(R.id.imgAvt);

        if (nhanVien != null) {
            txtMa.setText(nhanVien.getMaNV());
            txtTenNV.setText(nhanVien.getTenNV());
            txtChucVu.setText(nhanVien.getChucvu());
            txtSdt.setText(nhanVien.getSdt());
            txtPhongBan.setText(nhanVien.getPhongBan());
            txtLuongcb.setText(String.valueOf(nhanVien.getLuongcb()));
            ImgAvt.setImageResource(nhanVien.getAnhNV());
        }
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditing(true);
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thongtinct, menu);
        MenuItem cameraItem = menu.findItem(R.id.camera);
        cameraItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }

    private void enableEditing(boolean enable) {
        txtMa.setEnabled(enable);
        txtTenNV.setEnabled(enable);
        txtChucVu.setEnabled(enable);
        txtSdt.setEnabled(enable);
        txtPhongBan.setEnabled(enable);
        txtLuongcb.setEnabled(enable);
        btnLuu.setVisibility(enable ? View.VISIBLE : View.GONE); // Ẩn/hiện nút Lưu tùy thuộc vào chế độ chỉnh sửa
    }

    private void saveChanges() {

        nhanVien.setMaNV(txtMa.getText().toString());
        nhanVien.setTenNV(txtTenNV.getText().toString());
        nhanVien.setChucvu(txtChucVu.getText().toString());
        nhanVien.setSdt(txtSdt.getText().toString());
        nhanVien.setLuongcb(txtLuongcb.getText().toString());
        nhanVien.setPhongBan(txtPhongBan.getText().toString());

        Intent intent = new Intent();
        intent.putExtra("EditedNhanVien", nhanVien);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}
