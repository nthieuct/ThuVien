package com.hieu.ThuVien.model;

public class Sach {
    String maSach;
    Integer soLuongBanSao;
    String tacGia;
    String tenSach;
    String id;

    public Sach() {
    }

    public Sach(String maSach, Integer soLuongBanSao, String tacGia, String tenSach) {
        this.maSach = maSach;
        this.soLuongBanSao = soLuongBanSao;
        this.tacGia = tacGia;
        this.tenSach = tenSach;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public Integer getSoLuongBanSao() {
        return soLuongBanSao;
    }

    public void setSoLuongBanSao(Integer soLuongBanSao) {
        this.soLuongBanSao = soLuongBanSao;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
}
