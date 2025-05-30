package controller.DonHang;

import dao.DonHangDAO;
import dao.KhachHangDAO;
import dao.ChiTietDonHangDAO;
import dao.SanPhamDAO;
import dto.ChiTietDonHangDTO;
import dto.DonHangDTO;
import javafx.scene.control.TextInputDialog;
import model.KhachHangModel;
import model.SanPhamModel;
import util.ThongBaoUtil;
import view.MainView;
import view.DonHang.ChiTietDonHangView;
import view.DonHang.TaoChiTietView;
import view.DonHang.QuanLyDonHangView;
import view.DonHang.TaoDonHangView;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaoDonHangController {
    private TaoDonHangView taoDonHangView;
    private TaoChiTietView taoChiTietView;
    private MainView mainView;
    private DonHangDAO donHangDAO;
    private ChiTietDonHangDAO chiTietDonHangDAO;
    private SanPhamDAO sanPhamDAO;
    private KhachHangDAO khachHangDAO;
    private List<SanPhamModel> danhSachSanPham;
    private List<KhachHangModel> danhSachKhachHang;

    public TaoDonHangController(MainView mainView, TaoDonHangView taoDonHangView,
            TaoChiTietView taoChiTietView) {
        this.mainView = mainView;

        this.taoDonHangView = taoDonHangView;
        this.taoChiTietView = taoChiTietView;

        this.donHangDAO = new DonHangDAO();
        this.chiTietDonHangDAO = new ChiTietDonHangDAO();
        this.sanPhamDAO = new SanPhamDAO();
        this.khachHangDAO = new KhachHangDAO();
        this.danhSachSanPham = new ArrayList<>();
        this.danhSachKhachHang = new ArrayList<>();

        loadSanPham();
        loadKhachHang();
        initListeners();
    }

    private void loadSanPham() {
        try {
            danhSachSanPham = sanPhamDAO.layTatCaSanPham();
            taoDonHangView.getTblSanPham().getItems().addAll(danhSachSanPham);
        } catch (SQLException e) {
            ThongBaoUtil.baoLoi("Lỗi", "Không thể tải danh sách sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadKhachHang() {
        try {
            danhSachKhachHang = khachHangDAO.layTatCaKhachHang();
            taoChiTietView.getCbKhachHang().getItems().addAll(
                    danhSachKhachHang.stream()
                            .map(kh -> kh.getMaKhachHang() + " - " + kh.getTen())
                            .collect(Collectors.toList()));
        } catch (SQLException e) {
            ThongBaoUtil.baoLoi("Lỗi", "Không thể tải danh sách khách hàng: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initListeners() {
        taoChiTietView.getTblChiTietDonHang().getSelectionModel().selectedItemProperty()
                .addListener((_, _, newSelection) -> {
                    taoDonHangView.getBtnBoSanPham().setDisable(newSelection == null);
                    taoDonHangView.getBtnSuaSoLuong().setDisable(newSelection == null);
                });

        taoDonHangView.getTblSanPham().getSelectionModel().selectedItemProperty()
                .addListener((_, _, newSelection) -> {
                    taoDonHangView.getBtnThemSanPham().setDisable(newSelection == null);
                });

        taoDonHangView.getTxtTimKiemSanPham().textProperty().addListener((_, _, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                taoDonHangView.getTblSanPham().getItems().clear();
                taoDonHangView.getTblSanPham().getItems().addAll(danhSachSanPham);
            } else {
                String lowerKeyword = newValue.toLowerCase();
                List<SanPhamModel> filtered = danhSachSanPham.stream()
                        .filter(sp -> sp.getMaSanPham().toLowerCase().contains(lowerKeyword) ||
                                sp.getTen().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
                taoDonHangView.getTblSanPham().getItems().clear();
                taoDonHangView.getTblSanPham().getItems().addAll(filtered);
            }
        });

        taoDonHangView.getBtnThemSanPham().setOnAction(_ -> {
            SanPhamModel sanPham = taoDonHangView.getTblSanPham().getSelectionModel().getSelectedItem();
            if (sanPham == null)
                return;

            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setTitle("Nhập số lượng");
            dialog.setHeaderText("Nhập số lượng cho sản phẩm: " + sanPham.getTen());
            dialog.setContentText("Số lượng:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(soLuongStr -> {
                int soLuong;
                try {
                    soLuong = Integer.parseInt(soLuongStr);
                    if (soLuong <= 0) {
                        ThongBaoUtil.canhBao("Cảnh báo", "Số lượng phải lớn hơn 0!");
                        return;
                    }
                } catch (NumberFormatException e) {
                    ThongBaoUtil.canhBao("Cảnh báo", "Số lượng phải là số hợp lệ!");
                    return;
                }

                if (soLuong > sanPham.getSoLuongTon()) {
                    ThongBaoUtil.canhBao("Cảnh báo",
                            "Số lượng tồn kho không đủ! Hiện tại chỉ có: " + sanPham.getSoLuongTon());
                    return;
                }

                sanPham.setSoLuongTon(sanPham.getSoLuongTon() - soLuong);
                ChiTietDonHangDTO chiTiet = new ChiTietDonHangDTO(sanPham.getTen(), soLuong, sanPham.getGia());
                taoChiTietView.getTblChiTietDonHang().getItems().add(chiTiet);

                updateTongTien();
                taoDonHangView.getTblSanPham().refresh();
            });
        });

        taoDonHangView.getBtnBoSanPham().setOnAction(_ -> {
            ChiTietDonHangDTO selected = taoChiTietView.getTblChiTietDonHang().getSelectionModel()
                    .getSelectedItem();
            if (selected != null) {
                taoChiTietView.getTblChiTietDonHang().getItems().remove(selected);

                for (SanPhamModel sp : danhSachSanPham) {
                    if (sp.getTen().equals(selected.getTenSanPham())) {
                        sp.setSoLuongTon(sp.getSoLuongTon() + selected.getSoLuong());
                        break;
                    }
                }

                updateTongTien();
                taoDonHangView.getTblSanPham().refresh();
            }
        });

        taoDonHangView.getBtnSuaSoLuong().setOnAction(_ -> {
            ChiTietDonHangDTO selected = taoChiTietView.getTblChiTietDonHang().getSelectionModel()
                    .getSelectedItem();
            if (selected == null)
                return;

            TextInputDialog dialog = new TextInputDialog(String.valueOf(selected.getSoLuong()));
            dialog.setTitle("Sửa số lượng");
            dialog.setHeaderText("Sửa số lượng cho sản phẩm: " + selected.getTenSanPham());
            dialog.setContentText("Số lượng:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(soLuongStr -> {
                int soLuongMoi;
                try {
                    soLuongMoi = Integer.parseInt(soLuongStr);
                    if (soLuongMoi <= 0) {
                        ThongBaoUtil.canhBao("Cảnh báo", "Số lượng phải lớn hơn 0!");
                        return;
                    }
                } catch (NumberFormatException e) {
                    ThongBaoUtil.canhBao("Cảnh báo", "Số lượng phải là số hợp lệ!");
                    return;
                }

                SanPhamModel sanPham = null;
                for (SanPhamModel sp : danhSachSanPham) {
                    if (sp.getTen().equals(selected.getTenSanPham())) {
                        sanPham = sp;
                        break;
                    }
                }

                if (sanPham == null)
                    return;

                int soLuongHienTai = sanPham.getSoLuongTon() + selected.getSoLuong();
                if (soLuongMoi > soLuongHienTai) {
                    ThongBaoUtil.canhBao("Cảnh báo", "Số lượng tồn kho không đủ! Hiện tại chỉ có: " + soLuongHienTai);
                    return;
                }

                sanPham.setSoLuongTon(soLuongHienTai - soLuongMoi);
                selected.setSoLuong(soLuongMoi);

                updateTongTien();
                taoDonHangView.getTblSanPham().refresh();
                taoChiTietView.getTblChiTietDonHang().refresh();
            });
        });

        taoChiTietView.getBtnTao().setOnAction(_ -> {
            try {
                String maDonHang = taoChiTietView.getTxtMaDonHang().getText();
                String khachHangSelection = taoChiTietView.getCbKhachHang().getValue();
                String ngayDatStr = taoChiTietView.getTxtNgayDat().getText();
                String trangThai = taoChiTietView.getCbTrangThai().getValue();
                List<ChiTietDonHangDTO> chiTietList = taoChiTietView.getChiTietDonHangList();

                if (maDonHang == null || maDonHang.trim().isEmpty() ||
                        khachHangSelection == null || khachHangSelection.trim().isEmpty() ||
                        ngayDatStr == null || ngayDatStr.trim().isEmpty() ||
                        trangThai == null) {
                    ThongBaoUtil.canhBao("Cảnh báo", "Vui lòng điền đầy đủ thông tin đơn hàng!");
                    return;
                }

                if (chiTietList.isEmpty()) {
                    ThongBaoUtil.canhBao("Cảnh báo", "Đơn hàng phải có ít nhất một sản phẩm!");
                    return;
                }

                String[] khachHangParts = khachHangSelection.split(" - ");
                String maKhachHang = khachHangParts[0];
                String tenKhachHang = khachHangParts[1];

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date ngayDat = sdf.parse(ngayDatStr);

                double tongTien = chiTietList.stream().mapToDouble(ct -> ct.getSoLuong() * ct.getDonGia()).sum();

                if (!ngayDatStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    ThongBaoUtil.canhBao("Cảnh báo", "Ngày đặt phải có định dạng YYYY-MM-DD!");
                    return;
                }

                for (DonHangDTO dh : donHangDAO.layTatCaDonGanNhat()) {
                    if (dh.getMaDonHang().equals(maDonHang)) {
                        ThongBaoUtil.canhBao("Cảnh báo", "Mã đơn hàng đã tồn tại!");
                        return;
                    }
                }

                DonHangDTO donHangDTO = new DonHangDTO(maDonHang, maKhachHang, tenKhachHang, tongTien, ngayDat,
                        trangThai);
                donHangDAO.them(donHangDTO);

                for (ChiTietDonHangDTO chiTiet : chiTietList) {
                    String maSanPham = null;
                    for (SanPhamModel sp : sanPhamDAO.layTatCaSanPham()) {
                        if (sp.getTen().equals(chiTiet.getTenSanPham())) {
                            maSanPham = sp.getMaSanPham();
                            sanPhamDAO.sua(sp);
                            break;
                        }
                    }
                    chiTietDonHangDAO.them(maDonHang, maSanPham, chiTiet.getSoLuong(), chiTiet.getDonGia());
                }

                List<DonHangDTO> danhSachDonHang = new ArrayList<>();
                QuanLyDonHangView quanLyDonHangView = new QuanLyDonHangView();
                ChiTietDonHangView chiTietDonHangView = new ChiTietDonHangView();
                new QuanLyDonHangController(mainView, quanLyDonHangView, chiTietDonHangView, danhSachDonHang);

                ThongBaoUtil.thongTin("Thành công", "Tạo đơn hàng thành công!");
                mainView.setCenter(quanLyDonHangView);
                mainView.setRight(chiTietDonHangView);
            } catch (Exception e) {
                ThongBaoUtil.baoLoi("Lỗi", e.getMessage());
                e.printStackTrace();
            }
        });

        taoChiTietView.getBtnHuy().setOnAction(_ -> {
            List<DonHangDTO> danhSachDonHang = new ArrayList<>();
            QuanLyDonHangView quanLyDonHangView = new QuanLyDonHangView();
            ChiTietDonHangView chiTietDonHangView = new ChiTietDonHangView();
            new QuanLyDonHangController(mainView, quanLyDonHangView, chiTietDonHangView, danhSachDonHang);

            mainView.setCenter(quanLyDonHangView);
            mainView.setRight(chiTietDonHangView);
        });
    }

    private void updateTongTien() {
        double tongTien = taoChiTietView.getTblChiTietDonHang().getItems().stream()
                .mapToDouble(ct -> ct.getSoLuong() * ct.getDonGia())
                .sum();
        taoChiTietView.getTxtTongTien().setText(new DecimalFormat("#,###").format(tongTien) + " VNĐ");
    }
}