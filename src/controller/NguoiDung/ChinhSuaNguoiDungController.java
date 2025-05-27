package controller.NguoiDung;

import util.ThongBaoUtil;
import view.MainView;
import view.NguoiDung.ChinhSuaNguoiDungView;
import view.NguoiDung.QuanLyNguoiDungView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.NguoiDungDAO;
import model.NguoiDungModel;

public class ChinhSuaNguoiDungController {
    private ChinhSuaNguoiDungView chinhSuaNguoiDungView;
    private MainView mainView;

    private NguoiDungModel nguoiDungModel;

    private NguoiDungDAO nguoiDungDAO;

    public ChinhSuaNguoiDungController(MainView mainView, ChinhSuaNguoiDungView chinhSuaNguoiDungView,
            NguoiDungModel nguoiDungModel) {

        this.mainView = mainView;
        this.chinhSuaNguoiDungView = chinhSuaNguoiDungView;
        this.nguoiDungModel = nguoiDungModel;

        this.nguoiDungDAO = new NguoiDungDAO();

        initListeners();
    }

    private void initListeners() {
        chinhSuaNguoiDungView.getBtnLuu().setOnAction(_ -> {
            String tenNguoiDung = chinhSuaNguoiDungView.getTxtTenNguoiDung().getText();
            String vaiTro = chinhSuaNguoiDungView.getRVaiTro();
            String matKhau = chinhSuaNguoiDungView.getTxtMatKhau().getText();

            if (tenNguoiDung == null || tenNguoiDung.trim().isEmpty() ||
                    vaiTro == null || vaiTro.trim().isEmpty() ||
                    matKhau == null || matKhau.trim().isEmpty()) {
                ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng điền đầy đủ thông tin hợp lệ!");
                return;
            }

            nguoiDungModel.setTenDangNhap(tenNguoiDung);
            nguoiDungModel.setVaiTro(vaiTro);
            nguoiDungModel.setMatKhau(matKhau);

            if (!nguoiDungModel.isThongTinHopLe()) {
                ThongBaoUtil.canhBao("Cảnh báo", "Thông tin khách hàng không hợp lệ!");
                return;
            }

            try {
                nguoiDungDAO.sua(nguoiDungModel);

                List<NguoiDungModel> danhSachKhachHang = new ArrayList<>();
                QuanLyNguoiDungView quanLyKhachHangView = new QuanLyNguoiDungView();

                new QuanLyNguoiDungController(mainView, quanLyKhachHangView, danhSachKhachHang);

                ThongBaoUtil.thongTin("Thành công", "Cập nhập người dùng thành công!");
                mainView.setContent(quanLyKhachHangView);

            } catch (SQLException e) {
                ThongBaoUtil.baoLoi("Lỗi", e.getMessage());
                e.printStackTrace();
            }
        });

        chinhSuaNguoiDungView.getBtnHuy().setOnAction(_ -> {
            List<NguoiDungModel> danhSachSanPham = new ArrayList<>();
            QuanLyNguoiDungView quanLySanPhamView = new QuanLyNguoiDungView();

            new QuanLyNguoiDungController(mainView, quanLySanPhamView, danhSachSanPham);
            mainView.setContent(quanLySanPhamView);
        });
    }
}
