package com.example.do_an;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.do_an.model.NhanVien;

import java.util.ArrayList;

public class QLNhanVien extends AppCompatActivity {
    ArrayList<NhanVien> nhanvien;
    CustomAdapter adapter;
    SQLiteDatabase db;
    DPHelperDatabase dbh;
    Toolbar toolbar;
    ListView lvnv;
    EditText txts;
    Cursor cs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlnhan_vien);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Danh sách nhân viên");
        toolbar.setBackgroundColor(Color.parseColor("#FFFFD8E4"));
        setSupportActionBar(toolbar);


        initView();
        dbh = new DPHelperDatabase(this);
        cs = dbh.initRecordFistDB();
        showDATA();
    }

    void initView(){
        lvnv = findViewById(R.id.lvnv);
        txts = (EditText) findViewById(R.id.txtsearch);

        txts.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                showDATA();
                return false;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 200){
            showDATA();
        }
    }

    private void showDATA(){
        db = dbh.ketNoiDBRead();
        String s = txts.getText().toString();
        nhanvien = new ArrayList<>();

        String query = "SELECT * FROM NHANVIEN where MaNV like '%" + s + "%' or TenNV like '%" + s + "%'";
        Log.d("SQL_QUERY", s);
        Cursor cursor = db.rawQuery("SELECT * FROM NHANVIEN where manv like '%"+s+"%' or tennv like '%"+s+"%'", null);
        try {
            while (cursor.moveToNext()) {
                NhanVien b=new NhanVien(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5), Integer.parseInt(cursor.getString(6)));
                nhanvien.add(b);
                adapter = new CustomAdapter(this, nhanvien);
                lvnv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }


        } finally {
            cursor.close();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent in = new Intent(getApplicationContext(), Add_NhanVien.class);
            startActivityForResult(in, 200);
            Toast.makeText(this, "Thêm mới nhân viên", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void loadDataFromDatabase() {
        SQLiteDatabase db = openOrCreateDatabase("nhanvien.db", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM NHANVIEN", null);

        if (cursor.moveToFirst()) {
            do {
                String maNV = cursor.getString(0);
                String tenNV = cursor.getString(1);
                String chucvu = cursor.getString(2);
                String sdt = cursor.getString(3);
                String luongcb = cursor.getString(4);
                String phongBan = cursor.getString(5);
                int anhNV = cursor.getInt(6);

                nhanvien.add(new NhanVien(maNV, tenNV, chucvu, sdt, luongcb, phongBan, anhNV));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

}
