package view.trangchu;

import dao.DonHangDAO;
import dto.DonHangDTO;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.ThongBaoUtil;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TrangChuView extends BorderPane {
    private DonHangDAO donHangDAO;

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
        donHangDAO = new DonHangDAO();

        VBox vbxContent = new VBox(20);
        vbxContent.setPadding(new Insets(20));
        vbxContent.setStyle("-fx-background-color: #ffffff;");

        HBox hbxSummaryCards = new HBox(20);
        hbxSummaryCards.setAlignment(Pos.CENTER_LEFT);

        VBox vbxCardRevenue = new VBox(10);
        vbxCardRevenue.setPadding(new Insets(15));
        vbxCardRevenue.setStyle(
                "-fx-background-color: #ffffff; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        Label lblRevenueTitle = new Label("Tổng doanh thu");
        lblRevenueTitle.setStyle("-fx-font-size: 14; -fx-text-fill: #333;");
        lblRevenueValue = new Label("0 VNĐ");
        lblRevenueValue.setStyle("-fx-font-size: 18; -fx-text-fill: #1a73e8; -fx-font-weight: bold;");
        vbxCardRevenue.getChildren().addAll(lblRevenueTitle, lblRevenueValue);

        VBox vbxCardOrders = new VBox(10);
        vbxCardOrders.setPadding(new Insets(15));
        vbxCardOrders.setStyle(
                "-fx-background-color: #ffffff; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        Label lblOrdersTitle = new Label("Số đơn hàng");
        lblOrdersTitle.setStyle("-fx-font-size: 14; -fx-text-fill: #333;");
        lblOrdersValue = new Label("0");
        lblOrdersValue.setStyle("-fx-font-size: 18; -fx-text-fill: #1a73e8; -fx-font-weight: bold;");
        vbxCardOrders.getChildren().addAll(lblOrdersTitle, lblOrdersValue);

        VBox vbxCardCustomers = new VBox(10);
        vbxCardCustomers.setPadding(new Insets(15));
        vbxCardCustomers.setStyle(
                "-fx-background-color: #ffffff; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        Label lblCustomersTitle = new Label("Số khách hàng");
        lblCustomersTitle.setStyle("-fx-font-size: 14; -fx-text-fill: #333;");
        lblCustomersValue = new Label("0");
        lblCustomersValue.setStyle("-fx-font-size: 18; -fx-text-fill: #1a73e8; -fx-font-weight: bold;");
        vbxCardCustomers.getChildren().addAll(lblCustomersTitle, lblCustomersValue);

        hbxSummaryCards.getChildren().addAll(vbxCardRevenue, vbxCardOrders, vbxCardCustomers);

        try {
            DecimalFormat df = new DecimalFormat("#,### VNĐ");
            lblRevenueValue.setText(df.format(donHangDAO.getTotalRevenue()));
            lblOrdersValue.setText(String.valueOf(donHangDAO.getOrderCount()));
            lblCustomersValue.setText(String.valueOf(donHangDAO.getCustomerCount()));
        } catch (SQLException e) {
            e.printStackTrace();
            ThongBaoUtil.baoLoi("Lỗi", "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }

        HBox hbxFilter = new HBox(10);
        hbxFilter.setAlignment(Pos.CENTER_LEFT);

        Label lblStatusFilter = new Label("Trạng thái:");
        lblStatusFilter.setStyle("-fx-font-size: 14; -fx-text-fill: #333;");
        cbxStatusFilter = new ComboBox<>();
        cbxStatusFilter.getItems().addAll("Tất cả", "Đã giao", "Đang xử lý", "Đã hủy");
        cbxStatusFilter.setValue("Tất cả");
        cbxStatusFilter.setPrefWidth(150);
        cbxStatusFilter.setStyle("-fx-font-size: 14;");

        Label lblStartDate = new Label("Từ ngày:");
        lblStartDate.setStyle("-fx-font-size: 14; -fx-text-fill: #333;");
        dpStartDate = new DatePicker();
        dpStartDate.setValue(java.time.LocalDate.now().minusDays(30));
        dpStartDate.setPrefWidth(150);
        dpStartDate.setStyle("-fx-font-size: 14;");

        Label lblEndDate = new Label("Đến ngày:");
        lblEndDate.setStyle("-fx-font-size: 14; -fx-text-fill: #333;");

        dpEndDate = new DatePicker();
        dpEndDate.setValue(java.time.LocalDate.now());
        dpEndDate.setPrefWidth(150);
        dpEndDate.setStyle("-fx-font-size: 14;");

        btnApplyFilter = new Button("Áp dụng");
        btnApplyFilter.setStyle(
                "-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-size: 14; -fx-border-radius: 5; -fx-background-radius: 5;");

        hbxFilter.getChildren().addAll(lblStatusFilter, cbxStatusFilter, lblStartDate, dpStartDate, lblEndDate,
                dpEndDate, btnApplyFilter);

        tblRecentOrders = new TableView<>();
        tblRecentOrders.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tblRecentOrders.setStyle("-fx-border-color: #e0e0e0;");

        TableColumn<DonHangDTO, String> colOrderId = new TableColumn<>("Mã đơn hàng");
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("maDonHang"));
        colOrderId.setPrefWidth(150);

        TableColumn<DonHangDTO, String> colCustomer = new TableColumn<>("Khách hàng");
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
        colCustomer.setPrefWidth(200);

        TableColumn<DonHangDTO, String> colTotal = new TableColumn<>("Tổng tiền");
        colTotal.setCellValueFactory(cellData -> {
            double tongTien = cellData.getValue().getTongTien();
            return javafx.beans.binding.Bindings
                    .createStringBinding(() -> new DecimalFormat("#,### VNĐ").format(tongTien));
        });
        colTotal.setPrefWidth(150);

        TableColumn<DonHangDTO, String> colDate = new TableColumn<>("Ngày đặt");
        colDate.setCellValueFactory(cellData -> {
            Date ngayDat = cellData.getValue().getNgayDat();
            return javafx.beans.binding.Bindings
                    .createStringBinding(() -> new SimpleDateFormat("yyyy-MM-dd").format(ngayDat));
        });
        colDate.setPrefWidth(150);

        List<TableColumn<DonHangDTO, ?>> columns = List.of(
                colOrderId, colCustomer, colTotal, colDate);

        tblRecentOrders.getColumns().addAll(columns);

        try {
            tblRecentOrders.getItems().addAll(donHangDAO.getRecentDonHang());
        } catch (SQLException e) {
            e.printStackTrace();
            ThongBaoUtil.baoLoi("Lỗi", e.getMessage());
        }

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

    public DonHangDAO getDonHangDAO() {
        return donHangDAO;
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