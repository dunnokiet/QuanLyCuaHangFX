package controller;

import dao.SanPhamDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.SanPhamModel;
import util.ThongBaoUtil;
import view.MainView;
import view.sanpham.ChinhSuaSanPhamView;
import view.sanpham.QuanLySanPhamView;
import view.sanpham.TaoSanPhamView;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class QuanLySanPhamController {
    private MainView mainView;
    private QuanLySanPhamView quanLySanPhamView;
    private List<SanPhamModel> danhSachSanPham;

    private SanPhamDAO sanPhamDAO;

    public QuanLySanPhamController(MainView mainView, QuanLySanPhamView quanLySanPhamView,
            List<SanPhamModel> danhSachSanPham) {

        this.mainView = mainView;
        this.quanLySanPhamView = quanLySanPhamView;
        this.danhSachSanPham = danhSachSanPham;

        this.sanPhamDAO = new SanPhamDAO();

        initListeners();
        loadTableData();
    }

    private void initListeners() {
        quanLySanPhamView.getBtnTaoSanPham().setOnAction(_ -> {
            TaoSanPhamView taoSanPhamView = new TaoSanPhamView();
            new TaoSanPhamController(mainView, taoSanPhamView);
            mainView.setContent(taoSanPhamView);
        });

        quanLySanPhamView.getBtnSuaSanPham().setOnAction(_ -> {
            SanPhamModel sanPhamModel = quanLySanPhamView.getTblSanPham().getSelectionModel().getSelectedItem();

            if (sanPhamModel != null) {
                ChinhSuaSanPhamView chinhSuaView = new ChinhSuaSanPhamView();

                chinhSuaView.getTxtMaSanPham().setText(sanPhamModel.getMaSanPham());
                chinhSuaView.getTxtTenSanPham().setText(sanPhamModel.getTen());
                chinhSuaView.getTxtLoai().setText(sanPhamModel.getLoai());
                chinhSuaView.getTxtGia().setText(String.valueOf(sanPhamModel.getGia()));
                chinhSuaView.getTxtSoLuongTon().setText(String.valueOf(sanPhamModel.getSoLuongTon()));

                new ChinhSuaSanPhamController(mainView, chinhSuaView, sanPhamModel);

                mainView.setContent(chinhSuaView);
            } else {
                ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng chọn sản phẩm để chỉnh sửa!");
            }
        });

        quanLySanPhamView.getBtnXoaSanPham().setOnAction(_ -> {
            SanPhamModel sanPhamModel = quanLySanPhamView.getTblSanPham().getSelectionModel().getSelectedItem();

            if (sanPhamModel != null) {
                if (ThongBaoUtil.xacNhan("Xác nhận",
                        "Bạn có chắc chăn muốn xóa sản phẩm \"%s\" không?".formatted(sanPhamModel.getTen()))) {

                    try {
                        sanPhamDAO.xoa(sanPhamModel.getMaSanPham());
                        loadTableData();
                        ThongBaoUtil.thongTin("Thành công",
                                "Xóa sản phẩm \"%s\" thành công!".formatted(sanPhamModel.getTen()));
                    } catch (SQLException e) {
                        ThongBaoUtil.thongTin("Lỗi", e.getMessage());
                        e.printStackTrace();
                    }
                }

            } else {
                ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng chọn sản phẩm để xóa!");
            }
        });

        quanLySanPhamView.getTxtTimKiem().textProperty().addListener((_, _, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                loadTableData();
                quanLySanPhamView.getTblSanPham().getItems().clear();
                quanLySanPhamView.getTblSanPham().getItems().addAll(danhSachSanPham);
            } else {
                String lowerKeyword = newValue.toLowerCase();
                List<SanPhamModel> filtered = danhSachSanPham.stream()
                        .filter(sp -> sp.getMaSanPham().toLowerCase().contains(lowerKeyword) ||
                                sp.getTen().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
                quanLySanPhamView.getTblSanPham().getItems().clear();
                quanLySanPhamView.getTblSanPham().getItems().addAll(filtered);
            }
        });

    }

    private void loadTableData() {
        try {
            danhSachSanPham = sanPhamDAO.layTatCaSanPham();
            quanLySanPhamView.getTblSanPham().getItems().clear();
            quanLySanPhamView.getTblSanPham().getItems().addAll(danhSachSanPham);
        } catch (SQLException e) {
            ThongBaoUtil.baoLoi("Lỗi", e.getMessage());
            e.printStackTrace();
        }
    }
}