package dao;

import util.DatabaseConnection;
import dto.ChiTietDonHangDTO;
import model.ChiTietDonHangModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHangDAO {

    public List<ChiTietDonHangModel> layChiTietDonHang(String maDonHang) throws SQLException {
        List<ChiTietDonHangModel> chiTietList = new ArrayList<>();
        String query = "SELECT ma_don_hang, ma_san_pham, so_luong, don_gia FROM ChiTietDonHang WHERE ma_don_hang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maDonHang);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    chiTietList.add(new ChiTietDonHangModel(
                            rs.getString("ma_don_hang"),
                            rs.getString("ma_san_pham"),
                            rs.getInt("so_luong"),
                            rs.getDouble("don_gia")));
                }
            }
        }
        return chiTietList;
    }

    public List<ChiTietDonHangDTO> layChiTietDonHangDTO(String maDonHang) throws SQLException {
        List<ChiTietDonHangDTO> chiTietList = new ArrayList<>();
        String query = "SELECT sp.ten, ct.so_luong, ct.don_gia " +
                "FROM ChiTietDonHang ct " +
                "JOIN SanPham sp ON ct.ma_san_pham = sp.ma_san_pham " +
                "WHERE ct.ma_don_hang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maDonHang);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    chiTietList.add(new ChiTietDonHangDTO(
                            rs.getString("ten"),
                            rs.getInt("so_luong"),
                            rs.getDouble("don_gia")));
                }
            }
        }
        return chiTietList;
    }

    public void them(String maDonHang, String maSanPham, int soLuong, double donGia) throws SQLException {
        String sql = "INSERT INTO ChiTietDonHang (ma_don_hang, ma_san_pham, so_luong, don_gia) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maDonHang);
            stmt.setString(2, maSanPham);
            stmt.setInt(3, soLuong);
            stmt.setDouble(4, donGia);
            stmt.executeUpdate();
        }
    }

    public void xoaTheoMaDonHang(String maDonHang) throws SQLException {
        String sql = "DELETE FROM ChiTietDonHang WHERE ma_don_hang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maDonHang);
            stmt.executeUpdate();
        }
    }
}