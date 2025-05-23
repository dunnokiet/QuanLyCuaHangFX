package dao;

import model.NguoiDung;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class NguoiDungDAO {
    public NguoiDung xacThuc(String tenDangNhap, String matKhau) throws SQLException {
        String sql = "SELECT * FROM NguoiDung WHERE ten_dang_nhap = ? AND mat_khau = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tenDangNhap);
            pstmt.setString(2, matKhau);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new NguoiDung(
                        rs.getString("ma_nguoi_dung"),
                        rs.getString("ten_dang_nhap"),
                        rs.getString("mat_khau"),
                        rs.getString("vai_tro"));
            }
        }
        return null;
    }
}
