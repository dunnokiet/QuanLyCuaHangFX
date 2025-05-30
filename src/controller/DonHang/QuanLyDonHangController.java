package controller.DonHang;

import dao.ChiTietDonHangDAO;
import dao.DonHangDAO;
import dto.ChiTietDonHangDTO;
import dto.DonHangDTO;
import util.ThongBaoUtil;
import view.MainView;
import view.DonHang.ChiTietDonHangView;
import view.DonHang.ChinhSuaChiTietView;
import view.DonHang.ChinhSuaDonHangView;
import view.DonHang.QuanLyDonHangView;
import view.DonHang.TaoChiTietView;
import view.DonHang.TaoDonHangView;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class QuanLyDonHangController {
    private MainView mainView;
    private QuanLyDonHangView quanLyDonHangView;
    private ChiTietDonHangView chiTietDonHangView;
    private List<DonHangDTO> danhSachDonHang;
    private DonHangDAO donHangDAO;
    private ChiTietDonHangDAO chiTietDonHangDAO;

    public QuanLyDonHangController(MainView mainView, QuanLyDonHangView quanLyDonHangView,
            ChiTietDonHangView chiTietDonHangView,
            List<DonHangDTO> danhSachDonHang) {
        this.mainView = mainView;
        this.quanLyDonHangView = quanLyDonHangView;
        this.chiTietDonHangView = chiTietDonHangView;

        this.danhSachDonHang = danhSachDonHang;

        this.donHangDAO = new DonHangDAO();
        this.chiTietDonHangDAO = new ChiTietDonHangDAO();

        initListeners();
        loadTableData();
    }

    private void initListeners() {
        quanLyDonHangView.getBtnTaoDonHang().setOnAction(_ -> {
            TaoDonHangView taoSanPhamView = new TaoDonHangView();
            TaoChiTietView taoChiTietView = new TaoChiTietView();
            new TaoDonHangController(mainView, taoSanPhamView, taoChiTietView);

            mainView.setCenter(taoSanPhamView);
            mainView.setRight(taoChiTietView);
        });

        quanLyDonHangView.getBtnSuaDonHang().setOnAction(_ -> {
            DonHangDTO selected = quanLyDonHangView.getTblDonHang().getSelectionModel().getSelectedItem();
            if (selected == null) {
                ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng chọn một đơn hàng để chỉnh sửa!");
                return;
            }
            ChinhSuaDonHangView chinhSuaDonHangView = new ChinhSuaDonHangView();
            ChinhSuaChiTietView chinhSuaChiTietView = new ChinhSuaChiTietView();
            new ChinhSuaDonHangController(mainView, chinhSuaDonHangView, chinhSuaChiTietView, selected);

            mainView.setCenter(chinhSuaDonHangView);
            mainView.setRight(chinhSuaChiTietView);
        });

        quanLyDonHangView.getBtnXoaDonHang().setOnAction(_ -> {
            DonHangDTO selected = quanLyDonHangView.getTblDonHang().getSelectionModel().getSelectedItem();
            if (selected == null) {
                ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng chọn một đơn hàng để xóa!");
                return;
            }

            boolean confirmed = ThongBaoUtil.xacNhan("Xác nhận", "Bạn có chắc chắn muốn xóa đơn hàng này?");
            if (!confirmed)
                return;

            try {
                chiTietDonHangDAO.xoaTheoMaDonHang(selected.getMaDonHang());
                donHangDAO.xoa(selected.getMaDonHang());
                loadTableData();
                chiTietDonHangView.getLblMaDonHang().setText("Mã đơn hàng: ");
                chiTietDonHangView.getLblTenKhachHang().setText("Khách hàng: ");
                chiTietDonHangView.getLblNgayDat().setText("Ngày đặt: ");
                chiTietDonHangView.getLblTongTien().setText("Tổng tiền: ");
                chiTietDonHangView.getLblTrangThai().setText("Trạng thái: ");
                chiTietDonHangView.getTblChiTietDonHang().getItems().clear();
                ThongBaoUtil.thongTin("Thành công", "Xóa đơn hàng thành công!");
            } catch (SQLException e) {
                ThongBaoUtil.baoLoi("Lỗi", "Không thể xóa đơn hàng: " + e.getMessage());
                e.printStackTrace();
            }
        });

        quanLyDonHangView.getTblDonHang().getSelectionModel().selectedItemProperty()
                .addListener((_, _, newSelection) -> {
                    if (newSelection != null) {
                        try {
                            List<ChiTietDonHangDTO> chiTietList = chiTietDonHangDAO
                                    .layChiTietDonHangDTO(newSelection.getMaDonHang());
                            chiTietDonHangView.updateChiTietDonHang(newSelection, chiTietList);
                        } catch (SQLException e) {
                            ThongBaoUtil.baoLoi("Lỗi", "Không thể tải chi tiết đơn hàng: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });

        quanLyDonHangView.getTxtTimKiem().textProperty().addListener((_, _, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                loadTableData();
                quanLyDonHangView.getTblDonHang().getItems().clear();
                quanLyDonHangView.getTblDonHang().getItems().addAll(danhSachDonHang);
            } else {
                String lowerKeyword = newValue.toLowerCase();
                List<DonHangDTO> filtered = danhSachDonHang.stream()
                        .filter(dh -> dh.getMaDonHang().toLowerCase().contains(lowerKeyword) ||
                                dh.getTenKhachHang().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
                quanLyDonHangView.getTblDonHang().getItems().clear();
                quanLyDonHangView.getTblDonHang().getItems().addAll(filtered);
            }
        });
    }

    private void loadTableData() {
        try {
            danhSachDonHang = donHangDAO.layTatCaDonGanNhat();
            quanLyDonHangView.getTblDonHang().getItems().clear();
            quanLyDonHangView.getTblDonHang().getItems().addAll(danhSachDonHang);
        } catch (SQLException e) {
            ThongBaoUtil.baoLoi("Lỗi", "Không thể tải danh sách đơn hàng: " + e.getMessage());
            e.printStackTrace();
        }
    }
}