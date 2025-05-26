package view.sanpham;

import java.util.Arrays;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.SanPhamModel;

public class QuanLySanPhamView extends VBox {
    private TextField txtTimKiem;
    private TableView<SanPhamModel> tblSanPham;
    private Label lblThongBao;
    private Button btnTaoSanPham;
    private Button btnSuaSanPham;
    private Button btnXoaSanPham;

    public QuanLySanPhamView() {
        super(16);

        Label lblTitle = new Label("Danh sách sản phẩm");
        lblTitle.setStyle("-fx-text-fill: #000;");

        setPadding(new Insets(24));
        setStyle("-fx-background-color: #ffffff;");

        HBox header = new HBox(10);
        txtTimKiem = new TextField();
        txtTimKiem.setPromptText("Tìm kiếm sản phẩm...");
        HBox.setHgrow(txtTimKiem, Priority.ALWAYS);

        btnTaoSanPham = new Button("Tạo sản phẩm");
        btnTaoSanPham.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold");

        btnSuaSanPham = new Button("Sửa");
        btnSuaSanPham.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold");
        btnSuaSanPham.setDisable(true);

        btnXoaSanPham = new Button("Xóa");
        btnXoaSanPham.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-font-weight: bold");
        btnXoaSanPham.setDisable(true);

        header.getChildren().addAll(txtTimKiem, btnTaoSanPham, btnSuaSanPham, btnXoaSanPham);
        header.setPadding(new Insets(0, 0, 10, 0));

        tblSanPham = new TableView<>();
        tblSanPham.setStyle("-fx-border-color: #e0e0e0;");
        tblSanPham.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        setVgrow(tblSanPham, Priority.ALWAYS);

        TableColumn<SanPhamModel, String> colMaSanPham = new TableColumn<>("Mã sản phẩm");
        colMaSanPham.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));

        TableColumn<SanPhamModel, String> colTenSanPham = new TableColumn<>("Tên sản phẩm");
        colTenSanPham.setCellValueFactory(new PropertyValueFactory<>("ten"));

        TableColumn<SanPhamModel, String> colLoai = new TableColumn<>("Loại");
        colLoai.setCellValueFactory(new PropertyValueFactory<>("loai"));

        TableColumn<SanPhamModel, Double> colGia = new TableColumn<>("Giá");
        colGia.setCellValueFactory(new PropertyValueFactory<>("gia"));

        TableColumn<SanPhamModel, Integer> colSoLuongTon = new TableColumn<>("Số lượng tồn");
        colSoLuongTon.setCellValueFactory(new PropertyValueFactory<>("soLuongTon"));

        tblSanPham.getColumns()
                .addAll(Arrays.asList(colMaSanPham, colTenSanPham, colLoai, colGia, colSoLuongTon));

        tblSanPham.getSelectionModel().selectedItemProperty().addListener((_, _, newSelection) -> {
            boolean hasSelection = newSelection != null;
            btnSuaSanPham.setDisable(!hasSelection);
            btnXoaSanPham.setDisable(!hasSelection);
        });

        getChildren().addAll(lblTitle, header, tblSanPham);
    }

    public TextField getTxtTimKiem() {
        return txtTimKiem;
    }

    public TableView<SanPhamModel> getTblSanPham() {
        return tblSanPham;
    }

    public Label getLblThongBao() {
        return lblThongBao;
    }

    public Button getBtnTaoSanPham() {
        return btnTaoSanPham;
    }

    public Button getBtnSuaSanPham() {
        return btnSuaSanPham;
    }

    public Button getBtnXoaSanPham() {
        return btnXoaSanPham;
    }
}