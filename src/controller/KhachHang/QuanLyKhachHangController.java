package controller.KhachHang;

import util.ThongBaoUtil;
import view.MainView;
import view.KhachHang.ChinhSuaKhachHangView;
import view.KhachHang.QuanLyKhachHangView;
import view.KhachHang.TaoKhachHangView;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import dao.KhachHangDAO;
import model.KhachHangModel;

public class QuanLyKhachHangController {
    private MainView mainView;
    private QuanLyKhachHangView quanLyKhachHangView;
    private List<KhachHangModel> danhSachKhachHang;

    private KhachHangDAO khachHangDAO;

    public QuanLyKhachHangController(MainView mainView, QuanLyKhachHangView quanLyKhachHangView,
            List<KhachHangModel> danhSachKhachHang) {

        this.mainView = mainView;
        this.quanLyKhachHangView = quanLyKhachHangView;
        this.danhSachKhachHang = danhSachKhachHang;

        this.khachHangDAO = new KhachHangDAO();

        initListeners();
        loadTableData();
    }

    private void initListeners() {
        quanLyKhachHangView.getBtnTaoKhachHang().setOnAction(_ -> {

            TaoKhachHangView taoKhachHangView = new TaoKhachHangView();
            new TaoKhachHangController(mainView, taoKhachHangView);

            mainView.setCenter(taoKhachHangView);
        });

        quanLyKhachHangView.getBtnSuaKhachHang().setOnAction(_ -> {
            KhachHangModel khachHangModel = quanLyKhachHangView.getTblKhachHang().getSelectionModel().getSelectedItem();

            if (khachHangModel != null) {
                ChinhSuaKhachHangView chinhSuaView = new ChinhSuaKhachHangView();

                chinhSuaView.getTxtMaKhachHang().setText(khachHangModel.getMaKhachHang());
                chinhSuaView.getTxtTenKhachHang().setText(khachHangModel.getTen());
                chinhSuaView.getTxtSoDienThoai().setText(khachHangModel.getSoDienThoai());
                chinhSuaView.getTxtDiaChi().setText(khachHangModel.getDiaChi());

                new ChinhSuaKhachHangController(mainView, chinhSuaView, khachHangModel);

                mainView.setCenter(chinhSuaView);
            } else {
                ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng chọn sản phẩm để chỉnh sửa!");
            }
        });

        quanLyKhachHangView.getBtnXoaKhachHang().setOnAction(_ -> {
            KhachHangModel khachHangModel = quanLyKhachHangView.getTblKhachHang().getSelectionModel().getSelectedItem();

            if (khachHangModel != null) {
                if (ThongBaoUtil.xacNhan("Xác nhận",
                        "Bạn có chắc chăn muốn xóa khách hàng \"%s\" không?".formatted(khachHangModel.getTen()))) {

                    try {
                        khachHangDAO.xoa(khachHangModel.getMaKhachHang());
                        loadTableData();
                        ThongBaoUtil.thongTin("Thành công",
                                "Xóa khách hàng \"%s\" thành công!".formatted(khachHangModel.getTen()));
                    } catch (SQLException e) {
                        ThongBaoUtil.thongTin("Lỗi", e.getMessage());
                        e.printStackTrace();
                    }
                }

            } else {
                ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng chọn khách hàng để xóa!");
            }
        });

        quanLyKhachHangView.getTxtTimKiem().textProperty().addListener((_, _, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                loadTableData();
                quanLyKhachHangView.getTblKhachHang().getItems().clear();
                quanLyKhachHangView.getTblKhachHang().getItems().addAll(danhSachKhachHang);
            } else {
                String lowerKeyword = newValue.toLowerCase();
                List<KhachHangModel> filtered = danhSachKhachHang.stream()
                        .filter(sp -> sp.getMaKhachHang().toLowerCase().contains(lowerKeyword) ||
                                sp.getTen().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
                quanLyKhachHangView.getTblKhachHang().getItems().clear();
                quanLyKhachHangView.getTblKhachHang().getItems().addAll(filtered);
            }
        });

    }

    private void loadTableData() {
        try {
            danhSachKhachHang = khachHangDAO.layTatCaKhachHang();
            quanLyKhachHangView.getTblKhachHang().getItems().clear();
            quanLyKhachHangView.getTblKhachHang().getItems().addAll(danhSachKhachHang);
        } catch (SQLException e) {
            ThongBaoUtil.baoLoi("Lỗi", e.getMessage());
            e.printStackTrace();
        }
    }
}