package view.DonHang;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.SanPhamModel;

import java.text.DecimalFormat;
import java.util.Arrays;

public class TaoDonHangView extends VBox {
    private TableView<SanPhamModel> tblSanPham;
    private TextField txtTimKiemSanPham;
    private Button btnThemSanPham;
    private Button btnBoSanPham;
    private Button btnSuaSoLuong;

    public TaoDonHangView() {
        setPadding(new Insets(24));
        setSpacing(16);

        Label lblTitleSanPham = new Label("Tạo Đơn Hàng Mới");
        lblTitleSanPham.getStyleClass().add("title-1");

        txtTimKiemSanPham = new TextField();
        txtTimKiemSanPham.setPromptText("Tìm kiếm đơn hàng...");
        HBox.setHgrow(txtTimKiemSanPham, Priority.ALWAYS);

        HBox headerSanPham = new HBox(10, txtTimKiemSanPham);
        headerSanPham.setPadding(new Insets(0, 0, 10, 0));

        tblSanPham = new TableView<>();
        tblSanPham.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        VBox.setVgrow(tblSanPham, Priority.ALWAYS);

        TableColumn<SanPhamModel, String> colMaSanPham = new TableColumn<>("Mã sản phẩm");
        colMaSanPham.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));

        TableColumn<SanPhamModel, String> colTenSanPham = new TableColumn<>("Tên sản phẩm");
        colTenSanPham.setCellValueFactory(new PropertyValueFactory<>("ten"));

        TableColumn<SanPhamModel, Integer> colSoLuongTon = new TableColumn<>("Tồn kho");
        colSoLuongTon.setCellValueFactory(new PropertyValueFactory<>("soLuongTon"));

        TableColumn<SanPhamModel, String> colGia = new TableColumn<>("Giá");
        colGia.setCellValueFactory(cellData -> {
            double donGia = cellData.getValue().getGia();
            return javafx.beans.binding.Bindings
                    .createStringBinding(() -> new DecimalFormat("#,###").format(donGia));
        });

        tblSanPham.getColumns().addAll(Arrays.asList(colMaSanPham, colTenSanPham, colSoLuongTon, colGia));

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

        getChildren().addAll(lblTitleSanPham, headerSanPham, tblSanPham, actionButtons);
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
}