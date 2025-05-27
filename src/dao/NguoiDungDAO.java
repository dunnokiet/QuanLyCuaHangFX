package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.NguoiDungModel;
import util.DatabaseConnection;

import java.sql.PreparedStatement;

public class NguoiDungDAO {
    public void them(NguoiDungModel nguoiDung) throws SQLException {
        String sql = "INSERT INTO NguoiDung (ma_nguoi_dung, ten_dang_nhap, mat_khau, vai_tro) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nguoiDung.getMaNguoiDung());
            pstmt.setString(2, nguoiDung.getTenDangNhap());
            pstmt.setString(3, nguoiDung.getMatKhau());
            pstmt.setString(4, nguoiDung.getVaiTro());
            pstmt.executeUpdate();
        }
    }

    public void sua(NguoiDungModel nguoiDung) throws SQLException {
        String sql = "UPDATE NguoiDung SET ten_dang_nhap = ?, mat_khau = ?, vai_tro = ? WHERE ma_nguoi_dung = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nguoiDung.getTenDangNhap());
            pstmt.setString(2, nguoiDung.getMatKhau());
            pstmt.setString(3, nguoiDung.getVaiTro());
            pstmt.setString(4, nguoiDung.getMaNguoiDung());
            pstmt.executeUpdate();
        }
    }

    public void xoa(String maNguoiDung) throws SQLException {
        String sql = "DELETE FROM NguoiDung WHERE ma_nguoi_dung = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maNguoiDung);
            pstmt.executeUpdate();
        }
    }

    public NguoiDungModel layNguoiDungTheoTen(String tenDangNhap) throws SQLException {
        String query = "SELECT * FROM NguoiDung WHERE ten_dang_nhap = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tenDangNhap);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new NguoiDungModel(
                            rs.getString("ma_nguoi_dung"),
                            rs.getString("ten_dang_nhap"),
                            rs.getString("mat_khau"),
                            rs.getString("vai_tro"));
                }
            }
        }

        return null;
    }

    public List<NguoiDungModel> layTatCaNguoiDung() throws SQLException {
        List<NguoiDungModel> sanPhamList = new ArrayList<>();
        String query = "SELECT ma_nguoi_dung, ten_dang_nhap, mat_khau, vai_tro FROM NguoiDung";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sanPhamList.add(new NguoiDungModel(
                        rs.getString("ma_nguoi_dung"),
                        rs.getString("ten_dang_nhap"),
                        rs.getString("mat_khau"),
                        rs.getString("vai_tro")));
            }
        }
        return sanPhamList;
    }
}
