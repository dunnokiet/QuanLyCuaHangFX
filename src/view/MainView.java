package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainView extends BorderPane {
    private VBox thanhDieuHuong;
    private Button btnHome;
    private Button btnSanPham;
    private Button btnKhachHang;
    private Button btnDonHang;
    private Button btnNguoiDung;
    private Button btnDangXuat;

    public MainView(String vaiTro, String tenDangNhap) {
        thanhDieuHuong = new VBox();
        thanhDieuHuong.setPadding(new Insets(20));
        thanhDieuHuong.setStyle(
                "-fx-background-color: #f5f7fa; -fx-min-width: 200; -fx-border-color: #e0e0e0; -fx-border-width: 0 1 0 0;");
        thanhDieuHuong.setAlignment(Pos.TOP_LEFT);
        thanhDieuHuong.setSpacing(10);

        Label lblLogo = new Label("RetailSys");
        lblLogo.setFont(new Font("Arial", 20));
        lblLogo.setStyle("-fx-text-fill: #1a73e8; -fx-font-weight: bold; -fx-padding: 10 0 20 0;");

        btnHome = createNavButton("🏠 Home");
        btnSanPham = createNavButton("📦 Sản phẩm");
        btnKhachHang = createNavButton("👥 Khách hàng");
        btnDonHang = createNavButton("📋 Đơn hàng");
        btnNguoiDung = createNavButton("👤 Người dùng");
        btnNguoiDung.setDisable(!vaiTro.equals("Admin"));
        btnDangXuat = createNavButton("🚪 Đăng xuất");
        btnDangXuat.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #d32f2f; -fx-font-size: 14; -fx-alignment: BASELINE_LEFT;");

        thanhDieuHuong.getChildren().addAll(lblLogo, btnHome, btnSanPham, btnKhachHang, btnDonHang, btnNguoiDung,
                btnDangXuat);

        setLeft(thanhDieuHuong);
    }

    private Button createNavButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(180);
        btn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 14; -fx-alignment: BASELINE_LEFT;");
        return btn;
    }

    public void setActiveButton(Button activeButton) {
        btnHome.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 14; -fx-alignment: BASELINE_LEFT;");
        btnSanPham.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 14; -fx-alignment: BASELINE_LEFT;");
        btnKhachHang.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 14; -fx-alignment: BASELINE_LEFT;");
        btnDonHang.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 14; -fx-alignment: BASELINE_LEFT;");
        btnNguoiDung.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 14; -fx-alignment: BASELINE_LEFT;");
        btnDangXuat.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #d32f2f; -fx-font-size: 14; -fx-alignment: BASELINE_LEFT;");

        if (activeButton != null) {
            activeButton.setStyle(
                    "-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-size: 14; -fx-alignment: BASELINE_LEFT;");
        }
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

    public void setContent(Pane pane) {
        setCenter(pane);
    }
}
