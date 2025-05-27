package view.TrangChu;

import dto.DonHangDTO;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class TrangChuView extends BorderPane {

    private Button btnHome;
    private Button btnSanPham;
    private Button btnKhachHang;
    private Button btnDonHang;
    private Button btnNguoiDung;
    private Button btnDangXuat;

    private TableView<DonHangDTO> tblRecentOrders;
    private Label lblRevenueValue;
    private Label lblOrdersValue;
    private Label lblCustomersValue;
    private ComboBox<String> cbxStatusFilter;
    private DatePicker dpStartDate;
    private DatePicker dpEndDate;
    private Button btnApplyFilter;

    public TrangChuView(String vaiTro, String tenDangNhap) {
        VBox vbxContent = new VBox(20);
        vbxContent.setPadding(new Insets(20));

        HBox hbxSummaryCards = new HBox(20);
        hbxSummaryCards.setAlignment(Pos.CENTER_LEFT);

        VBox vbxCardRevenue = new VBox(10);
        vbxCardRevenue.setPadding(new Insets(15));

        Label lblRevenueTitle = new Label("Tổng doanh thu");
        lblRevenueValue = new Label("0 VNĐ");
        vbxCardRevenue.getChildren().addAll(lblRevenueTitle, lblRevenueValue);

        VBox vbxCardOrders = new VBox(10);
        vbxCardOrders.setPadding(new Insets(15));

        Label lblOrdersTitle = new Label("Số đơn hàng");
        lblOrdersValue = new Label("0");
        vbxCardOrders.getChildren().addAll(lblOrdersTitle, lblOrdersValue);

        VBox vbxCardCustomers = new VBox(10);
        vbxCardCustomers.setPadding(new Insets(15));

        Label lblCustomersTitle = new Label("Số khách hàng");
        lblCustomersValue = new Label("0");
        vbxCardCustomers.getChildren().addAll(lblCustomersTitle, lblCustomersValue);

        hbxSummaryCards.getChildren().addAll(vbxCardRevenue, vbxCardOrders, vbxCardCustomers);

        HBox hbxFilter = new HBox(16);
        hbxFilter.setAlignment(Pos.CENTER_LEFT);

        // Nhãn "Trạng thái"
        Label lblStatusFilter = new Label("Trạng thái:");

        // ComboBox
        cbxStatusFilter = new ComboBox<>();
        cbxStatusFilter.getItems().addAll("Tất cả", "Đã giao", "Đang xử lý", "Đã hủy");
        cbxStatusFilter.setValue("Tất cả");
        cbxStatusFilter.setMaxWidth(Double.MAX_VALUE); // Cho phép mở rộng
        HBox.setHgrow(cbxStatusFilter, Priority.ALWAYS);

        // Nhãn "Từ ngày"
        Label lblStartDate = new Label("Từ ngày:");

        dpStartDate = new DatePicker();
        dpStartDate.setValue(java.time.LocalDate.now().minusDays(30));
        dpStartDate.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(dpStartDate, Priority.ALWAYS);

        Label lblEndDate = new Label("Đến ngày:");

        dpEndDate = new DatePicker();
        dpEndDate.setValue(java.time.LocalDate.now());
        dpEndDate.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(dpEndDate, Priority.ALWAYS);

        btnApplyFilter = new Button("Áp dụng");

        hbxFilter.getChildren().addAll(
                lblStatusFilter, cbxStatusFilter,
                lblStartDate, dpStartDate,
                lblEndDate, dpEndDate,
                btnApplyFilter);

        tblRecentOrders = new TableView<>();
        tblRecentOrders.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<DonHangDTO, String> colOrderId = new TableColumn<>("Mã đơn hàng");
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("maDonHang"));

        TableColumn<DonHangDTO, String> colCustomer = new TableColumn<>("Khách hàng");
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));

        TableColumn<DonHangDTO, String> colTotal = new TableColumn<>("Tổng tiền");
        colTotal.setCellValueFactory(cellData -> {
            double tongTien = cellData.getValue().getTongTien();
            return javafx.beans.binding.Bindings
                    .createStringBinding(() -> new DecimalFormat("#,###").format(tongTien));
        });

        TableColumn<DonHangDTO, String> colDate = new TableColumn<>("Ngày đặt");
        colDate.setCellValueFactory(cellData -> {
            Date ngayDat = cellData.getValue().getNgayDat();
            return javafx.beans.binding.Bindings
                    .createStringBinding(() -> new SimpleDateFormat("yyyy-MM-dd").format(ngayDat));
        });

        TableColumn<DonHangDTO, String> colStatus = new TableColumn<>("Trạng thái");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("trangThai"));

        tblRecentOrders.getColumns().addAll(Arrays.asList(colOrderId, colCustomer, colTotal, colDate, colStatus));

        vbxContent.getChildren().addAll(hbxSummaryCards, hbxFilter,
                tblRecentOrders);

        setCenter(vbxContent);
    }

    public Button getBtnHome() {
        return btnHome;
    }

    public Button getBtnSanPham() {
        return btnSanPham;
    }

    public Button getBtnKhachHang() {
        return btnKhachHang;
    }

    public Button getBtnDonHang() {
        return btnDonHang;
    }

    public Button getBtnNguoiDung() {
        return btnNguoiDung;
    }

    public Button getBtnDangXuat() {
        return btnDangXuat;
    }

    public TableView<DonHangDTO> getTblRecentOrders() {
        return tblRecentOrders;
    }

    public Label getLblRevenueValue() {
        return lblRevenueValue;
    }

    public Label getLblOrdersValue() {
        return lblOrdersValue;
    }

    public Label getLblCustomersValue() {
        return lblCustomersValue;
    }

    public ComboBox<String> getCbxStatusFilter() {
        return cbxStatusFilter;
    }

    public DatePicker getDpStartDate() {
        return dpStartDate;
    }

    public DatePicker getDpEndDate() {
        return dpEndDate;
    }

    public Button getBtnApplyFilter() {
        return btnApplyFilter;
    }
}