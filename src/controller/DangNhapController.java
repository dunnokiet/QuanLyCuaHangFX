package controller;

import model.NguoiDung;
import view.DangNhapView;
import dao.NguoiDungDAO;

import java.sql.SQLException;

import util.ThongBaoUtil;

public class DangNhapController {
    private DangNhapView view;
    private NguoiDungDAO nguoiDungDAO;

    public DangNhapController(DangNhapView view) {
        this.view = view;
        this.nguoiDungDAO = new NguoiDungDAO();

        view.getLoginButton().setOnAction(_ -> xuLyDangNhap());
    }

    private void xuLyDangNhap() {
        String tenDangNhap = view.getTenDangNhapField().getText();
        String matKhau = view.getMatKhauField().getText();

        if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
            view.getMessageLabel().setText("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            return;
        }

        try {
            NguoiDung user = nguoiDungDAO.xacThuc(tenDangNhap, matKhau);

            if (user != null) {
                ThongBaoUtil.thongTin("Thành công",
                        "Đăng nhập thành công! Xin chào " + user.getTenDangNhap() + " (" + user.getVaiTro() + ")");
            } else {
                view.getMessageLabel().setText("Sai tên đăng nhập hoặc mật khẩu.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ThongBaoUtil.baoLoi("Lỗi", "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
}
