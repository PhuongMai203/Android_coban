package com.example.do_an;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LuongNV extends AppCompatActivity {

    private EditText edtLuongCB, edtSoNgayLam;
    private Button btnTinhLuong;
    private TextView tvKetQua;
    private Spinner spTenNV, spThang;
    private SQLiteDatabase db;
    private ArrayAdapter<String> tenNVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luong_nv);

        // Ánh xạ các thành phần từ layout
        edtLuongCB = findViewById(R.id.edtLuongCB);
        edtSoNgayLam = findViewById(R.id.edtSoNgayLam);
        btnTinhLuong = findViewById(R.id.btnTinhLuong);
        tvKetQua = findViewById(R.id.tvKetQua);
        spTenNV = findViewById(R.id.tennv);
        spThang = findViewById(R.id.thang);

        // Khởi tạo và thiết lập Spinner cho danh sách tên nhân viên
        tenNVAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
        tenNVAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTenNV.setAdapter(tenNVAdapter);

        // Load danh sách tên nhân viên từ CSDL
        loadTenNVFromDatabase();

        // Thiết lập sự kiện khi chọn tên nhân viên từ Spinner
        spTenNV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tenNV = tenNVAdapter.getItem(position);
                if (tenNV != null) {
                    loadLuongCB(tenNV);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Khởi tạo và thiết lập Spinner cho danh sách các tháng
        ArrayList<String> month = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            month.add(String.valueOf(i));
        }
        ArrayAdapter<String> thangAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, month);
        thangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spThang.setAdapter(thangAdapter);

        // Thiết lập sự kiện khi nhấn vào nút Tính lương
        btnTinhLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinhLuong();
            }
        });

        // Bắt sự kiện thay đổi trên EditText để tính toán lại khi dữ liệu thay đổi
        edtLuongCB.addTextChangedListener(watcher);
        edtSoNgayLam.addTextChangedListener(watcher);
        String luongCBStr = edtLuongCB.getText().toString().trim();
// Xóa ký tự "vnd" từ chuỗi luongCBStr
        luongCBStr = luongCBStr.replaceAll("[^\\d.]", "");
    }

    // Phương thức để load danh sách tên nhân viên từ CSDL
    private void loadTenNVFromDatabase() {
        try {
            db = openOrCreateDatabase("nhanvien.db", MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT TenNV FROM NHANVIEN", null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Lấy chỉ số cột "TenNV" từ cursor
                    int indexTenNV = cursor.getColumnIndex("TenNV");
                    if (indexTenNV != -1) {
                        String tenNV = cursor.getString(indexTenNV);
                        tenNVAdapter.add(tenNV);
                    }
                } while (cursor.moveToNext());
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Phương thức để load lương cơ bản dựa trên tên nhân viên được chọn
    private void loadLuongCB(String tenNV) {
        try {
            db = openOrCreateDatabase("nhanvien.db", MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT Luongcb FROM NHANVIEN WHERE TenNV = ?", new String[]{tenNV});
            if (cursor != null && cursor.moveToFirst()) {
                // Lấy chỉ số cột "Luongcb" từ cursor
                int indexLuongCB = cursor.getColumnIndex("Luongcb");
                if (indexLuongCB != -1) {
                    String luongCB = cursor.getString(indexLuongCB);
                    edtLuongCB.setText(luongCB);
                }
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Phương thức để tính toán tổng lương
    private void tinhLuong() {
        // Lấy dữ liệu từ EditText và Spinner
        String luongCBStr = edtLuongCB.getText().toString().trim();
        String soNgayLamStr = edtSoNgayLam.getText().toString().trim();

        // Kiểm tra xem các trường dữ liệu có được nhập đầy đủ không
        if (luongCBStr.isEmpty() || soNgayLamStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Chuyển đổi dữ liệu nhập vào từ String sang kiểu số
        double luongCB = Double.parseDouble(luongCBStr);
        int soNgayLam = Integer.parseInt(soNgayLamStr);

        // Tính tổng lương
        double tongLuong = luongCB * soNgayLam;

        // Hiển thị kết quả tính toán
        tvKetQua.setText("Tổng lương: " + tongLuong + " VNĐ");
    }

    // Watcher để theo dõi sự thay đổi trên EditText
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Khi có sự thay đổi, tự động tính lại lương
            tinhLuong();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Đóng kết nối CSDL khi Activity bị hủy
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
