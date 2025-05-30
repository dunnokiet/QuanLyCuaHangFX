package view.KhachHang;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChinhSuaKhachHangView extends VBox {
    private TextField txtMaKhachHang;
    private TextField txtTenKhachHang;
    private TextField txtSoDienThoai;
    private TextField txtDiaChi;
    private Button btnLuu;
    private Button btnHuy;

    public ChinhSuaKhachHangView() {
        super(8);
        setPadding(new Insets(20));

        Label lblTitle = new Label("Chỉnh sửa khách hàng");
        lblTitle.getStyleClass().addAll("title-1");

        Label lblMaKhachHang = new Label("Mã khách hàng");
        txtMaKhachHang = new TextField();
        txtMaKhachHang.setPromptText("Mã khách hàng");
        txtMaKhachHang.setDisable(true);
        VBox maKhachHangBox = new VBox(4, lblMaKhachHang, txtMaKhachHang);

        Label lblTenKhachHang = new Label("Tên khách hàng");
        txtTenKhachHang = new TextField();
        txtTenKhachHang.setPromptText("Tên khách hàng");
        VBox tenKhachHangBox = new VBox(4, lblTenKhachHang, txtTenKhachHang);

        Label lblSoDienThoai = new Label("Số điện thoại");
        txtSoDienThoai = new TextField();
        txtSoDienThoai.setPromptText("Số điện thoại");
        VBox soDienThoaiBox = new VBox(4, lblSoDienThoai, txtSoDienThoai);

        Label lblDiaChi = new Label("Địa chỉ");
        txtDiaChi = new TextField();
        txtDiaChi.setPromptText("Địa chỉ");
        VBox diaChiBox = new VBox(4, lblDiaChi, txtDiaChi);

        btnLuu = new Button("Lưu");
        btnLuu.getStyleClass().add("success");
        btnHuy = new Button("Hủy");
        btnHuy.getStyleClass().add("danger");
        HBox buttonBox = new HBox(8, btnHuy, btnLuu);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        getChildren().addAll(lblTitle, maKhachHangBox, tenKhachHangBox, soDienThoaiBox, diaChiBox, buttonBox);
    }

    public TextField getTxtMaKhachHang() {
        return txtMaKhachHang;
    }

    public TextField getTxtTenKhachHang() {
        return txtTenKhachHang;
    }

    public TextField getTxtSoDienThoai() {
        return txtSoDienThoai;
    }

    public TextField getTxtDiaChi() {
        return txtDiaChi;
    }

    public Button getBtnLuu() {
        return btnLuu;
    }

    public Button getBtnHuy() {
        return btnHuy;
    }
}