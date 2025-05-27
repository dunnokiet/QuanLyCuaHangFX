package controller.NguoiDung;

import util.ThongBaoUtil;
import view.MainView;
import view.NguoiDung.QuanLyNguoiDungView;
import view.NguoiDung.TaoNguoiDungView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.NguoiDungDAO;
import model.NguoiDungModel;

public class TaoNguoiDungController {
    private TaoNguoiDungView taoNguoiDungView;
    private MainView mainView;

    private NguoiDungDAO nguoiDungDAO;

    public TaoNguoiDungController(MainView mainView, TaoNguoiDungView taoNguoiDungView) {
        this.mainView = mainView;
        this.taoNguoiDungView = taoNguoiDungView;

        nguoiDungDAO = new NguoiDungDAO();

        initListeners();
    }

    private void initListeners() {
        taoNguoiDungView.getBtnTao().setOnAction(_ -> {
            try {

                String maNguoiDung = taoNguoiDungView.getTxtMaNguoiDung().getText();
                String tenNguoiDung = taoNguoiDungView.getTxtTenNguoiDung().getText();
                String vaiTro = taoNguoiDungView.getRVaiTro();
                String matKhau = taoNguoiDungView.getTxtMatKhau().getText();

                if (tenNguoiDung == null || tenNguoiDung.trim().isEmpty() ||
                        vaiTro == null || vaiTro.trim().isEmpty() ||
                        matKhau == null || matKhau.trim().isEmpty() || maNguoiDung == null || maNguoiDung.isEmpty()) {
                    ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng điền đầy đủ thông tin hợp lệ!");
                    return;
                }

                for (NguoiDungModel nd : nguoiDungDAO.layTatCaNguoiDung()) {
                    if (nd.getMaNguoiDung().equals(maNguoiDung)) {
                        ThongBaoUtil.canhBao("Cảnh báo", "Mã khách hàng đã tồn tại!");
                        return;
                    }
                }

                NguoiDungModel khachHangModel = new NguoiDungModel(maNguoiDung, tenNguoiDung, matKhau, vaiTro);

                if (!khachHangModel.isThongTinHopLe()) {
                    ThongBaoUtil.canhBao("Cảnh báo", "Thông tin khách hàng không hợp lệ!");
                    return;
                }

                nguoiDungDAO.them(khachHangModel);

                List<NguoiDungModel> danhSachNguoiDung = new ArrayList<>();
                QuanLyNguoiDungView quanLyNguoiDungView = new QuanLyNguoiDungView();
                new QuanLyNguoiDungController(mainView, quanLyNguoiDungView, danhSachNguoiDung);

                ThongBaoUtil.thongTin("Thành công", "Tạo khách hàng thành công!");
                mainView.setContent(quanLyNguoiDungView);

            } catch (SQLException e) {
                ThongBaoUtil.baoLoi("Lỗi", e.getMessage());
                e.printStackTrace();
            }
        });

        taoNguoiDungView.getBtnHuy().setOnAction(_ -> {
            List<NguoiDungModel> danhSachSanPham = new ArrayList<>();
            QuanLyNguoiDungView quanLySanPhamView = new QuanLyNguoiDungView();

            new QuanLyNguoiDungController(mainView, quanLySanPhamView, danhSachSanPham);
            mainView.setContent(quanLySanPhamView);
        });
    }

}