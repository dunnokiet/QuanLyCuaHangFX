package view.DonHang;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import dto.ChiTietDonHangDTO;
import dto.DonHangDTO;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ChiTietDonHangView extends VBox {
    private Label lblTitle;
    private Label lblMaDonHang;
    private Label lblTenKhachHang;
    private Label lblNgayDat;
    private Label lblTongTien;
    private Label lblTrangThai;

    private TableView<ChiTietDonHangDTO> tblChiTietDonHang;

    public ChiTietDonHangView() {
        setPrefWidth(400);
        setSpacing(16);
        setPadding(new Insets(24));

        lblTitle = new Label("Chi Tiết Đơn Hàng");
        lblTitle.getStyleClass().add("title-1");

        lblMaDonHang = new Label("Mã đơn hàng: ");
        lblTenKhachHang = new Label("Khách hàng: ");
        lblNgayDat = new Label("Ngày đặt: ");
        lblTongTien = new Label("Tổng tiền: ");
        lblTrangThai = new Label("Trạng thái: ");

        tblChiTietDonHang = new TableView<>();
        tblChiTietDonHang.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        VBox.setVgrow(tblChiTietDonHang, Priority.ALWAYS);

        TableColumn<ChiTietDonHangDTO, String> colTenSanPham = new TableColumn<>("Sản phẩm");
        colTenSanPham.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));

        TableColumn<ChiTietDonHangDTO, Integer> colSoLuong = new TableColumn<>("Số lượng");
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));

        TableColumn<ChiTietDonHangDTO, String> colDonGia = new TableColumn<>("Đơn giá");
        colDonGia.setCellValueFactory(cellData -> {
            double donGia = cellData.getValue().getDonGia();
            return javafx.beans.binding.Bindings
                    .createStringBinding(() -> new DecimalFormat("#,###").format(donGia));
        });

        tblChiTietDonHang.getColumns().addAll(Arrays.asList(colTenSanPham, colSoLuong, colDonGia));

        getChildren().addAll(lblTitle, lblMaDonHang, lblTenKhachHang, lblNgayDat, lblTongTien, tblChiTietDonHang);
    }

    public void updateChiTietDonHang(DonHangDTO donHang, List<ChiTietDonHangDTO> chiTietList) {
        lblMaDonHang.setText("Mã đơn hàng: " + donHang.getMaDonHang());
        lblTenKhachHang.setText("Khách hàng: " + donHang.getTenKhachHang());
        lblNgayDat.setText("Ngày đặt: " + donHang.getNgayDat());
        lblTongTien.setText("Tổng tiền: " + new DecimalFormat("#,###").format(donHang.getTongTien()) + " VNĐ");
        lblTrangThai.setText("Trạng thái: " + donHang.getNgayDat());
        tblChiTietDonHang.getItems().clear();
        tblChiTietDonHang.getItems().addAll(chiTietList);
    }

    public Label getLblTitle() {
        return lblTitle;
    }

    public Label getLblMaDonHang() {
        return lblMaDonHang;
    }

    public Label getLblTenKhachHang() {
        return lblTenKhachHang;
    }

    public Label getLblNgayDat() {
        return lblNgayDat;
    }

    public Label getLblTongTien() {
        return lblTongTien;
    }

    public Label getLblTrangThai() {
        return lblTrangThai;
    }

    public TableView<ChiTietDonHangDTO> getTblChiTietDonHang() {
        return tblChiTietDonHang;
    }

}