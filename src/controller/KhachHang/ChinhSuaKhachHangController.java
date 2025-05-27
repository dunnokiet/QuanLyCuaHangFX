package controller.KhachHang;

import dao.KhachHangDAO;
import model.KhachHangModel;

import util.ThongBaoUtil;
import view.MainView;
import view.KhachHang.ChinhSuaKhachHangView;
import view.KhachHang.QuanLyKhachHangView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChinhSuaKhachHangController {
    private ChinhSuaKhachHangView chinhSuaKhachHangView;
    private MainView mainView;

    private KhachHangModel khachHangModel;

    private KhachHangDAO khachHangDAO;

    public ChinhSuaKhachHangController(MainView mainView, ChinhSuaKhachHangView chinhSuaKhachHangView,
            KhachHangModel khachHangModel) {

        this.mainView = mainView;
        this.chinhSuaKhachHangView = chinhSuaKhachHangView;
        this.khachHangModel = khachHangModel;

        this.khachHangDAO = new KhachHangDAO();

        initListeners();
    }

    private void initListeners() {
        chinhSuaKhachHangView.getBtnLuu().setOnAction(_ -> {
            String tenKhachHang = chinhSuaKhachHangView.getTxtTenKhachHang().getText();
            String soDienThoai = chinhSuaKhachHangView.getTxtSoDienThoai().getText();
            String diaChi = chinhSuaKhachHangView.getTxtDiaChi().getText();

            if (tenKhachHang == null || tenKhachHang.trim().isEmpty() ||
                    soDienThoai == null || soDienThoai.trim().isEmpty() ||
                    diaChi == null || diaChi.trim().isEmpty()) {
                ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng điền đầy đủ thông tin hợp lệ!");
                return;
            }

            khachHangModel.setTen(tenKhachHang);
            khachHangModel.setSoDienThoai(soDienThoai);
            khachHangModel.setDiaChi(diaChi);

            if (!khachHangModel.isThongTinHopLe()) {
                ThongBaoUtil.canhBao("Cảnh báo", "Thông tin khách hàng không hợp lệ!");
                return;
            }

            try {
                khachHangDAO.sua(khachHangModel);

                List<KhachHangModel> danhSachKhachHang = new ArrayList<>();
                QuanLyKhachHangView quanLyKhachHangView = new QuanLyKhachHangView();

                new QuanLyKhachHangController(mainView, quanLyKhachHangView, danhSachKhachHang);

                ThongBaoUtil.thongTin("Thành công", "Cập nhập khách hàng thành công!");
                mainView.setContent(quanLyKhachHangView);

            } catch (SQLException e) {
                ThongBaoUtil.baoLoi("Lỗi", e.getMessage());
                e.printStackTrace();
            }
        });

        chinhSuaKhachHangView.getBtnHuy().setOnAction(_ -> {
            List<KhachHangModel> danhSachSanPham = new ArrayList<>();
            QuanLyKhachHangView quanLySanPhamView = new QuanLyKhachHangView();

            new QuanLyKhachHangController(mainView, quanLySanPhamView, danhSachSanPham);
            mainView.setContent(quanLySanPhamView);
        });
    }
}
