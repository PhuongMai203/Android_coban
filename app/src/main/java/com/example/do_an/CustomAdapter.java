package com.example.do_an;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an.model.NhanVien;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<NhanVien> {

    private final Activity context;
    private final ArrayList<NhanVien> nhanvienList;

    public TextView manv, tennv, chucvu;
        public ImageView imganhnv;
        public Button btn_chitiet, btn_xoa;

    public CustomAdapter(Activity context, ArrayList<NhanVien> nhanvienList2) {
        super(context, R.layout.activity_custom_adapter, nhanvienList2);
        this.context = context;
        this.nhanvienList = nhanvienList2;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_custom_adapter,null, true);
        NhanVien nhanvien = nhanvienList.get(position);

        manv = rowView.findViewById(R.id.txt_ma);
        tennv = rowView.findViewById(R.id.txt_ten);
        chucvu = rowView.findViewById(R.id.txt_cv);
        imganhnv = rowView.findViewById(R.id.imganhnv);
        btn_chitiet = rowView.findViewById(R.id.btn_ct);
        btn_xoa = rowView.findViewById(R.id.btndelete);

        manv.setText(nhanvien.getMaNV());
        tennv.setText(nhanvien.getTenNV());
        chucvu.setText(nhanvien.getChucvu());
        imganhnv.setImageResource(nhanvien.getAnhNV());
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogConfirm(nhanvien, position);
            }
        });

        btn_chitiet.setOnClickListener(v -> openDetailsActivity(nhanvien));
        return rowView;
    };

    private void openDetailsActivity(NhanVien nhanvien) {
        Intent intent = new Intent(context, ChiTietNV.class);
        intent.putExtra("NhanVien", nhanvien);
        context.startActivity(intent);
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showDialogConfirm(NhanVien nhanvien, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có đồng ý xóa mục " + nhanvien.getTenNV() + " này không?");
        builder.setPositiveButton("Đồng ý", (dialog, which) -> deleteNhanVien(nhanvien.getMaNV(), position));
        builder.setNegativeButton("Không đồng ý", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }

    private void deleteNhanVien(String maNV, int position) {
        try {
            String sql = "DELETE FROM NHANVIEN WHERE MaNV = '" + maNV + "'";
            SQLiteDatabase db = context.openOrCreateDatabase("managenhanviens.db", Context.MODE_PRIVATE, null);
            db.execSQL(sql);
            db.close();
            nhanvienList.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT).show();
        }
    }
}
