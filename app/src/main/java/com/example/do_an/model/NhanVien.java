package com.example.do_an.model;

import java.io.Serializable;

public class NhanVien implements Serializable {
    private String maNV;
    private String tenNV;
    private String Chucvu;
    private String sdt;
    private String Luongcb;
    private String phongBan;
    private int anhNV;

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getChucvu() {
        return Chucvu;
    }

    public void setChucvu(String chucvu) {
        Chucvu = chucvu;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getLuongcb() {
        return Luongcb;
    }

    public void setLuongcb(String luongcb) {
        Luongcb = luongcb;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public int getAnhNV() {
        return anhNV;
    }

    public void setAnhNV(int anhNV) {
        this.anhNV = anhNV;
    }

    public NhanVien(String maNV, String tenNV, String chucvu, String sdt, String luongcb, String phongBan, int anhNV) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        Chucvu = chucvu;
        this.sdt = sdt;
        Luongcb = luongcb;
        this.phongBan = phongBan;
        this.anhNV = anhNV;
    }
}