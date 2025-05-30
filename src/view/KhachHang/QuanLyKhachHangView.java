package view.KhachHang;

import java.util.Arrays;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.KhachHangModel;

public class QuanLyKhachHangView extends VBox {
    private TextField txtTimKiem;
    private TableView<KhachHangModel> tblKhachHang;
    private Button btnTaoKhachHang;
    private Button btnSuaKhachHang;
    private Button btnXoaKhachHang;

    public QuanLyKhachHangView() {
        setSpacing(16);
        setPadding(new Insets(24));

        Label lblTitle = new Label("Quản Lý Khách Hàng");
        lblTitle.getStyleClass().add("title-1");

        txtTimKiem = new TextField();
        txtTimKiem.setPromptText("Tìm kiếm khách hàng...");
        HBox.setHgrow(txtTimKiem, Priority.ALWAYS);

        btnTaoKhachHang = new Button("Tạo khách hàng");
        btnTaoKhachHang.getStyleClass().add("success");
        btnSuaKhachHang = new Button("Sửa");
        btnSuaKhachHang.setDisable(true);
        btnXoaKhachHang = new Button("Xóa");
        btnXoaKhachHang.getStyleClass().add("danger");
        btnXoaKhachHang.setDisable(true);

        HBox header = new HBox(10, txtTimKiem, btnTaoKhachHang, btnSuaKhachHang, btnXoaKhachHang);
        header.setPadding(new Insets(0, 0, 10, 0));

        tblKhachHang = new TableView<>();
        tblKhachHang.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        setVgrow(tblKhachHang, Priority.ALWAYS);

        TableColumn<KhachHangModel, String> colMaKhachHang = new TableColumn<>("Mã khách hàng");
        colMaKhachHang.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));

        TableColumn<KhachHangModel, String> colTenKhachHang = new TableColumn<>("Tên khách hàng");
        colTenKhachHang.setCellValueFactory(new PropertyValueFactory<>("ten"));

        TableColumn<KhachHangModel, String> colSoDienThoai = new TableColumn<>("Số điện thoại");
        colSoDienThoai.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));

        TableColumn<KhachHangModel, String> colDiaChi = new TableColumn<>("Địa chỉ");
        colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));

        tblKhachHang.getColumns()
                .addAll(Arrays.asList(colMaKhachHang, colTenKhachHang, colSoDienThoai, colDiaChi));

     

        getChildren().addAll(lblTitle, header, tblKhachHang);
    }

    public TextField getTxtTimKiem() {
        return txtTimKiem;
    }

    public TableView<KhachHangModel> getTblKhachHang() {
        return tblKhachHang;
    }

    public Button getBtnTaoKhachHang() {
        return btnTaoKhachHang;
    }

    public Button getBtnSuaKhachHang() {
        return btnSuaKhachHang;
    }

    public Button getBtnXoaKhachHang() {
        return btnXoaKhachHang;
    }
}