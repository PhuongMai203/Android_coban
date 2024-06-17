package com.example.do_an;

import android.content.ContentValues;
import android.content.Intent;
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
    DPHelperDatabase dbh;

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

        Button btnSave = findViewById(R.id.btnluu);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        String maNV = edtMaNV.getText().toString().trim();
        String tenNV = edtTenNV.getText().toString().trim();
        String chucVu = edtChucVu.getText().toString().trim();
        String sdt = edtSDT.getText().toString().trim();
        String luongCB = edtLuongCB.getText().toString().trim();
        String phongBan = edtPhongBan.getText().toString().trim();
        // Ảnh nhân viên lưu vào dạng số nguyên (ID của ảnh)
        int anhNV = R.drawable.ic_launcher_background; // Giả sử mặc định là ảnh mặc định

        try {
            SQLiteDatabase db1 = openOrCreateDatabase("managenhanviens.db", MODE_PRIVATE, null);

            String sql = "INSERT INTO NHANVIEN VALUES('"+maNV+"','"+tenNV+"','"+chucVu+"','"+sdt+"','"+luongCB+"','"+phongBan+"','"+anhNV+"')";
            try {
                //SQLiteDatabase db1 = dbh.ketNoiDBWrite();
                db1.execSQL(sql);
                Toast.makeText(this, "Thêm mới nhân viên thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(200, intent);
                finish();
            }catch (Exception e){
                Toast.makeText(this, "Thêm mới nhân viên thất bại" + e, Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("SQL Error", e.getMessage());
        }
    }

}
