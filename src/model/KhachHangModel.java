package model;

public class KhachHangModel {
    private String maKhachHang;
    private String ten;
    private String soDienThoai;
    private String diaChi;

    public KhachHangModel() {
    }

    public KhachHangModel(String maKhachHang, String ten, String soDienThoai, String diaChi) {
        this.maKhachHang = maKhachHang;
        this.ten = ten;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean isThongTinHopLe() {
        return ten != null && !ten.isEmpty() && diaChi != null && !diaChi.trim().isEmpty()
                && (soDienThoai == null || soDienThoai.matches("^\\d{9,11}$"));
    }
}
