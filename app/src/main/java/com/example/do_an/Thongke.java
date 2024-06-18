package com.example.do_an;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.do_an.model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class Thongke extends AppCompatActivity {

    private Spinner spinner;
    private Button btnThongke;
    private TextView txtkq;
    private ListView lvnv;
    private DPHelperDatabase dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        spinner = findViewById(R.id.spinner);
        btnThongke = findViewById(R.id.btn_thongke);
        txtkq = findViewById(R.id.txtkq);
        lvnv = findViewById(R.id.lvnv);

        dbh = new DPHelperDatabase(this);

        // Thiết lập Spinner với các tùy chọn
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnThongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = spinner.getSelectedItemPosition();
                if (selectedPosition == 0) {
                    // Thống kê tổng lương
                    thongKeTongLuong();
                } else if (selectedPosition == 1) {
                    // Thống kê phòng kinh doanh
                    thongKePhongKinhDoanh();
                }
            }
        });
    }

    private void thongKeTongLuong() {
        int tongLuong = 0;
        SQLiteDatabase db = dbh.ketNoiDBRead();
        Cursor cursor = db.rawQuery("SELECT SUM(Luongcb) FROM NHANVIEN", null);

        if (cursor.moveToFirst()) {
            tongLuong = cursor.getInt(0);
        }
        cursor.close();

        txtkq.setText("Tổng số tiền trả lương: " + tongLuong + " VND");
        lvnv.setAdapter(null); // Clear ListView
    }

    private void thongKePhongKinhDoanh() {
        List<NhanVien> nhanVienList = new ArrayList<>();
        SQLiteDatabase db = dbh.ketNoiDBRead();
        Cursor cursor = db.rawQuery("SELECT MaNV, TenNV, ChucVu, SDT, Luongcb, PhongBan, AnhNV FROM NHANVIEN WHERE PhongBan = 'Phòng kinh doanh'", null);

        while (cursor.moveToNext()) {
            String maNV = cursor.getString(0);
            String tenNV = cursor.getString(1);
            String chucVu = cursor.getString(2);
            String sdt = cursor.getString(3);
            String luongcb = cursor.getString(4); // Đọc Luongcb dưới dạng String
            String phongBan = cursor.getString(5);
            int anhNV = cursor.getInt(6);

            NhanVien nhanVien = new NhanVien(maNV, tenNV, chucVu, sdt, luongcb, phongBan, anhNV);
            nhanVienList.add(nhanVien);
        }
        cursor.close();

        List<String> tenNVList = new ArrayList<>();
        for (NhanVien nv : nhanVienList) {
            tenNVList.add(nv.getTenNV());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, tenNVList);
        lvnv.setAdapter(adapter);
        txtkq.setText("");

        lvnv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NhanVien selectedNhanVien = nhanVienList.get(position);
                Intent intent = new Intent(Thongke.this, ChiTietNV.class);
                intent.putExtra("NhanVien", selectedNhanVien);
                startActivity(intent);
            }
        });
    }
}
