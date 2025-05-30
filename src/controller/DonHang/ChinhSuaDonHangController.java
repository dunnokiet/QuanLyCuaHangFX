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
import view.DonHang.ChinhSuaChiTietView;
import view.DonHang.ChinhSuaDonHangView;
import view.DonHang.QuanLyDonHangView;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChinhSuaDonHangController {
    private ChinhSuaDonHangView chinhSuaDonHangView;
    private ChinhSuaChiTietView chinhSuaChiTietView;
    private MainView mainView;
    private DonHangDTO donHangDTO;
    private DonHangDAO donHangDAO;
    private ChiTietDonHangDAO chiTietDonHangDAO;
    private SanPhamDAO sanPhamDAO;
    private KhachHangDAO khachHangDAO;
    private List<SanPhamModel> danhSachSanPham;
    private List<KhachHangModel> danhSachKhachHang;
    private Map<String, Integer> originalSoLuong;

    public ChinhSuaDonHangController(MainView mainView, ChinhSuaDonHangView chinhSuaDonHangView,
            ChinhSuaChiTietView chinhSuaChiTietView,
            DonHangDTO donHangDTO) {
        this.mainView = mainView;
        this.chinhSuaDonHangView = chinhSuaDonHangView;
        this.chinhSuaChiTietView = chinhSuaChiTietView;
        this.donHangDTO = donHangDTO;
        this.donHangDAO = new DonHangDAO();
        this.chiTietDonHangDAO = new ChiTietDonHangDAO();
        this.sanPhamDAO = new SanPhamDAO();
        this.khachHangDAO = new KhachHangDAO();
        this.danhSachSanPham = new ArrayList<>();
        this.danhSachKhachHang = new ArrayList<>();
        this.originalSoLuong = new HashMap<>();

        loadSanPham();
        loadKhachHang();
        loadChiTietDonHang();
        initListeners();
    }

    private void loadSanPham() {
        try {
            danhSachSanPham = sanPhamDAO.layTatCaSanPham();
            chinhSuaDonHangView.getTblSanPham().getItems().addAll(danhSachSanPham);
        } catch (SQLException e) {
            ThongBaoUtil.baoLoi("Lỗi", "Không thể tải danh sách sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadKhachHang() {
        try {
            danhSachKhachHang = khachHangDAO.layTatCaKhachHang();
            chinhSuaChiTietView.getCbKhachHang().getItems().addAll(
                    danhSachKhachHang.stream()
                            .map(kh -> kh.getMaKhachHang() + " - " + kh.getTen())
                            .collect(Collectors.toList()));
        } catch (SQLException e) {
            ThongBaoUtil.baoLoi("Lỗi", "Không thể tải danh sách khách hàng: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initListeners() {
        chinhSuaChiTietView.getTblChiTietDonHang().getSelectionModel().selectedItemProperty()
                .addListener((_, _, newSelection) -> {
                    chinhSuaDonHangView.getBtnBoSanPham().setDisable(newSelection == null);
                    chinhSuaDonHangView.getBtnSuaSoLuong().setDisable(newSelection == null);
                });

        chinhSuaDonHangView.getTblSanPham().getSelectionModel().selectedItemProperty()
                .addListener((_, _, newSelection) -> {
                    chinhSuaDonHangView.getBtnThemSanPham().setDisable(newSelection == null);
                });

        chinhSuaDonHangView.getTxtTimKiemSanPham().textProperty().addListener((_, _, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                chinhSuaDonHangView.getTblSanPham().getItems().clear();
                chinhSuaDonHangView.getTblSanPham().getItems().addAll(danhSachSanPham);
            } else {
                String lowerKeyword = newValue.toLowerCase();
                List<SanPhamModel> filtered = danhSachSanPham.stream()
                        .filter(sp -> sp.getMaSanPham().toLowerCase().contains(lowerKeyword) ||
                                sp.getTen().toLowerCase().contains(lowerKeyword))
                        .collect(Collectors.toList());
                chinhSuaDonHangView.getTblSanPham().getItems().clear();
                chinhSuaDonHangView.getTblSanPham().getItems().addAll(filtered);
            }
        });

        chinhSuaDonHangView.getBtnThemSanPham().setOnAction(_ -> {
            SanPhamModel sanPham = chinhSuaDonHangView.getTblSanPham().getSelectionModel().getSelectedItem();
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

                int soLuongHienTai = chinhSuaChiTietView.getTblChiTietDonHang().getItems().stream()
                        .filter(ct -> ct.getTenSanPham().equals(sanPham.getTen()))
                        .mapToInt(ChiTietDonHangDTO::getSoLuong)
                        .sum();
                int soLuongTonHieuLuc = sanPham.getSoLuongTon()
                        + (originalSoLuong.getOrDefault(sanPham.getMaSanPham(), 0) - soLuongHienTai);
                if (soLuong > soLuongTonHieuLuc) {
                    ThongBaoUtil.canhBao("Cảnh báo",
                            "Số lượng tồn kho không đủ! Hiện tại chỉ có: " + soLuongTonHieuLuc);
                    return;
                }

                sanPham.setSoLuongTon(sanPham.getSoLuongTon() - soLuong);
                ChiTietDonHangDTO chiTiet = new ChiTietDonHangDTO(sanPham.getTen(), soLuong, sanPham.getGia());
                chinhSuaChiTietView.getTblChiTietDonHang().getItems().add(chiTiet);

                updateTongTien();
                chinhSuaDonHangView.getTblSanPham().refresh();
            });
        });

        chinhSuaDonHangView.getBtnBoSanPham().setOnAction(_ -> {
            ChiTietDonHangDTO selected = chinhSuaChiTietView.getTblChiTietDonHang().getSelectionModel()
                    .getSelectedItem();
            if (selected != null) {
                chinhSuaChiTietView.getTblChiTietDonHang().getItems().remove(selected);

                for (SanPhamModel sp : danhSachSanPham) {
                    if (sp.getTen().equals(selected.getTenSanPham())) {
                        sp.setSoLuongTon(sp.getSoLuongTon() + selected.getSoLuong());
                        break;
                    }
                }

                updateTongTien();
                chinhSuaDonHangView.getTblSanPham().refresh();
            }
        });

        chinhSuaDonHangView.getBtnSuaSoLuong().setOnAction(_ -> {
            ChiTietDonHangDTO selected = chinhSuaChiTietView.getTblChiTietDonHang().getSelectionModel()
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
                chinhSuaDonHangView.getTblSanPham().refresh();
                chinhSuaChiTietView.getTblChiTietDonHang().refresh();
            });
        });

        chinhSuaChiTietView.getBtnLuu().setOnAction(_ -> {
            String khachHangSelection = chinhSuaChiTietView.getCbKhachHang().getValue();
            String ngayDatStr = chinhSuaChiTietView.getTxtNgayDat().getText();
            String trangThai = chinhSuaChiTietView.getCbTrangThai().getValue();
            List<ChiTietDonHangDTO> chiTietList = chinhSuaChiTietView.getChiTietDonHangList();

            if (khachHangSelection == null || khachHangSelection.trim().isEmpty() ||
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
            Date ngayDat;
            try {
                ngayDat = sdf.parse(ngayDatStr);
            } catch (Exception e) {
                ThongBaoUtil.canhBao("Cảnh báo", "Ngày đặt phải có định dạng YYYY-MM-DD!");
                return;
            }

            double tongTien = chiTietList.stream().mapToDouble(ct -> ct.getSoLuong() * ct.getDonGia()).sum();

            donHangDTO.setMaKhachHang(maKhachHang);
            donHangDTO.setTenKhachHang(tenKhachHang);
            donHangDTO.setNgayDat(ngayDat);
            donHangDTO.setTrangThai(trangThai);
            donHangDTO.setTongTien(tongTien);

            try {
                donHangDAO.sua(donHangDTO);

                chiTietDonHangDAO.xoaTheoMaDonHang(donHangDTO.getMaDonHang());

                for (ChiTietDonHangDTO chiTiet : chiTietList) {
                    String maSanPham = null;
                    for (SanPhamModel sp : sanPhamDAO.layTatCaSanPham()) {
                        if (sp.getTen().equals(chiTiet.getTenSanPham())) {
                            maSanPham = sp.getMaSanPham();
                            sanPhamDAO.sua(sp);
                            break;
                        }
                    }
                    chiTietDonHangDAO.them(donHangDTO.getMaDonHang(), maSanPham, chiTiet.getSoLuong(),
                            chiTiet.getDonGia());
                }
                List<DonHangDTO> danhSachDonHang = new ArrayList<>();
                QuanLyDonHangView quanLyDonHangView = new QuanLyDonHangView();
                ChiTietDonHangView chiTietDonHangView = new ChiTietDonHangView();
                new QuanLyDonHangController(mainView, quanLyDonHangView, chiTietDonHangView, danhSachDonHang);

                ThongBaoUtil.thongTin("Thành công", "Cập nhật đơn hàng thành công!");
                mainView.setCenter(quanLyDonHangView);
                mainView.setRight(chiTietDonHangView);

            } catch (SQLException e) {
                ThongBaoUtil.baoLoi("Lỗi", e.getMessage());
                e.printStackTrace();
            }
        });

        chinhSuaChiTietView.getBtnHuy().setOnAction(_ -> {
            List<DonHangDTO> danhSachDonHang = new ArrayList<>();
            QuanLyDonHangView quanLyDonHangView = new QuanLyDonHangView();
            ChiTietDonHangView chiTietDonHangView = new ChiTietDonHangView();
            new QuanLyDonHangController(mainView, quanLyDonHangView, chiTietDonHangView, danhSachDonHang);

            mainView.setCenter(quanLyDonHangView);
            mainView.setRight(chiTietDonHangView);
        });
    }

    private void updateTongTien() {
        double tongTien = chinhSuaChiTietView.getTblChiTietDonHang().getItems().stream()
                .mapToDouble(ct -> ct.getSoLuong() * ct.getDonGia())
                .sum();
        chinhSuaChiTietView.getTxtTongTien().setText(new DecimalFormat("#,###").format(tongTien) + " VNĐ");
    }

    private void loadChiTietDonHang() {
        try {
            List<ChiTietDonHangDTO> chiTietList = chiTietDonHangDAO.layChiTietDonHangDTO(donHangDTO.getMaDonHang());
            chinhSuaChiTietView.getTblChiTietDonHang().getItems().addAll(chiTietList);
            double tongTien = chiTietList.stream().mapToDouble(ct -> ct.getSoLuong() * ct.getDonGia()).sum();
            chinhSuaChiTietView.getTxtTongTien().setText(new DecimalFormat("#,###").format(tongTien) + " VNĐ");

            for (ChiTietDonHangDTO chiTiet : chiTietList) {
                for (SanPhamModel sp : danhSachSanPham) {
                    if (sp.getTen().equals(chiTiet.getTenSanPham())) {
                        originalSoLuong.put(sp.getMaSanPham(), chiTiet.getSoLuong());
                        break;
                    }
                }
            }

            chinhSuaChiTietView.getTxtMaDonHang().setText(donHangDTO.getMaDonHang());
            chinhSuaChiTietView.getCbKhachHang()
                    .setValue(donHangDTO.getMaKhachHang() + " - " + donHangDTO.getTenKhachHang());
            chinhSuaChiTietView.getCbTrangThai().setValue(donHangDTO.getTrangThai());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            chinhSuaChiTietView.getTxtNgayDat().setText(sdf.format(donHangDTO.getNgayDat()));
        } catch (Exception e) {
            ThongBaoUtil.baoLoi("Lỗi", "Không thể tải chi tiết đơn hàng: " + e.getMessage());
            e.printStackTrace();
        }
    }
}