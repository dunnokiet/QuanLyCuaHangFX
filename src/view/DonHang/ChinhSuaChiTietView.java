package view.DonHang;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dto.ChiTietDonHangDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ChinhSuaChiTietView extends VBox {
    private TextField txtMaDonHang;
    private ComboBox<String> cbKhachHang;
    private TextField txtNgayDat;
    private ComboBox<String> cbTrangThai;
    private TextField txtTongTien;
    private Button btnBoSanPham;
    private Button btnSuaSoLuong;
    private TableView<ChiTietDonHangDTO> tblChiTietDonHang;
    private Button btnLuu;
    private Button btnHuy;

    public ChinhSuaChiTietView() {
        setPrefWidth(400);
        setSpacing(16);
        setPadding(new Insets(24));

        Label lblTitle = new Label("Chi Tiết Hóa Đơn");
        lblTitle.getStyleClass().add("title-1");

        Label lblMaDonHang = new Label("Mã đơn hàng");
        txtMaDonHang = new TextField();
        txtMaDonHang.setPromptText("Mã đơn hàng");
        txtMaDonHang.setDisable(true);
        VBox maDonHangBox = new VBox(4, lblMaDonHang, txtMaDonHang);

        Label lblKhachHang = new Label("Khách hàng");
        cbKhachHang = new ComboBox<>();
        cbKhachHang.setPromptText("Chọn khách hàng");
        VBox khachHangBox = new VBox(4, lblKhachHang, cbKhachHang);

        Label lblNgayDat = new Label("Ngày đặt (YYYY-MM-DD)");
        txtNgayDat = new TextField();
        txtNgayDat.setPromptText("Ngày đặt (YYYY-MM-DD)");
        VBox ngayDatBox = new VBox(4, lblNgayDat, txtNgayDat);

        Label lblTrangThai = new Label("Trạng thái");
        cbTrangThai = new ComboBox<>();
        cbTrangThai.getItems().addAll("Chờ xử lý", "Đã giao", "Hủy");
        VBox trangThaiBox = new VBox(4, lblTrangThai, cbTrangThai);

        Label lblTongTien = new Label("Tổng tiền");
        txtTongTien = new TextField();
        txtTongTien.getStyleClass().add("title-3");
        txtTongTien.setPromptText("Tổng tiền");
        txtTongTien.setDisable(true);
        VBox tongTienBox = new VBox(4, lblTongTien, txtTongTien);

        tblChiTietDonHang = new TableView<>();
        tblChiTietDonHang.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        VBox.setVgrow(tblChiTietDonHang, Priority.ALWAYS);

        TableColumn<ChiTietDonHangDTO, String> colChiTietTenSanPham = new TableColumn<>("Tên sản phẩm");
        colChiTietTenSanPham.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));

        TableColumn<ChiTietDonHangDTO, Integer> colChiTietSoLuong = new TableColumn<>("Số lượng");
        colChiTietSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));

        TableColumn<ChiTietDonHangDTO, String> colDonGia = new TableColumn<>("Đơn giá");
        colDonGia.setCellValueFactory(cellData -> {
            double donGia = cellData.getValue().getDonGia();
            return javafx.beans.binding.Bindings
                    .createStringBinding(() -> new DecimalFormat("#,###").format(donGia));
        });

        tblChiTietDonHang.getColumns().addAll(Arrays.asList(colChiTietTenSanPham, colChiTietSoLuong, colDonGia));

        btnLuu = new Button("Lưu");
        btnLuu.getStyleClass().add("success");
        btnHuy = new Button("Hủy");
        btnHuy.getStyleClass().add("danger");
        HBox buttonBox = new HBox(8, btnHuy, btnLuu);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        getChildren().addAll(lblTitle, maDonHangBox, khachHangBox, ngayDatBox, trangThaiBox, tongTienBox,
                tblChiTietDonHang, buttonBox);
    }

    public TextField getTxtMaDonHang() {
        return txtMaDonHang;
    }

    public ComboBox<String> getCbKhachHang() {
        return cbKhachHang;
    }

    public TextField getTxtNgayDat() {
        return txtNgayDat;
    }

    public ComboBox<String> getCbTrangThai() {
        return cbTrangThai;
    }

    public TextField getTxtTongTien() {
        return txtTongTien;
    }

    public Button getBtnBoSanPham() {
        return btnBoSanPham;
    }

    public Button getBtnSuaSoLuong() {
        return btnSuaSoLuong;
    }

    public TableView<ChiTietDonHangDTO> getTblChiTietDonHang() {
        return tblChiTietDonHang;
    }

    public Button getBtnLuu() {
        return btnLuu;
    }

    public Button getBtnHuy() {
        return btnHuy;
    }

    public List<ChiTietDonHangDTO> getChiTietDonHangList() {
        return new ArrayList<>(tblChiTietDonHang.getItems());
    }
}