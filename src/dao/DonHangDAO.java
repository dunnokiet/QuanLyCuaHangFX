package dao;

import model.DonHangModel;
import util.DatabaseConnection;
import dto.DonHangDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonHangDAO {
    public double getTotalRevenue() throws SQLException {
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

    public int getOrderCount() throws SQLException {
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

    public int getCustomerCount() throws SQLException {
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

    public List<DonHangDTO> getRecentDonHang() throws SQLException {
        List<DonHangDTO> orders = new ArrayList<>();
        String query = "SELECT dh.ma_don_hang, kh.ten, dh.tong_tien, dh.ngay_dat, dh.trang_thai " +
                "FROM DonHang dh " +
                "JOIN KhachHang kh ON dh.ma_khach_hang = kh.ma_khach_hang " +
                "ORDER BY dh.ngay_dat DESC";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String maDonHang = rs.getString("ma_don_hang");
                String ten = rs.getString("ten");
                double tongTien = rs.getDouble("tong_tien");
                Date ngayDat = rs.getDate("ngay_dat");
                String trangThai = rs.getString("trang_thai");
                orders.add(new DonHangDTO(maDonHang, ten, tongTien, ngayDat, trangThai));
            }
        }
        return orders;
    }

    public List<DonHangDTO> getDonHangByFilter(String status, Date startDate, Date endDate)
            throws SQLException {
        List<DonHangDTO> orders = new ArrayList<>();
        StringBuilder query = new StringBuilder(
                "SELECT dh.ma_don_hang, kh.ten, dh.tong_tien, dh.ngay_dat, dh.trang_thai " +
                        "FROM DonHang dh " +
                        "JOIN KhachHang kh ON dh.ma_khach_hang = kh.ma_khach_hang " +
                        "WHERE 1=1");

        List<Object> params = new ArrayList<>();
        if (status != null && !status.isEmpty()) {
            query.append(" AND dh.trang_thai = ?");
            params.add(status);
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
                    String ten = rs.getString("ten");
                    double tongTien = rs.getDouble("tong_tien");
                    Date ngayDat = rs.getDate("ngay_dat");
                    String trangThai = rs.getString("trang_thai");
                    orders.add(new DonHangDTO(maDonHang, ten, tongTien, ngayDat, trangThai));
                }
            }
        }
        return orders;
    }

    public void create(DonHangModel donHang) throws SQLException {
        String query = "INSERT INTO DonHang (ma_don_hang, ma_khach_hang, ngay_dat, tong_tien, trang_thai) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, donHang.getMaDonHang());
            stmt.setString(2, donHang.getMaKhachHang());
            stmt.setDate(3, new java.sql.Date(donHang.getNgayDat().getTime()));
            stmt.setDouble(4, donHang.getTongTien());
            stmt.setString(5, donHang.getTrangThai());
            stmt.executeUpdate();
        }
    }

    public DonHangModel read(String maDonHang) throws SQLException {
        String query = "SELECT * FROM DonHang WHERE ma_don_hang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maDonHang);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String maKhachHang = rs.getString("ma_khach_hang");
                    Date ngayDat = rs.getDate("ngay_dat");
                    double tongTien = rs.getDouble("tong_tien");
                    String trangThai = rs.getString("trang_thai");
                    return new DonHangModel(maDonHang, maKhachHang, ngayDat, tongTien, trangThai);
                }
                return null;
            }
        }
    }

    public void update(DonHangModel donHang) throws SQLException {
        String query = "UPDATE DonHang SET ma_khach_hang = ?, ngay_dat = ?, tong_tien = ?, trang_thai = ? " +
                "WHERE ma_don_hang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, donHang.getMaKhachHang());
            stmt.setDate(2, new java.sql.Date(donHang.getNgayDat().getTime()));
            stmt.setDouble(3, donHang.getTongTien());
            stmt.setString(4, donHang.getTrangThai());
            stmt.setString(5, donHang.getMaDonHang());
            stmt.executeUpdate();
        }
    }

    public void delete(String maDonHang) throws SQLException {
        String query = "DELETE FROM DonHang WHERE ma_don_hang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maDonHang);
            stmt.executeUpdate();
        }
    }
}