package controller;

import model.SanPhamModel;
import util.ThongBaoUtil;
import view.MainView;
import view.sanpham.QuanLySanPhamView;
import view.sanpham.TaoSanPhamView;

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
                double gia = Double.parseDouble(taoSanPhamView.getTxtGia().getText());
                int soLuongTon = Integer.parseInt(taoSanPhamView.getTxtSoLuongTon().getText());

                if (!maSanPham.isEmpty() && !tenSanPham.isEmpty() && !loai.isEmpty() && gia >= 0 && soLuongTon >= 0) {

                    for (SanPhamModel sp : sanPhamDAO.layTatCaSanPham()) {
                        if (sp.getMaSanPham().equals(maSanPham)) {
                            ThongBaoUtil.canhBao("Cảnh báo", "Mã sản phẩm đã tồn tại!");
                            return;
                        }
                    }

                    SanPhamModel sanPham = new SanPhamModel(maSanPham, tenSanPham, loai, gia, soLuongTon);
                    sanPhamDAO.them(sanPham);

                    List<SanPhamModel> danhSachSanPham = new ArrayList<>();
                    QuanLySanPhamView quanLySanPhamView = new QuanLySanPhamView();
                    new QuanLySanPhamController(mainView, quanLySanPhamView, danhSachSanPham);

                    ThongBaoUtil.thongTin("Thành công", "Tạo sản phẩm thành công!");
                    mainView.setContent(quanLySanPhamView);
                } else {
                    ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng điền đầy đủ thông tin hợp lệ!");
                }
            } catch (NumberFormatException e) {
                ThongBaoUtil.canhBao("Cảnh báo", "Giá và số lượng phải là số!");

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