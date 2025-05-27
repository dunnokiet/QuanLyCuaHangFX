package model;

public class SanPhamModel {
    private String maSanPham;
    private String ten;
    private String loai;
    private double gia;
    private int soLuongTon;

    public SanPhamModel() {
    }

    public SanPhamModel(String maSanPham, String ten, String loai, double gia, int soLuongTon) {
        this.maSanPham = maSanPham;
        this.ten = ten;
        this.loai = loai;
        this.gia = gia;
        this.soLuongTon = soLuongTon;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public double tinhTienTonKho() {
        return gia * soLuongTon;
    }

    public boolean isThongTinHopLe() {
        return maSanPham != null && !maSanPham.isEmpty() &&
                ten != null && !ten.isEmpty()
                && loai != null && !loai.isEmpty()
                && gia > 0 && soLuongTon >= 0;
    }
}