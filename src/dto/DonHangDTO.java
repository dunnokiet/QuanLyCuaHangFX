package dto;

import java.util.Date;

public class DonHangDTO {
    private String maDonHang;
    private String tenKhachHang;
    private Date ngayDat;
    private double tongTien;
    private String trangThai;

    public DonHangDTO(String maDonHang, String tenKhachHang, double tongTien, Date ngayDat, String trangThai) {
        this.maDonHang = maDonHang;
        this.tenKhachHang = tenKhachHang;
        this.tongTien = tongTien;
        this.ngayDat = ngayDat;
        this.trangThai = trangThai;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
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