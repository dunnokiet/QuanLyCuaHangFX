package controller.KhachHang;

import util.ThongBaoUtil;
import view.MainView;
import view.KhachHang.QuanLyKhachHangView;
import view.KhachHang.TaoKhachHangView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.KhachHangDAO;
import model.KhachHangModel;

public class TaoKhachHangController {
    private TaoKhachHangView taoKhachHangView;
    private MainView mainView;

    private KhachHangDAO khachHangDAO;

    public TaoKhachHangController(MainView mainView, TaoKhachHangView taoKhachHangView) {
        this.mainView = mainView;
        this.taoKhachHangView = taoKhachHangView;

        khachHangDAO = new KhachHangDAO();

        initListeners();
    }

    private void initListeners() {
        taoKhachHangView.getBtnTao().setOnAction(_ -> {
            try {
                String maKhachHang = taoKhachHangView.getTxtMaKhachHang().getText();
                String tenKhachHang = taoKhachHangView.getTxtTenKhachHang().getText();
                String soDienThoai = taoKhachHangView.getTxtSoDienThoai().getText();
                String diaChi = taoKhachHangView.getTxtDiaChi().getText();

                if (maKhachHang == null || maKhachHang.trim().isEmpty() ||
                        tenKhachHang == null || tenKhachHang.trim().isEmpty() ||
                        soDienThoai == null || soDienThoai.trim().isEmpty() ||
                        diaChi == null || diaChi.trim().isEmpty()) {
                    ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng điền đầy đủ thông tin hợp lệ!");
                    return;
                }

                for (KhachHangModel kh : khachHangDAO.layTatCaKhachHang()) {
                    if (kh.getMaKhachHang().equals(maKhachHang)) {
                        ThongBaoUtil.canhBao("Cảnh báo", "Mã khách hàng đã tồn tại!");
                        return;
                    }
                }

                KhachHangModel khachHangModel = new KhachHangModel(maKhachHang, tenKhachHang, soDienThoai, diaChi);

                if (!khachHangModel.isThongTinHopLe()) {
                    ThongBaoUtil.canhBao("Cảnh báo", "Thông tin khách hàng không hợp lệ!");
                    return;
                }

                khachHangDAO.them(khachHangModel);

                List<KhachHangModel> danhSachKhachHang = new ArrayList<>();
                QuanLyKhachHangView quanLyKhachHangView = new QuanLyKhachHangView();
                new QuanLyKhachHangController(mainView, quanLyKhachHangView, danhSachKhachHang);

                ThongBaoUtil.thongTin("Thành công", "Tạo khách hàng thành công!");
                mainView.setContent(quanLyKhachHangView);

            } catch (SQLException e) {
                ThongBaoUtil.baoLoi("Lỗi", e.getMessage());
                e.printStackTrace();
            }
        });

        taoKhachHangView.getBtnHuy().setOnAction(_ -> {
            List<KhachHangModel> danhSachSanPham = new ArrayList<>();
            QuanLyKhachHangView quanLySanPhamView = new QuanLyKhachHangView();

            new QuanLyKhachHangController(mainView, quanLySanPhamView, danhSachSanPham);
            mainView.setContent(quanLySanPhamView);
        });
    }

}