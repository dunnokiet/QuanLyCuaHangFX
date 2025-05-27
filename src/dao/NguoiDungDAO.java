package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.NguoiDungModel;
import util.DatabaseConnection;

import java.sql.PreparedStatement;

public class NguoiDungDAO {
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

}
