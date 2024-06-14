package com.example.do_an;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DPHelperDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NHANVIEN = "NHANVIEN";
    private static final String DATABASE_NAME = "managenhanviens.db";

    public DPHelperDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqltext = "CREATE TABLE NHANVIEN("
                + "CREATE TABLE NHANVIEN("
                + "MaNV TEXT PRIMARY KEY, "
                + "TenNV TEXT, "
                + "ChucVu TEXT, "
                + "SDT TEXT, "
                + "Luongcb TEXT, "
                + "PhongBan TEXT, "
                + "AnhNV TEXT);\n"
                + "INSERT INTO NHANVIEN VALUES('001', 'Nguyễn Văn Hoàng', 'Giám đốc', 0123456789, 50000000,'Phòng tài chính'," + R.drawable.hinh1 + ");\n"
                + "INSERT INTO NHANVIEN VALUES('002', 'Nguyễn Hương ', 'Trưởng phòng', '0987654321', 40000000,'Phòng kinh doanh', " + R.drawable.hinh2 + ");\n"
                + "INSERT INTO NHANVIEN VALUES('003', 'Hồ Minh Anh', 'Quản lý', 0123984756, 30000000,'Phòng kinh doanh', " + R.drawable.hinh3 + ");\n"
                + "INSERT INTO NHANVIEN VALUES('004', 'Trương Thị Thanh Mai', 'Thư ký', 0192837465, 25000000,'Phòng kinh doanh', " + R.drawable.hinh4 + ");"
                + "INSERT INTO NHANVIEN VALUES('005', 'Nguyễn Thành Quang', 'Phó giám đốc', 0093456789, 45000000,'Phòng tài chính'," + R.drawable.hinh1 + ");\n"
                + "INSERT INTO NHANVIEN VALUES('006', 'Đặng Trần Mỹ Duyên', 'Nhân viên chính thức ', '0764654321', 15000000,'Phòng tài chính', " + R.drawable.hinh2 + ");\n"
                + "INSERT INTO NHANVIEN VALUES('007', 'Dương Thuỳ Trang', 'Nhân viên thử việc', 0888984756, 10000000,'Phòng kinh doanh', " + R.drawable.hinh3 + ");\n"
                + "INSERT INTO NHANVIEN VALUES('008', 'Trần Phương Thuỷ', 'Nhân viên chính thức', 0678984756, 15000000,'Phòng kinh doanh', " + R.drawable.hinh3 + ");\n"
                + "INSERT INTO NHANVIEN VALUES('009', 'Vũ Mỹ Quyên', 'Nhân viên chính thức', 0598837465, 15000000,'Kế toán', " + R.drawable.hinh4 + ");";

        for (String sql : sqltext.split("\n")) {
            db.execSQL(sql);
        }

        String sqllogin = "CREATE TABLE login(username TEXT PRIMARY KEY, password TEXT);";
        db.execSQL(sqllogin);
        String sqlinsert = "INSERT INTO LOGIN VALUES('1', '1')";
        db.execSQL(sqlinsert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // xóa bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NHANVIEN);
        db.execSQL("DROP TABLE IF EXISTS login");
        onCreate(db);
    }

    // Đọc dữ liệu
    public Cursor initRecordFistDB() {
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cs = db.rawQuery("SELECT * FROM NHANVIEN", null);
            cs.moveToNext();
            return cs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Kết nối DB để đọc
    public SQLiteDatabase ketNoiDBRead() {
        return getReadableDatabase();
    }

    // Kết nối DB để ghi
    public SQLiteDatabase ketNoiDBWrite() {
        return getWritableDatabase();
    }
}
