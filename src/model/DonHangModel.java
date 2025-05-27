package model;

import java.util.Date;

public class DonHangModel {
    private String maDonHang;
    private String maKhachHang;
    private Date ngayDat;
    private double tongTien;
    private String trangThai;

    public DonHangModel() {
    }

    public DonHangModel(String maDonHang, String maKhachHang, Date ngayDat, double tongTien, String trangThai) {
        this.maDonHang = maDonHang;
        this.maKhachHang = maKhachHang;
        this.ngayDat = ngayDat;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public Date getNgayDat() {
        return ngayDat;
    }

    public double getTongTien() {
        return tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }
}