package view.NguoiDung;

import java.util.Arrays;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.NguoiDungModel;

public class QuanLyNguoiDungView extends VBox {
    private TextField txtTimKiem;
    private TableView<NguoiDungModel> tblNguoiDung;
    private Button btnTaoNguoiDung;
    private Button btnSuaNguoiDung;
    private Button btnXoaNguoiDung;

    public QuanLyNguoiDungView() {
        setSpacing(16);
        setPadding(new Insets(24));

        Label lblTitle = new Label("Quản Lý Người Dùng");
        lblTitle.getStyleClass().add("title-1");

        txtTimKiem = new TextField();
        txtTimKiem.setPromptText("Tìm kiếm người dùng...");
        HBox.setHgrow(txtTimKiem, Priority.ALWAYS);

        btnTaoNguoiDung = new Button("Tạo người dùng");
        btnTaoNguoiDung.getStyleClass().add("success");
        btnSuaNguoiDung = new Button("Sửa");
        btnSuaNguoiDung.setDisable(true);
        btnXoaNguoiDung = new Button("Xóa");
        btnXoaNguoiDung.getStyleClass().add("danger");
        btnXoaNguoiDung.setDisable(true);

        HBox header = new HBox(10, txtTimKiem, btnTaoNguoiDung, btnSuaNguoiDung, btnXoaNguoiDung);
        header.setPadding(new Insets(0, 0, 10, 0));

        tblNguoiDung = new TableView<>();
        tblNguoiDung.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        setVgrow(tblNguoiDung, Priority.ALWAYS);

        TableColumn<NguoiDungModel, String> colNguoiDung = new TableColumn<>("Mã người dùng");
        colNguoiDung.setCellValueFactory(new PropertyValueFactory<>("maNguoiDung"));

        TableColumn<NguoiDungModel, String> colTenNguoiDung = new TableColumn<>("Tên đăng nhập");
        colTenNguoiDung.setCellValueFactory(new PropertyValueFactory<>("tenDangNhap"));

        TableColumn<NguoiDungModel, String> colVaiTro = new TableColumn<>("Vai trò");
        colVaiTro.setCellValueFactory(new PropertyValueFactory<>("vaiTro"));

        tblNguoiDung.getColumns()
                .addAll(Arrays.asList(colNguoiDung, colTenNguoiDung, colVaiTro));

        tblNguoiDung.getSelectionModel().selectedItemProperty().addListener((_, _, newSelection) -> {
            boolean hasSelection = newSelection != null;
            btnSuaNguoiDung.setDisable(!hasSelection);
            btnXoaNguoiDung.setDisable(!hasSelection);
        });

        getChildren().addAll(lblTitle, header, tblNguoiDung);
    }

    public TextField getTxtTimKiem() {
        return txtTimKiem;
    }

    public TableView<NguoiDungModel> getTblNguoiDung() {
        return tblNguoiDung;
    }

    public Button getBtnTaoNguoiDung() {
        return btnTaoNguoiDung;
    }

    public Button getBtnSuaNguoiDung() {
        return btnSuaNguoiDung;
    }

    public Button getBtnXoaNguoiDung() {
        return btnXoaNguoiDung;
    }

}