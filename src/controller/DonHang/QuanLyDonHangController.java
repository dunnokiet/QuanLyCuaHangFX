package controller.DonHang;

import dao.ChiTietDonHangDAO;
import dao.DonHangDAO;
import dto.ChiTietDonHangDTO;
import dto.DonHangDTO;
import util.ThongBaoUtil;
import view.MainView;
import view.DonHang.ChiTietDonHangView;
import view.DonHang.QuanLyDonHangView;
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
            new TaoDonHangController(mainView, taoSanPhamView);

            mainView.setCenter(taoSanPhamView);
            mainView.setRight(null);
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