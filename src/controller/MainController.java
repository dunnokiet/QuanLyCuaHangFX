package controller;

import java.util.ArrayList;
import java.util.List;

import controller.DonHang.QuanLyDonHangController;
import controller.KhachHang.QuanLyKhachHangController;
import controller.NguoiDung.QuanLyNguoiDungController;
import controller.SanPham.QuanLySanPhamController;
import controller.TrangChu.TrangChuController;
import dto.DonHangDTO;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.KhachHangModel;
import model.NguoiDungModel;
import model.SanPhamModel;
import util.ThongBaoUtil;
import view.DangNhapView;
import view.MainView;
import view.DonHang.ChiTietDonHangView;
import view.DonHang.QuanLyDonHangView;
import view.KhachHang.QuanLyKhachHangView;
import view.NguoiDung.QuanLyNguoiDungView;
import view.SanPham.QuanLySanPhamView;
import view.TrangChu.TrangChuView;

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

            mainView.setCenter(trangChuView);
            mainView.setRight(null);
        });

        mainView.getBtnSanPham().setOnAction(_ -> {
            mainView.setActiveButton(mainView.getBtnSanPham());

            List<SanPhamModel> danhSachSanPham = new ArrayList<>();
            QuanLySanPhamView quanLySanPhamView = new QuanLySanPhamView();

            new QuanLySanPhamController(mainView, quanLySanPhamView, danhSachSanPham);
            mainView.setCenter(quanLySanPhamView);
            mainView.setRight(null);
        });

        mainView.getBtnKhachHang().setOnAction(_ -> {
            mainView.setActiveButton(mainView.getBtnKhachHang());

            List<KhachHangModel> danhSachKhachHang = new ArrayList<>();
            QuanLyKhachHangView quanLyKhachHangView = new QuanLyKhachHangView();

            new QuanLyKhachHangController(mainView, quanLyKhachHangView, danhSachKhachHang);
            mainView.setCenter(quanLyKhachHangView);
            mainView.setRight(null);
        });

        mainView.getBtnNguoiDung().setOnAction(_ -> {
            mainView.setActiveButton(mainView.getBtnNguoiDung());

            List<NguoiDungModel> danhSachNguoiDung = new ArrayList<>();
            QuanLyNguoiDungView quanLyNguoiDungView = new QuanLyNguoiDungView();

            new QuanLyNguoiDungController(mainView, quanLyNguoiDungView, danhSachNguoiDung);
            mainView.setCenter(quanLyNguoiDungView);
            mainView.setRight(null);
        });

        mainView.getBtnDonHang().setOnAction(_ -> {
            mainView.setActiveButton(mainView.getBtnDonHang());

            QuanLyDonHangView quanLyDonHangView = new QuanLyDonHangView();
            ChiTietDonHangView chiTietDonHangView = new ChiTietDonHangView();

            List<DonHangDTO> danhSachDonHang = new ArrayList<>();

            new QuanLyDonHangController(mainView, quanLyDonHangView, chiTietDonHangView, danhSachDonHang);

            mainView.setCenter(quanLyDonHangView);
            mainView.setRight(chiTietDonHangView);
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

                Scene scene = new Scene(dangNhapView, 500, 400);
                scene.getStylesheets().add(getClass().getResource("../resources/primer-light.css").toExternalForm());
                primaryStage.setScene(scene);
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
