package com.example.do_an;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an.model.NhanVien;

import java.util.ArrayList;

public class Thongke extends AppCompatActivity {

    private Spinner spinnerTimePeriod;
    private Button btnGenerateReport;
    private RecyclerView recyclerView;
    private TextView tvHighestSalary;

    private CustomAdapter adapter;
    private ArrayList<NhanVien> nhanVienList;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        spinnerTimePeriod = findViewById(R.id.spinner);
        btnGenerateReport = findViewById(R.id.btn_thongke);
        recyclerView = findViewById(R.id.lvthongkenv);
        tvHighestSalary = findViewById(R.id.tvHighestSalary);

        db = openOrCreateDatabase("nhanvien.db", MODE_PRIVATE, null);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        nhanVienList = new ArrayList<>();
        adapter = new CustomAdapter(this, nhanVienList);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.time_periods, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimePeriod.setAdapter(spinnerAdapter);


        btnGenerateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedOption = spinnerTimePeriod.getSelectedItem().toString();
                if (selectedOption.equals(getString(R.string.option_highest_salary))) {
                    showEmployeeWithHighestSalary();
                } else if (selectedOption.equals(getString(R.string.option_by_department))) {
                    showEmployeesInKinhDoanh();
                }
            }
        });

    }

    private void showEmployeeWithHighestSalary() {
        String query = "SELECT * FROM NHANVIEN ORDER BY LuongCB DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            nhanVienList.clear();
            do {
                String maNV = cursor.getString(0);
                String tenNV = cursor.getString(1);
                String chucvu = cursor.getString(2);
                String phongban = cursor.getString(3);
                String sdt = cursor.getString(4);
                String email = cursor.getString(5);
                int luongCB = cursor.getInt(6);

                NhanVien nhanVien = new NhanVien(maNV, tenNV, chucvu, phongban, sdt, email, luongCB);
                nhanVienList.add(nhanVien);
            } while (cursor.moveToNext());
            adapter.notifyDataSetChanged();

            // Hiển thị nhân viên có lương cao nhất
            if (!nhanVienList.isEmpty()) {
                NhanVien highestSalaryEmployee = nhanVienList.get(0); // vì đã sắp xếp DESC và giới hạn 1 record
                tvHighestSalary.setText("Nhân viên có lương cao nhất: " + highestSalaryEmployee.getTenNV()
                        + " - Lương: " + highestSalaryEmployee.getLuongcb());
            } else {
                tvHighestSalary.setText("Không có dữ liệu");
            }
        } else {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    private void showEmployeesInKinhDoanh() {
        String query = "SELECT * FROM NHANVIEN WHERE PhongBan = 'Phòng kinh doanh'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            nhanVienList.clear();
            do {
                String maNV = cursor.getString(0);
                String tenNV = cursor.getString(1);
                String chucvu = cursor.getString(2);
                String phongban = cursor.getString(3);
                String sdt = cursor.getString(4);
                String email = cursor.getString(5);
                int luongCB = cursor.getInt(6);

                NhanVien nhanVien = new NhanVien(maNV, tenNV, chucvu, phongban, sdt, email, luongCB);
                nhanVienList.add(nhanVien);
            } while (cursor.moveToNext());
            adapter.notifyDataSetChanged();

            // Hiển thị kết quả
            if (!nhanVienList.isEmpty()) {
                tvHighestSalary.setText("Thống kê nhân viên phòng kinh doanh");
            } else {
                tvHighestSalary.setText("Không có dữ liệu");
            }
        } else {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
}
