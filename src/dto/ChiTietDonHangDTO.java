package dto;

public class ChiTietDonHangDTO {
    private String tenSanPham;
    private int soLuong;
    private double donGia;

    public ChiTietDonHangDTO() {
    }

    public ChiTietDonHangDTO(String tenSanPham, int soLuong, double donGia) {
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getDonGia() {
        return donGia;
    }
}