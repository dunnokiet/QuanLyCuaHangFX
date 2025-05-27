package QuanLyKhachHang;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TaoKhachHangView extends VBox {
    private TextField txtMaKhachHang;
    private TextField txtTenKhachHang;
    private TextField txtSoDienThoai;
    private TextField txtDiaChi;
    private Button btnTao;
    private Button btnHuy;

    public TaoKhachHangView() {
        setSpacing(8);
        setPadding(new Insets(24));

        Label lblTitle = new Label("Tạo khách hàng mới");
        lblTitle.getStyleClass().add("title-1");

        Label lblMaKhachHang = new Label("Mã khách hàng");
        txtMaKhachHang = new TextField();
        txtMaKhachHang.setPromptText("Mã khách hàng");
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

        btnTao = new Button("Tạo");
        btnTao.getStyleClass().add("success");
        btnHuy = new Button("Hủy");
        btnHuy.getStyleClass().add("danger");

        HBox buttonBox = new HBox(8, btnHuy, btnTao);
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

    public Button getBtnTao() {
        return btnTao;
    }

    public Button getBtnHuy() {
        return btnHuy;
    }
}