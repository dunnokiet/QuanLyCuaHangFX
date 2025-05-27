package dao;

import dto.DonHangDTO;
import model.ChiTietDonHangModel;
import model.SanPhamModel;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonHangDAO {
    private ChiTietDonHangDAO chiTietDonHangDAO;
    private SanPhamDAO sanPhamDAO;

    public DonHangDAO() {
        this.chiTietDonHangDAO = new ChiTietDonHangDAO();
        this.sanPhamDAO = new SanPhamDAO();
    }

    public List<String> layDanhSachMaDonHang() throws SQLException {
        List<String> maDonHangList = new ArrayList<>();
        String query = "SELECT ma_don_hang FROM DonHang";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                maDonHangList.add(rs.getString("ma_don_hang"));
            }
        }
        return maDonHangList;
    }

    public double layTongDoanhThu() throws SQLException {
        String query = "SELECT SUM(tong_tien) FROM DonHang";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0.0;
        }
    }

    public int laySoLuongDonHang() throws SQLException {
        String query = "SELECT COUNT(*) FROM DonHang";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public int laySoLuongKhachHang() throws SQLException {
        String query = "SELECT COUNT(DISTINCT ma_khach_hang) FROM DonHang";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public List<DonHangDTO> layTatCaDonGanNhat() throws SQLException {
        List<DonHangDTO> orders = new ArrayList<>();
        String query = "SELECT dh.ma_don_hang, dh.ma_khach_hang, kh.ten AS ten, dh.tong_tien, dh.ngay_dat, dh.trang_thai "
                +
                "FROM DonHang dh " +
                "JOIN KhachHang kh ON dh.ma_khach_hang = kh.ma_khach_hang " +
                "ORDER BY dh.ngay_dat DESC";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String maDonHang = rs.getString("ma_don_hang");
                String maKhachHang = rs.getString("ma_khach_hang");
                String tenKhachHang = rs.getString("ten");
                double tongTien = rs.getDouble("tong_tien");
                Date ngayDat = rs.getDate("ngay_dat");
                String trangThai = rs.getString("trang_thai");
                orders.add(new DonHangDTO(maDonHang, maKhachHang, tenKhachHang, tongTien, ngayDat, trangThai));
            }
        }
        return orders;
    }

    public List<DonHangDTO> layDonTheoLoc(String status, Date startDate, Date endDate) throws SQLException {
        List<DonHangDTO> orders = new ArrayList<>();
        StringBuilder query = new StringBuilder(
                "SELECT dh.ma_don_hang, dh.ma_khach_hang, kh.ten AS ten_khach_hang, dh.tong_tien, dh.ngay_dat, dh.trang_thai "
                        +
                        "FROM DonHang dh " +
                        "JOIN KhachHang kh ON dh.ma_khach_hang = kh.ma_khach_hang " +
                        "WHERE 1=1");

        List<Object> params = new ArrayList<>();
        if (status != null && !status.trim().isEmpty()) {
            query.append(" AND dh.trang_thai = ?");
            params.add(status.trim());
        }
        if (startDate != null) {
            query.append(" AND dh.ngay_dat >= ?");
            params.add(new java.sql.Date(startDate.getTime()));
        }
        if (endDate != null) {
            query.append(" AND dh.ngay_dat <= ?");
            params.add(new java.sql.Date(endDate.getTime()));
        }
        query.append(" ORDER BY dh.ngay_dat DESC");

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String maDonHang = rs.getString("ma_don_hang");
                    String maKhachHang = rs.getString("ma_khach_hang");
                    String tenKhachHang = rs.getString("ten_khach_hang");
                    double tongTien = rs.getDouble("tong_tien");
                    Date ngayDat = rs.getDate("ngay_dat");
                    String trangThai = rs.getString("trang_thai");
                    orders.add(new DonHangDTO(maDonHang, maKhachHang, tenKhachHang, tongTien, ngayDat, trangThai));
                }
            }
        }
        return orders;
    }

    public void them(DonHangDTO donHang) throws SQLException {
        String sql = "INSERT INTO DonHang (ma_don_hang, ma_khach_hang, ngay_dat, trang_thai, tong_tien) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, donHang.getMaDonHang());
            stmt.setString(2, donHang.getMaKhachHang());
            stmt.setDate(3, donHang.getNgayDat() != null ? new java.sql.Date(donHang.getNgayDat().getTime()) : null);
            stmt.setString(4, donHang.getTrangThai());
            stmt.setDouble(5, donHang.getTongTien());
            stmt.executeUpdate();
        }
    }

    public void sua(DonHangDTO donHang) throws SQLException {
        String sql = "UPDATE DonHang SET ma_khach_hang = ?, ngay_dat = ?, trang_thai = ?, tong_tien = ? WHERE ma_don_hang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, donHang.getMaKhachHang());
            stmt.setDate(2, donHang.getNgayDat() != null ? new java.sql.Date(donHang.getNgayDat().getTime()) : null);
            stmt.setString(3, donHang.getTrangThai());
            stmt.setDouble(4, donHang.getTongTien());
            stmt.setString(5, donHang.getMaDonHang());
            stmt.executeUpdate();
        }
    }

    public void xoa(String maDonHang) throws SQLException {
        List<ChiTietDonHangModel> chiTietList = chiTietDonHangDAO.layChiTietDonHang(maDonHang);
        for (ChiTietDonHangModel chiTiet : chiTietList) {
            for (SanPhamModel sp : sanPhamDAO.layTatCaSanPham()) {
                if (sp.getMaSanPham().equals(chiTiet.getMaSanPham())) {
                    sp.setSoLuongTon(sp.getSoLuongTon() + chiTiet.getSoLuong());
                    sanPhamDAO.sua(sp);
                    break;
                }
            }
        }

        chiTietDonHangDAO.xoaTheoMaDonHang(maDonHang);
        String sql = "DELETE FROM DonHang WHERE ma_don_hang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maDonHang);
            stmt.executeUpdate();
        }
    }
}