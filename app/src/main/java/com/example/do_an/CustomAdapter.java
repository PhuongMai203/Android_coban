package com.example.do_an;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an.model.NhanVien;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<NhanVien> nhanvienList;

    public CustomAdapter(Context context, ArrayList<NhanVien> nhanvienList) {
        this.context = context;
        this.nhanvienList = nhanvienList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_custom_adapter, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NhanVien nhanvien = nhanvienList.get(position);

        holder.manv.setText(nhanvien.getMaNV());
        holder.tennv.setText(nhanvien.getTenNV());
        holder.chucvu.setText(nhanvien.getChucvu());
        // Đặt hình ảnh từ resource ID trực tiếp
        holder.imganhnv.setImageResource(nhanvien.getAnhNV());

        holder.btn_chitiet.setOnClickListener(v -> openDetailsActivity(nhanvien));
        holder.btn_xoa.setOnClickListener(v -> showDialogConfirm(nhanvien, position));
    }


    @Override
    public int getItemCount() {
        return nhanvienList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView manv, tennv, chucvu;
        public ImageView imganhnv;
        public Button btn_chitiet, btn_xoa;

        public ViewHolder(View view) {
            super(view);
            manv = view.findViewById(R.id.txt_ma);
            tennv = view.findViewById(R.id.txt_ten);
            chucvu = view.findViewById(R.id.txt_cv);
            imganhnv = view.findViewById(R.id.imganhnv);
            btn_chitiet = view.findViewById(R.id.btn_ct);
            btn_xoa = view.findViewById(R.id.btndelete);
        }
    }

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
            SQLiteDatabase db = context.openOrCreateDatabase("nhanvien.db", Context.MODE_PRIVATE, null);
            db.execSQL(sql);
            db.close();
            nhanvienList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, nhanvienList.size());
            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT).show();
        }
    }
}
