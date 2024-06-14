package com.example.do_an;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an.model.NhanVien;

import java.util.ArrayList;

public class QLNhanVien extends AppCompatActivity {
    ArrayList<NhanVien> nhanvien;
    CustomAdapter adapter;
    SQLiteDatabase db;
    DPHelperDatabase dbh;
    Toolbar toolbar;
    RecyclerView lvnv;
    EditText txts;
    Cursor cs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlnhan_vien);

        // Thiết lập Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Danh sách nhân viên");
        toolbar.setBackgroundColor(Color.parseColor("#FFFFD8E4"));
        setSupportActionBar(toolbar);

        lvnv = findViewById(R.id.lvthongkenv);
        txts = findViewById(R.id.txts);

        // Khởi tạo DPHelperDatabase
        dbh = new DPHelperDatabase(this);
        initDB();

        // Khởi tạo danh sách nhanvien
        nhanvien = new ArrayList<>();

        // Tạo cơ sở dữ liệu và thêm dữ liệu mẫu
        createNhanVienDatabase();

        // Lấy dữ liệu từ cơ sở dữ liệu và thêm vào danh sách nhanvien
        loadDataFromDatabase();
//        showDataListView();

        // Khởi tạo adapter và thiết lập cho RecyclerView
        adapter = new CustomAdapter(this, nhanvien);
        lvnv.setLayoutManager(new LinearLayoutManager(this));
        lvnv.setAdapter(adapter);

        txts.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                showDataListView();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            // Chuyển đến màn hình Add_NhanVien khi người dùng nhấn vào nút Add trên Toolbar
            startActivity(new Intent(QLNhanVien.this, Add_NhanVien.class));
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

    public void createNhanVienDatabase() {
        String sqltext = "DROP TABLE IF EXISTS NHANVIEN;\n"
                + "CREATE TABLE NHANVIEN("
                + "MaNV TEXT PRIMARY KEY, "
                + "TenNV TEXT, "
                + "ChucVu TEXT, "
                + "SDT TEXT, "
                + "Luongcb TEXT, "
                + "PhongBan TEXT, "
                + "AnhNV INTEGER);\n"
                + "INSERT INTO NHANVIEN VALUES('001', 'Nguyễn Văn Hoàng', 'Giám đốc', 0123456789, 50000000,'Phòng tài chính'," + R.drawable.hinh1 + ");\n"
                + "INSERT INTO NHANVIEN VALUES('002', 'Nguyễn Hương ', 'Trưởng phòng', '0987654321', 40000000,'Phòng kinh doanh', " + R.drawable.hinh2 + ");\n"
                + "INSERT INTO NHANVIEN VALUES('003', 'Hồ Minh Anh', 'Quản lý', 0123984756, 30000000,'Phòng kinh doanh', " + R.drawable.hinh3 + ");\n"
                + "INSERT INTO NHANVIEN VALUES('004', 'Trương Thị Thanh Mai', 'Thư ký', 0192837465, 25000000,'Phòng kinh doanh', " + R.drawable.hinh4 + ");"
                + "INSERT INTO NHANVIEN VALUES('005', 'Nguyễn Thành Quang', 'Phó giám đốc', 0093456789, 45000000,'Phòng tài chính'," + R.drawable.hinh1 + ");\n"
                + "INSERT INTO NHANVIEN VALUES('006', 'Đặng Trần Mỹ Duyên', 'Nhân viên chính thức ', '0764654321', 15000000,'Phòng kế hoach', " + R.drawable.hinh2 + ");\n"
                + "INSERT INTO NHANVIEN VALUES('007', 'Dương Thuỳ Trang', 'Nhân viên thử việc', 0888984756, 10000000,'Phòng kế hoach', " + R.drawable.hinh3 + ");\n"
                + "INSERT INTO NHANVIEN VALUES('008', 'Trần Phương Thuỷ', 'Nhân viên chính thức', 0678984756, 15000000,'Phòng kế hoach', " + R.drawable.hinh3 + ");\n"
                + "INSERT INTO NHANVIEN VALUES('009', 'Vũ Mỹ Quyên', 'Nhân viên chính thức', 0598837465, 15000000,'Kế toán', " + R.drawable.hinh4 + ");";

        // Tạo DB và thực hiện một số câu SQL
        SQLiteDatabase db = openOrCreateDatabase("nhanvien.db", MODE_PRIVATE, null);
        for (String sql : sqltext.split("\n")) {
            db.execSQL(sql);
        }
        db.close();
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

    void updateRecord() {
        // Dummy method to maintain structure. Implement if required.
    }

    void initDB() {
        try {
            db = openOrCreateDatabase("nhanvien.db", MODE_PRIVATE, null);
            cs = db.rawQuery("SELECT * FROM NHANVIEN", null);
        } catch (Exception e) {
            finish();
        }
        if (cs != null && cs.moveToFirst()) {
            updateRecord();
        }
    }

//    private void showDataListView() {
//        nhanvien.clear();
//        SQLiteDatabase db = dbh.ketNoiDBRead();
//        String s = txts.getText().toString();
//        nhanvien = new ArrayList<>();
//        Cursor cursor = db.rawQuery("SELECT * FROM NHANVIEN WHERE TenNV LIKE '%" + s + "%'", null);
//        if (cursor.moveToFirst()) {
//            do {
//                String maNV = cursor.getString(0);
//                String tenNV = cursor.getString(1);
//                String chucvu = cursor.getString(2);
//                String sdt = cursor.getString(3);
//                String luongcb = cursor.getString(4);
//                String phongBan = cursor.getString(5);
//                int anhNV = cursor.getInt(6);
//
//                nhanvien.add(new NhanVien(maNV, tenNV, chucvu, sdt, luongcb, phongBan, anhNV));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//
//        adapter.notifyDataSetChanged();
//    }
}
