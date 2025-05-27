package controller.SanPham;

import dao.SanPhamDAO;
import model.SanPhamModel;
import util.ThongBaoUtil;
import view.MainView;
import view.SanPham.ChinhSuaSanPhamView;
import view.SanPham.QuanLySanPhamView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChinhSuaSanPhamController {
    private ChinhSuaSanPhamView chinhSuaSanPhamView;
    private MainView mainView;

    private SanPhamModel sanPhamModel;

    private SanPhamDAO sanPhamDAO;

    public ChinhSuaSanPhamController(MainView mainView, ChinhSuaSanPhamView chinhSuaSanPhamView,
            SanPhamModel sanPhamModel) {

        this.mainView = mainView;
        this.chinhSuaSanPhamView = chinhSuaSanPhamView;
        this.sanPhamModel = sanPhamModel;

        this.sanPhamDAO = new SanPhamDAO();

        initListeners();
    }

    private void initListeners() {
        chinhSuaSanPhamView.getBtnLuu().setOnAction(_ -> {
            String tenSanPham = chinhSuaSanPhamView.getTxtTenSanPham().getText();
            String loai = chinhSuaSanPhamView.getTxtLoai().getText();
            String giaStr = chinhSuaSanPhamView.getTxtGia().getText();
            String soLuongTonStr = chinhSuaSanPhamView.getTxtSoLuongTon().getText();

            if (tenSanPham == null || tenSanPham.trim().isEmpty() ||
                    loai == null || loai.trim().isEmpty() ||
                    giaStr == null || giaStr.trim().isEmpty() ||
                    soLuongTonStr == null || soLuongTonStr.trim().isEmpty()) {
                ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            double gia;
            int soLuongTon;
            try {
                gia = Double.parseDouble(giaStr);
                soLuongTon = Integer.parseInt(soLuongTonStr);
            } catch (NumberFormatException e) {
                ThongBaoUtil.canhBao("Cảnh báo", "Giá và số lượng phải là số hợp lệ!");
                return;
            }

            sanPhamModel.setTen(tenSanPham);
            sanPhamModel.setLoai(loai);
            sanPhamModel.setGia(gia);
            sanPhamModel.setSoLuongTon(soLuongTon);

            if (!sanPhamModel.isThongTinHopLe()) {
                ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng điền đầy đủ thông tin hợp lệ!");
                return;
            }

            try {
                sanPhamDAO.sua(sanPhamModel);

                List<SanPhamModel> danhSachSanPham = new ArrayList<>();
                QuanLySanPhamView quanLySanPhamView = new QuanLySanPhamView();

                new QuanLySanPhamController(mainView, quanLySanPhamView, danhSachSanPham);

                ThongBaoUtil.thongTin("Thành công", "Cập nhập sản phẩm thành công!");
                mainView.setCenter(quanLySanPhamView);

            } catch (SQLException e) {
                ThongBaoUtil.baoLoi("Lỗi", e.getMessage());
                e.printStackTrace();
            }
        });

        chinhSuaSanPhamView.getBtnHuy().setOnAction(_ -> {
            List<SanPhamModel> danhSachSanPham = new ArrayList<>();
            QuanLySanPhamView quanLySanPhamView = new QuanLySanPhamView();

            new QuanLySanPhamController(mainView, quanLySanPhamView, danhSachSanPham);
            mainView.setCenter(quanLySanPhamView);
        });
    }
}
