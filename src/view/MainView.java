package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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

    public MainView(boolean isQuanLy, String tenDangNhap) {
        thanhDieuHuong = new VBox(8);
        thanhDieuHuong.setPadding(new Insets(24));
        thanhDieuHuong.setAlignment(Pos.TOP_LEFT);
        thanhDieuHuong.setSpacing(10);

        Label lblTitle = new Label("Chào " + tenDangNhap);
        lblTitle.getStyleClass().add("title-3");

        btnHome = createNavButton("Trang chủ");
        btnSanPham = createNavButton("Sản phẩm");
        btnKhachHang = createNavButton("Khách hàng");
        btnDonHang = createNavButton("Đơn hàng");
        btnNguoiDung = createNavButton("Người dùng");

        btnDangXuat = createNavButton("Đăng xuất");
        btnDangXuat.getStyleClass().add("danger");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        if (isQuanLy)
            thanhDieuHuong.getChildren().addAll(lblTitle, btnHome, btnSanPham, btnKhachHang, btnDonHang, btnNguoiDung,
                    spacer,
                    btnDangXuat);
        else
            thanhDieuHuong.getChildren().addAll(lblTitle, btnHome, btnSanPham, btnKhachHang, btnDonHang, spacer,
                    btnDangXuat);

        setLeft(thanhDieuHuong);
    }

    private Button createNavButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(200);
        return btn;
    }

    public void setActiveButton(Button activeButton) {

        btnHome.getStyleClass().remove("success");
        btnSanPham.getStyleClass().remove("success");
        btnKhachHang.getStyleClass().remove("success");
        btnDonHang.getStyleClass().remove("success");
        btnNguoiDung.getStyleClass().remove("success");
        if (activeButton != null) {
            activeButton.getStyleClass().add("success");

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
}
