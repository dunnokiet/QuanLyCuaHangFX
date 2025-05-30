package controller;

import model.NguoiDungModel;
import view.DangNhapView;
import view.MainView;
import view.TrangChu.TrangChuView;
import dao.NguoiDungDAO;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

import controller.TrangChu.TrangChuController;
import util.ThongBaoUtil;

public class DangNhapController {
    private DangNhapView dangNhapView;
    private NguoiDungModel nguoiDungModel;

    private NguoiDungDAO nguoiDungDAO;

    private Stage primaryStage;

    public DangNhapController(DangNhapView dangNhapView, NguoiDungModel nguoiDungModel, Stage primaryStage) {
        this.dangNhapView = dangNhapView;
        this.nguoiDungModel = nguoiDungModel;

        this.primaryStage = primaryStage;

        nguoiDungDAO = new NguoiDungDAO();

        initListeners();
    }

    private void initListeners() {
        dangNhapView.getBtnDangNhap().setOnAction(_ -> {
            String tenDangNhap = dangNhapView.getTxtTenDangNhap().getText();
            String matKhau = dangNhapView.getPwdMatKhau().getText();

            if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
                dangNhapView.getLblThongBao().setText("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
                return;
            }

            nguoiDungModel.setTenDangNhap(tenDangNhap);
            nguoiDungModel.setMatKhau(matKhau);

            if (!nguoiDungModel.isThongTinHopLe()) {
                dangNhapView.getLblThongBao().setText("Thông tin người dùng không hợp lệ.");
                return;
            }

            try {
                NguoiDungModel kq = nguoiDungDAO.layNguoiDungTheoTen(tenDangNhap);

                if (kq != null && kq.kiemTraDangNhap(tenDangNhap, matKhau)) {
                    nguoiDungModel = kq;

                    MainView mainView = new MainView(nguoiDungModel.isQuanLy(), nguoiDungModel.getTenDangNhap());
                    new MainController(mainView, nguoiDungModel, primaryStage);

                    mainView.setActiveButton(mainView.getBtnHome());

                    TrangChuView trangChuView = new TrangChuView(matKhau, tenDangNhap);
                    new TrangChuController(trangChuView);

                    mainView.setCenter(trangChuView);

                    double centerX = primaryStage.getX() + primaryStage.getWidth() / 2;
                    double centerY = primaryStage.getY() + primaryStage.getHeight() / 2;

                    Scene scene = new Scene(mainView, 1420, 720);
                    scene.getStylesheets()
                            .add(getClass().getResource("../resources/primer-light.css").toExternalForm());
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("Hệ Thống Quản Lý Cửa Hàng");

                    primaryStage.sizeToScene();
                    double newX = centerX - primaryStage.getWidth() / 2;
                    double newY = centerY - primaryStage.getHeight() / 2;

                    primaryStage.setX(newX);
                    primaryStage.setY(newY);

                } else {
                    dangNhapView.getLblThongBao().setText("Sai tên đăng nhập hoặc mật khẩu.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                ThongBaoUtil.baoLoi("Lỗi", e.getMessage());
            }

        });

    }
}
