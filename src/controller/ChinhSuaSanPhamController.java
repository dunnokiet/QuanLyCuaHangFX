package controller;

import dao.SanPhamDAO;
import model.SanPhamModel;
import util.ThongBaoUtil;
import view.MainView;
import view.sanpham.ChinhSuaSanPhamView;
import view.sanpham.QuanLySanPhamView;

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
            try {
                String tenSanPham = chinhSuaSanPhamView.getTxtTenSanPham().getText();
                String loai = chinhSuaSanPhamView.getTxtLoai().getText();
                double gia = Double.parseDouble(chinhSuaSanPhamView.getTxtGia().getText());
                int soLuongTon = Integer.parseInt(chinhSuaSanPhamView.getTxtSoLuongTon().getText());

                if (!tenSanPham.isEmpty() && !loai.isEmpty() && gia >= 0 && soLuongTon >= 0) {
                    sanPhamDAO.sua(sanPhamModel);

                    List<SanPhamModel> danhSachSanPham = new ArrayList<>();
                    QuanLySanPhamView quanLySanPhamView = new QuanLySanPhamView();

                    new QuanLySanPhamController(mainView, quanLySanPhamView, danhSachSanPham);

                    mainView.setContent(quanLySanPhamView);

                    ThongBaoUtil.thongTin("Thành công", "Cập nhập sản phẩm thành công!");
                } else {
                    ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng điền đầy đủ thông tin hợp lệ!");
                }
            } catch (NumberFormatException e) {
                ThongBaoUtil.canhBao("Cảnh báo", e.getMessage());
            } catch (SQLException e) {
                ThongBaoUtil.baoLoi("Lỗi", e.getMessage());
                e.printStackTrace();
            }
        });

        chinhSuaSanPhamView.getBtnHuy().setOnAction(_ -> {
            List<SanPhamModel> danhSachSanPham = new ArrayList<>();
            QuanLySanPhamView quanLySanPhamView = new QuanLySanPhamView();

            new QuanLySanPhamController(mainView, quanLySanPhamView, danhSachSanPham);
            mainView.setContent(quanLySanPhamView);
        });
    }
}
