package dao;

import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.SanPhamModel;

public class SanPhamDAO {
    public void them(SanPhamModel sanPham) throws SQLException {
        String sql = "INSERT INTO SanPham (ma_san_pham, ten, loai, gia, so_luong_ton) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sanPham.getMaSanPham());
            stmt.setString(2, sanPham.getTen());
            stmt.setString(3, sanPham.getLoai());
            stmt.setDouble(4, sanPham.getGia());
            stmt.setInt(5, sanPham.getSoLuongTon());
            stmt.executeUpdate();
        }
    }

    public void sua(SanPhamModel sanPham) throws SQLException {
        String sql = "UPDATE SanPham SET ten = ?, loai = ?, gia = ?, so_luong_ton = ? WHERE ma_san_pham = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sanPham.getTen());
            stmt.setString(2, sanPham.getLoai());
            stmt.setDouble(3, sanPham.getGia());
            stmt.setInt(4, sanPham.getSoLuongTon());
            stmt.setString(5, sanPham.getMaSanPham());
            stmt.executeUpdate();
        }
    }

    public void xoa(String maSanPham) throws SQLException {
        String sql = "DELETE FROM SanPham WHERE ma_san_pham = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSanPham);
            stmt.executeUpdate();
        }
    }

    public List<SanPhamModel> layTatCaSanPham() throws SQLException {
        List<SanPhamModel> sanPhamList = new ArrayList<>();
        String query = "SELECT ma_san_pham, ten, loai, gia, so_luong_ton FROM SanPham";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sanPhamList.add(new SanPhamModel(
                        rs.getString("ma_san_pham"),
                        rs.getString("ten"),
                        rs.getString("loai"),
                        rs.getDouble("gia"),
                        rs.getInt("so_luong_ton")));
            }
        }
        return sanPhamList;
    }

}