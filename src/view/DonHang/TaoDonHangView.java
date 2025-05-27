package view.DonHang;

import dto.ChiTietDonHangDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.SanPhamModel;

import java.util.ArrayList;
import java.util.List;

public class TaoDonHangView extends BorderPane {
    private TextField txtMaDonHang;
    private ComboBox<String> cbKhachHang;
    private TextField txtNgayDat;
    private ComboBox<String> cbTrangThai;
    private TextField txtTongTien;
    private TableView<SanPhamModel> tblSanPham;
    private TextField txtTimKiemSanPham;
    private Button btnThemSanPham;
    private Button btnBoSanPham;
    private Button btnSuaSoLuong;
    private TableView<ChiTietDonHangDTO> tblChiTietDonHang;
    private Button btnTao;
    private Button btnHuy;

    public TaoDonHangView() {
        setPadding(new Insets(24));
        setStyle("-fx-background-color: #ffffff;");

        // Left: Bảng sản phẩm với bộ lọc
        VBox leftPane = new VBox(16);
        leftPane.setPrefWidth(600);

        Label lblTitleSanPham = new Label("Danh Sách Sản Phẩm");
        lblTitleSanPham.getStyleClass().add("title-1");

        txtTimKiemSanPham = new TextField();
        txtTimKiemSanPham.setPromptText("Tìm kiếm sản phẩm...");
        HBox.setHgrow(txtTimKiemSanPham, Priority.ALWAYS);

        HBox headerSanPham = new HBox(10, txtTimKiemSanPham);
        headerSanPham.setPadding(new Insets(0, 0, 10, 0));

        tblSanPham = new TableView<>();
        tblSanPham.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        leftPane.setVgrow(tblSanPham, Priority.ALWAYS);

        TableColumn<SanPhamModel, String> colMaSanPham = new TableColumn<>("Mã sản phẩm");
        colMaSanPham.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));

        TableColumn<SanPhamModel, String> colTenSanPham = new TableColumn<>("Tên sản phẩm");
        colTenSanPham.setCellValueFactory(new PropertyValueFactory<>("ten"));

        TableColumn<SanPhamModel, Integer> colSoLuongTon = new TableColumn<>("Tồn kho");
        colSoLuongTon.setCellValueFactory(new PropertyValueFactory<>("soLuongTon"));

        TableColumn<SanPhamModel, Double> colGia = new TableColumn<>("Giá");
        colGia.setCellValueFactory(new PropertyValueFactory<>("gia"));

        tblSanPham.getColumns().addAll(colMaSanPham, colTenSanPham, colSoLuongTon, colGia);

        btnThemSanPham = new Button("Thêm vào đơn hàng");
        btnThemSanPham.getStyleClass().add("success");
        btnThemSanPham.setDisable(true);

        btnBoSanPham = new Button("Bỏ sản phẩm");
        btnBoSanPham.getStyleClass().add("danger");
        btnBoSanPham.setDisable(true);

        btnSuaSoLuong = new Button("Sửa số lượng");
        btnSuaSoLuong.setDisable(true);

        HBox actionButtons = new HBox(10, btnThemSanPham, btnBoSanPham, btnSuaSoLuong);
        actionButtons.setAlignment(Pos.CENTER);

        leftPane.getChildren().addAll(lblTitleSanPham, headerSanPham, tblSanPham, actionButtons);

        // Right: Chi tiết hóa đơn
        VBox rightPane = new VBox(10);
        rightPane.setPrefWidth(400);
        rightPane.setStyle("-fx-background-color: #f5f5f5; -fx-padding: 20;");

        Label lblTitle = new Label("Chi Tiết Hóa Đơn");
        lblTitle.getStyleClass().add("title-1");

        Label lblMaDonHang = new Label("Mã đơn hàng");
        txtMaDonHang = new TextField();
        txtMaDonHang.setPromptText("Mã đơn hàng");
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
        cbTrangThai.setValue("Chờ xử lý");
        VBox trangThaiBox = new VBox(4, lblTrangThai, cbTrangThai);

        Label lblTongTien = new Label("Tổng tiền");
        txtTongTien = new TextField();
        txtTongTien.setPromptText("Tổng tiền");
        txtTongTien.setDisable(true);
        VBox tongTienBox = new VBox(4, lblTongTien, txtTongTien);

        tblChiTietDonHang = new TableView<>();
        tblChiTietDonHang.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        rightPane.setVgrow(tblChiTietDonHang, Priority.ALWAYS);

        TableColumn<ChiTietDonHangDTO, String> colChiTietTenSanPham = new TableColumn<>("Tên sản phẩm");
        colChiTietTenSanPham.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));

        TableColumn<ChiTietDonHangDTO, Integer> colChiTietSoLuong = new TableColumn<>("Số lượng");
        colChiTietSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));

        TableColumn<ChiTietDonHangDTO, Double> colChiTietDonGia = new TableColumn<>("Đơn giá");
        colChiTietDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));

        tblChiTietDonHang.getColumns().addAll(colChiTietTenSanPham, colChiTietSoLuong, colChiTietDonGia);

        btnTao = new Button("Tạo");
        btnTao.getStyleClass().add("success");
        btnHuy = new Button("Hủy");
        btnHuy.getStyleClass().add("danger");
        HBox buttonBox = new HBox(8, btnHuy, btnTao);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        rightPane.getChildren().addAll(lblTitle, maDonHangBox, khachHangBox, ngayDatBox, trangThaiBox, tongTienBox,
                tblChiTietDonHang, buttonBox);

        setLeft(leftPane);
        setRight(rightPane);

        tblSanPham.getSelectionModel().selectedItemProperty().addListener((_, _, newSelection) -> {
            btnThemSanPham.setDisable(newSelection == null);
        });

        tblChiTietDonHang.getSelectionModel().selectedItemProperty().addListener((_, _, newSelection) -> {
            btnBoSanPham.setDisable(newSelection == null);
            btnSuaSoLuong.setDisable(newSelection == null);
        });
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

    public TableView<SanPhamModel> getTblSanPham() {
        return tblSanPham;
    }

    public TextField getTxtTimKiemSanPham() {
        return txtTimKiemSanPham;
    }

    public Button getBtnThemSanPham() {
        return btnThemSanPham;
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

    public Button getBtnTao() {
        return btnTao;
    }

    public Button getBtnHuy() {
        return btnHuy;
    }

    public List<ChiTietDonHangDTO> getChiTietDonHangList() {
        return new ArrayList<>(tblChiTietDonHang.getItems());
    }
}