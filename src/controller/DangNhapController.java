package controller;

import model.NguoiDungModel;
import view.DangNhapView;
import view.MainView;
import view.trangchu.TrangChuView;
import dao.NguoiDungDAO;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

import util.ThongBaoUtil;

public class DangNhapController {
    private DangNhapView dangNhapView;
    private NguoiDungModel nguoiDungModel;

    private Stage primaryStage;

    public DangNhapController(DangNhapView dangNhapView, NguoiDungModel nguoiDungModel, Stage primaryStage) {
        this.dangNhapView = dangNhapView;
        this.nguoiDungModel = nguoiDungModel;

        this.primaryStage = primaryStage;

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

            try {
                nguoiDungModel = new NguoiDungDAO().dangNhap(tenDangNhap, matKhau);

                if (nguoiDungModel != null) {

                    MainView mainView = new MainView(nguoiDungModel.getVaiTro(), nguoiDungModel.getTenDangNhap());
                    new MainController(mainView, nguoiDungModel, primaryStage);

                    mainView.setActiveButton(mainView.getBtnHome());

                    TrangChuView trangChuView = new TrangChuView(matKhau, tenDangNhap);
                    new TrangChuController(trangChuView);

                    mainView.setContent(trangChuView);

                    double centerX = primaryStage.getX() + primaryStage.getWidth() / 2;
                    double centerY = primaryStage.getY() + primaryStage.getHeight() / 2;

                    primaryStage.setScene(new Scene(mainView));
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
