package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.NguoiDungModel;
import model.SanPhamModel;
import util.ThongBaoUtil;
import view.DangNhapView;
import view.MainView;
import view.sanpham.QuanLySanPhamView;
import view.trangchu.TrangChuView;

public class MainController {
    private MainView mainView;

    private NguoiDungModel nguoiDungModel;

    private Stage primaryStage;

    public MainController(MainView mainView, NguoiDungModel nguoiDungModel,
            Stage primaryStage) {
        this.mainView = mainView;

        this.nguoiDungModel = nguoiDungModel;

        this.primaryStage = primaryStage;

        initListeners();
    }

    private void initListeners() {
        mainView.getBtnHome().setOnAction(_ -> {
            mainView.setActiveButton(mainView.getBtnHome());
            TrangChuView trangChuView = new TrangChuView(nguoiDungModel.getVaiTro(), nguoiDungModel.getTenDangNhap());
            new TrangChuController(trangChuView);

            mainView.setContent(trangChuView);
        });

        mainView.getBtnSanPham().setOnAction(_ -> {
            mainView.setActiveButton(mainView.getBtnSanPham());

            List<SanPhamModel> danhSachSanPham = new ArrayList<>();
            QuanLySanPhamView quanLySanPhamView = new QuanLySanPhamView();

            new QuanLySanPhamController(mainView, quanLySanPhamView, danhSachSanPham);
            mainView.setContent(quanLySanPhamView);
        });

        mainView.getBtnDangXuat().setOnAction(_ -> {
            if (ThongBaoUtil.xacNhan("Đăng xuất", "Bạn có chắc chắn muốn đăng xuất?")) {
                nguoiDungModel.setMaNguoiDung(null);
                nguoiDungModel.setMatKhau(null);
                nguoiDungModel.setTenDangNhap(null);
                nguoiDungModel.setVaiTro(null);

                DangNhapView dangNhapView = new DangNhapView();
                new DangNhapController(dangNhapView, nguoiDungModel, primaryStage);

                double centerX = primaryStage.getX() + primaryStage.getWidth() / 2;
                double centerY = primaryStage.getY() + primaryStage.getHeight() / 2;

                primaryStage.setScene(new Scene(dangNhapView, 400, 300));
                primaryStage.setTitle("Đăng nhập - Hệ Thống Quản Lý Cửa Hàng");

                primaryStage.sizeToScene();
                double newX = centerX - primaryStage.getWidth() / 2;
                double newY = centerY - primaryStage.getHeight() / 2;

                primaryStage.setX(newX);
                primaryStage.setY(newY);
            }
        });
    }
}
