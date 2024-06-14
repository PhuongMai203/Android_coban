package com.example.do_an;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Add_NhanVien extends AppCompatActivity {

    EditText edtMaNV, edtTenNV, edtChucVu, edtSDT, edtLuongCB, edtPhongBan; // Khai báo các EditText
    ImageView imgAvt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nhan_vien);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Thêm nhân viên");
        toolbar.setBackgroundColor(Color.parseColor("#FFFFD8E4"));
        setSupportActionBar(toolbar);
        // Ánh xạ các EditText và ImageView
        edtMaNV = findViewById(R.id.txtma);
        edtTenNV = findViewById(R.id.txtname);
        edtChucVu = findViewById(R.id.txtcv);
        edtSDT = findViewById(R.id.txtsdt);
        edtLuongCB = findViewById(R.id.txtlcb);
        edtPhongBan = findViewById(R.id.txtpb);
        imgAvt = findViewById(R.id.imgAvt);

        // Xử lý sự kiện khi nhấn nút Lưu
        Button btnSave = findViewById(R.id.btnluu);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lưu thông tin vào cơ sở dữ liệu và thực hiện các xử lý cần thiết
                saveNewNhanVien();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thongtinct, menu);
        return true;
    }
    private void saveNewNhanVien() {
        // Lấy thông tin từ các EditText
        String maNV = edtMaNV.getText().toString().trim();
        String tenNV = edtTenNV.getText().toString().trim();
        String chucVu = edtChucVu.getText().toString().trim();
        String sdt = edtSDT.getText().toString().trim();
        String luongCB = edtLuongCB.getText().toString().trim();
        String phongBan = edtPhongBan.getText().toString().trim();
        // Ảnh nhân viên lưu vào dạng số nguyên (ID của ảnh)
        int anhNV = R.drawable.ic_launcher_background; // Giả sử mặc định là ảnh mặc định

        // Kiểm tra và mở cơ sở dữ liệu
        try {
            SQLiteDatabase db = openOrCreateDatabase("nhanvien.db", MODE_PRIVATE, null);

            // Tạo bảng NHANVIEN nếu chưa tồn tại
            db.execSQL("CREATE TABLE IF NOT EXISTS NHANVIEN (MaNV TEXT PRIMARY KEY, TenNV TEXT, ChucVu TEXT, SDT TEXT, Luongcb TEXT, PhongBan TEXT, AnhNV INTEGER)");

            // Tạo một đối tượng ContentValues để chứa dữ liệu
            ContentValues contentValues = new ContentValues();
            contentValues.put("MaNV", maNV);
            contentValues.put("TenNV", tenNV);
            contentValues.put("ChucVu", chucVu);
            contentValues.put("SDT", sdt);
            contentValues.put("Luongcb", luongCB);
            contentValues.put("PhongBan", phongBan);
            contentValues.put("AnhNV", anhNV); // Đây là một giá trị ảo, bạn cần xử lý lưu ảnh thật

            // Thực hiện insert dữ liệu vào bảng NHANVIEN
            long result = db.insert("NHANVIEN", null, contentValues);

            // Kiểm tra kết quả insert và thông báo cho người dùng
            if (result == -1) {
                Toast.makeText(this, "Thêm mới nhân viên thất bại", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Thêm mới nhân viên thành công", Toast.LENGTH_SHORT).show();
                finish(); // Đóng màn hình Add_NhanVien và quay trở lại màn hình trước đó
            }

            // Đóng kết nối đến cơ sở dữ liệu
            db.close();
        } catch (SQLException e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("SQL Error", e.getMessage());
        }
    }

}
