package model;

public class NguoiDungModel {
    private String maNguoiDung;
    private String tenDangNhap;
    private String matKhau;
    private String vaiTro;

    public NguoiDungModel() {
    }

    public NguoiDungModel(String maNguoiDung, String tenDangNhap, String matKhau, String vaiTro) {
        this.maNguoiDung = maNguoiDung;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public boolean isQuanLy() {
        return "Quản lý".equalsIgnoreCase(vaiTro);
    }

    public boolean isNhanVien() {
        return "nhân viên".equalsIgnoreCase(vaiTro);
    }

    public boolean isThongTinHopLe() {
        return tenDangNhap != null && !tenDangNhap.isEmpty()
                && matKhau != null && matKhau.length() >= 6;
    }

    public boolean kiemTraDangNhap(String tenDangNhapInput, String matKhauInput) {
        return this.tenDangNhap.equals(tenDangNhapInput)
                && this.matKhau.equals(matKhauInput);
    }
}