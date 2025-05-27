package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MainView extends BorderPane {
    private VBox thanhDieuHuong;
    private Button btnHome;
    private Button btnSanPham;
    private Button btnKhachHang;
    private Button btnDonHang;
    private Button btnNguoiDung;
    private Button btnDangXuat;

    public MainView(String vaiTro, String tenDangNhap) {
        thanhDieuHuong = new VBox(8);
        thanhDieuHuong.setPadding(new Insets(24));
        thanhDieuHuong.setAlignment(Pos.TOP_LEFT);
        thanhDieuHuong.setSpacing(10);

        btnHome = createNavButton("Trang chủ");
        btnSanPham = createNavButton("Sản phẩm");
        btnKhachHang = createNavButton("Khách hàng");
        btnDonHang = createNavButton("Đơn hàng");
        btnNguoiDung = createNavButton("Người dùng");

        btnNguoiDung.setDisable(!vaiTro.equals("Quản lý"));
        btnDangXuat = createNavButton("Đăng xuất");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        thanhDieuHuong.getChildren().addAll(btnHome, btnSanPham, btnKhachHang, btnDonHang, btnNguoiDung, spacer,
                btnDangXuat);

        setLeft(thanhDieuHuong);
    }

    private Button createNavButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(200);
        return btn;
    }

    public void setActiveButton(Button activeButton) {

        if (activeButton != null) {

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
