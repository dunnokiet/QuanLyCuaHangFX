package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.KhachHangModel;
import util.DatabaseConnection;

public class KhachHangDAO {
    public ObservableList<KhachHangModel> layTatCaKhachHang() throws SQLException {
        ObservableList<KhachHangModel> tatCaKhachHang = FXCollections.observableArrayList();
        String sql = "SELECT * FROM KhachHang";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tatCaKhachHang.add(new KhachHangModel(
                        rs.getString("ma_khach_hang"),
                        rs.getString("ten"),
                        rs.getString("so_dien_thoai"),
                        rs.getString("dien_thoai"),
                        rs.getString("dia_chi")));
            }
        }
        return tatCaKhachHang;
    }

    public void themKhachHang(KhachHangModel khachHang) throws SQLException {
        String sql = "INSERT INTO KhachHang (ma_khach_hang, ten, so_dien_thoai, dien_thoai, dia_chi) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, khachHang.getMaKhachHang());
            pstmt.setString(2, khachHang.getTen());
            pstmt.setString(3, khachHang.getSoDienThoai());
            pstmt.setString(4, khachHang.getDienThoai());
            pstmt.setString(5, khachHang.getDiaChi());
            pstmt.executeUpdate();
        }
    }

    public void updateKhachHang(KhachHangModel khachHang) throws SQLException {
        String sql = "UPDATE KhachHang SET ten = ?, so_dien_thoai = ?, dien_thoai = ?, dia_chi = ? WHERE ma_khach_hang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, khachHang.getTen());
            pstmt.setString(2, khachHang.getSoDienThoai());
            pstmt.setString(3, khachHang.getDienThoai());
            pstmt.setString(4, khachHang.getDiaChi());
            pstmt.setString(5, khachHang.getMaKhachHang());
            pstmt.executeUpdate();
        }
    }

    public void deleteKhachHang(String maKhachHang) throws SQLException {
        String sql = "DELETE FROM KhachHang WHERE ma_khach_hang = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maKhachHang);
            pstmt.executeUpdate();
        }
    }
}
