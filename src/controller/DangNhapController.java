package controller;

import model.NguoiDung;
import view.DangNhapView;
import dao.NguoiDungDAO;

import java.sql.SQLException;
import javafx.scene.control.Alert;

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
                showAlert("Thành công",
                        "Đăng nhập thành công! Xin chào " + user.getTenDangNhap() + " (" + user.getVaiTro() + ")");
            } else {
                view.getMessageLabel().setText("Sai tên đăng nhập hoặc mật khẩu.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
