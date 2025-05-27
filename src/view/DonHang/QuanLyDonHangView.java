package view.DonHang;

import java.text.DecimalFormat;
import java.util.Arrays;

import dto.DonHangDTO;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class QuanLyDonHangView extends VBox {
    private TextField txtTimKiem;
    private TableView<DonHangDTO> tblDonHang;
    private Button btnTaoDonHang;

    public QuanLyDonHangView() {
        setSpacing(16);
        setPadding(new Insets(24));

        Label lblTitle = new Label("Quản Lý Đơn Hàng");
        lblTitle.getStyleClass().add("title-1");

        txtTimKiem = new TextField();
        txtTimKiem.setPromptText("Tìm kiếm đơn hàng...");
        HBox.setHgrow(txtTimKiem, Priority.ALWAYS);

        btnTaoDonHang = new Button("Tạo đơn hàng");
        btnTaoDonHang.getStyleClass().add("success");

        HBox header = new HBox(10, txtTimKiem, btnTaoDonHang);
        header.setPadding(new Insets(0, 0, 10, 0));

        tblDonHang = new TableView<>();
        tblDonHang.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        VBox.setVgrow(tblDonHang, Priority.ALWAYS);

        TableColumn<DonHangDTO, String> colMaDonHang = new TableColumn<>("Mã đơn hàng");
        colMaDonHang.setCellValueFactory(new PropertyValueFactory<>("maDonHang"));

        TableColumn<DonHangDTO, String> colTenKhachHang = new TableColumn<>("Khách hàng");
        colTenKhachHang.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));

        TableColumn<DonHangDTO, String> colNgayDat = new TableColumn<>("Ngày đặt");
        colNgayDat.setCellValueFactory(new PropertyValueFactory<>("ngayDat"));

        TableColumn<DonHangDTO, String> colTongTien = new TableColumn<>("Tổng tiền");
        colTongTien.setCellValueFactory(cellData -> {
            double tongTien = cellData.getValue().getTongTien();
            return javafx.beans.binding.Bindings
                    .createStringBinding(() -> new DecimalFormat("#,###").format(tongTien));
        });

        tblDonHang.getColumns().addAll(Arrays.asList(colMaDonHang, colTenKhachHang, colNgayDat, colTongTien));

        getChildren().addAll(lblTitle, header, tblDonHang);
    }

    public TextField getTxtTimKiem() {
        return txtTimKiem;
    }

    public TableView<DonHangDTO> getTblDonHang() {
        return tblDonHang;
    }

    public Button getBtnTaoDonHang() {
        return btnTaoDonHang;
    }
}