package controller.NguoiDung;

import util.ThongBaoUtil;
import view.MainView;
import view.NguoiDung.ChinhSuaNguoiDungView;
import view.NguoiDung.QuanLyNguoiDungView;
import view.NguoiDung.TaoNguoiDungView;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import dao.NguoiDungDAO;
import model.NguoiDungModel;

public class QuanLyNguoiDungController {
    private MainView mainView;
    private QuanLyNguoiDungView quanLyNguoiDungView;
    private List<NguoiDungModel> danhSachKhachHang;

    private NguoiDungDAO nguoiDungDAO;

    public QuanLyNguoiDungController(MainView mainView, QuanLyNguoiDungView quanLyNguoiDungView,
            List<NguoiDungModel> danhSachKhachHang) {

        this.mainView = mainView;
        this.quanLyNguoiDungView = quanLyNguoiDungView;
        this.danhSachKhachHang = danhSachKhachHang;

        this.nguoiDungDAO = new NguoiDungDAO();

        initListeners();
        loadTableData();
    }

    private void initListeners() {
        quanLyNguoiDungView.getBtnTaoNguoiDung().setOnAction(_ -> {

            TaoNguoiDungView taoNguoiDungView = new TaoNguoiDungView();
            new TaoNguoiDungController(mainView, taoNguoiDungView);

            mainView.setContent(taoNguoiDungView);
        });

        quanLyNguoiDungView.getBtnSuaNguoiDung().setOnAction(_ -> {
            NguoiDungModel nguoiDungModel = quanLyNguoiDungView.getTblNguoiDung().getSelectionModel().getSelectedItem();

            if (nguoiDungModel != null) {
                ChinhSuaNguoiDungView chinhSuaNguoiDungView = new ChinhSuaNguoiDungView();

                chinhSuaNguoiDungView.getTxtMaNguoiDung().setText(nguoiDungModel.getMaNguoiDung());
                chinhSuaNguoiDungView.getTxtTenNguoiDung().setText(nguoiDungModel.getTenDangNhap());
                chinhSuaNguoiDungView.getTxtMatKhau().setText(nguoiDungModel.getMatKhau());
                chinhSuaNguoiDungView.setVaiTro(nguoiDungModel.getVaiTro());

                new ChinhSuaNguoiDungController(mainView, chinhSuaNguoiDungView, nguoiDungModel);

                mainView.setContent(chinhSuaNguoiDungView);
            } else {
                ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng chọn người dùng để chỉnh sửa!");
            }
        });

        quanLyNguoiDungView.getBtnXoaNguoiDung().setOnAction(_ -> {
            NguoiDungModel nguoiDungModel = quanLyNguoiDungView.getTblNguoiDung().getSelectionModel().getSelectedItem();

            if (nguoiDungModel != null) {
                if (ThongBaoUtil.xacNhan("Xác nhận",
                        "Bạn có chắc chăn muốn xóa người dùng \"%s\" không?"
                                .formatted(nguoiDungModel.getTenDangNhap()))) {

                    try {
                        nguoiDungDAO.xoa(nguoiDungModel.getMaNguoiDung());
                        loadTableData();
                        ThongBaoUtil.thongTin("Thành công",
                                "Xóa người dùng \"%s\" thành công!".formatted(nguoiDungModel.getTenDangNhap()));
                    } catch (SQLException e) {
                        ThongBaoUtil.thongTin("Lỗi", e.getMessage());
                        e.printStackTrace();
                    }
                }

            } else {
                ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng chọn người dùng để xóa!");
            }
        });

        quanLyNguoiDungView.getTxtTimKiem().textProperty().addListener((_, _, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                loadTableData();
                quanLyNguoiDungView.getTblNguoiDung().getItems().clear();
                quanLyNguoiDungView.getTblNguoiDung().getItems().addAll(danhSachKhachHang);
            } else {
                String lowerKeyword = newValue.toLowerCase();
                List<NguoiDungModel> filtered = danhSachKhachHang.stream()
                        .filter(sp -> sp.getMaNguoiDung().toLowerCase().contains(lowerKeyword) ||
                                sp.getTenDangNhap().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
                quanLyNguoiDungView.getTblNguoiDung().getItems().clear();
                quanLyNguoiDungView.getTblNguoiDung().getItems().addAll(filtered);
            }
        });

    }

    private void loadTableData() {
        try {
            danhSachKhachHang = nguoiDungDAO.layTatCaNguoiDung();
            quanLyNguoiDungView.getTblNguoiDung().getItems().clear();
            quanLyNguoiDungView.getTblNguoiDung().getItems().addAll(danhSachKhachHang);
        } catch (SQLException e) {
            ThongBaoUtil.baoLoi("Lỗi", e.getMessage());
            e.printStackTrace();
        }
    }
}