package com.hieu.ThuVien.model;

public class MuonSach {
    String ghiChu;
    String hanTra;
    String ngayMuon;
    String ngayTra;
    String nguoiMuon;
    String soDienThoai;
    String id;

    public MuonSach() {
    }

    public MuonSach(String ghiChu, String hanTra, String ngayMuon, String ngayTra, String nguoiMuon, String soDienThoai) {
        this.ghiChu = ghiChu;
        this.hanTra = hanTra;
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
        this.nguoiMuon = nguoiMuon;
        this.soDienThoai = soDienThoai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getHanTra() {
        return hanTra;
    }

    public void setHanTra(String hanTra) {
        this.hanTra = hanTra;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getNguoiMuon() {
        return nguoiMuon;
    }

    public void setNguoiMuon(String nguoiMuon) {
        this.nguoiMuon = nguoiMuon;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
}
