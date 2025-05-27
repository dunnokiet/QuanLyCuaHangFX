package controller.SanPham;

import model.SanPhamModel;
import util.ThongBaoUtil;
import view.MainView;
import view.SanPham.QuanLySanPhamView;
import view.SanPham.TaoSanPhamView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.SanPhamDAO;

public class TaoSanPhamController {
    private TaoSanPhamView taoSanPhamView;
    private MainView mainView;

    private SanPhamDAO sanPhamDAO;

    public TaoSanPhamController(MainView mainView, TaoSanPhamView taoSanPhamView) {
        this.mainView = mainView;
        this.taoSanPhamView = taoSanPhamView;

        sanPhamDAO = new SanPhamDAO();

        initListeners();
    }

    private void initListeners() {
        taoSanPhamView.getBtnTao().setOnAction(_ -> {
            try {
                String maSanPham = taoSanPhamView.getTxtMaSanPham().getText();
                String tenSanPham = taoSanPhamView.getTxtTenSanPham().getText();
                String loai = taoSanPhamView.getTxtLoai().getText();
                String giaStr = taoSanPhamView.getTxtGia().getText();
                String soLuongStr = taoSanPhamView.getTxtSoLuongTon().getText();

                if (maSanPham == null || maSanPham.trim().isEmpty() ||
                        tenSanPham == null || tenSanPham.trim().isEmpty() ||
                        loai == null || loai.trim().isEmpty() ||
                        giaStr == null || giaStr.trim().isEmpty() ||
                        soLuongStr == null || soLuongStr.trim().isEmpty()) {
                    ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng điền đầy đủ tất cả các trường!");
                    return;
                }

                double gia;
                int soLuongTon;
                try {
                    gia = Double.parseDouble(giaStr);
                    soLuongTon = Integer.parseInt(soLuongStr);
                } catch (NumberFormatException e) {
                    ThongBaoUtil.canhBao("Cảnh báo", "Giá và số lượng phải là số hợp lệ!");
                    return;
                }

                for (SanPhamModel sp : sanPhamDAO.layTatCaSanPham()) {
                    if (sp.getMaSanPham().equals(maSanPham)) {
                        ThongBaoUtil.canhBao("Cảnh báo", "Mã sản phẩm đã tồn tại!");
                        return;
                    }
                }

                SanPhamModel sanPhamModel = new SanPhamModel(maSanPham, tenSanPham, loai, gia, soLuongTon);

                if (!sanPhamModel.isThongTinHopLe()) {
                    ThongBaoUtil.canhBao("Cảnh báo", "Thông tin sản phẩm không hợp lệ!");
                    return;
                }

                sanPhamDAO.them(sanPhamModel);

                List<SanPhamModel> danhSachSanPham = new ArrayList<>();
                QuanLySanPhamView quanLySanPhamView = new QuanLySanPhamView();

                new QuanLySanPhamController(mainView, quanLySanPhamView, danhSachSanPham);

                ThongBaoUtil.thongTin("Thành công", "Tạo sản phẩm thành công!");
                mainView.setContent(quanLySanPhamView);
            } catch (SQLException e) {
                ThongBaoUtil.baoLoi("Lỗi", e.getMessage());
                e.printStackTrace();
            }
        });

        taoSanPhamView.getBtnHuy().setOnAction(_ -> {
            List<SanPhamModel> danhSachSanPham = new ArrayList<>();
            QuanLySanPhamView quanLySanPhamView = new QuanLySanPhamView();

            new QuanLySanPhamController(mainView, quanLySanPhamView, danhSachSanPham);
            mainView.setContent(quanLySanPhamView);
        });
    }

}