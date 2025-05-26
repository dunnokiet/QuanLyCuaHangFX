package view.sanpham;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChinhSuaSanPhamView extends VBox {
    private TextField txtMaSanPham;
    private TextField txtTenSanPham;
    private TextField txtLoai;
    private TextField txtGia;
    private TextField txtSoLuongTon;
    private Button btnLuu;
    private Button btnHuy;
    private Label lblThongBao;

    public ChinhSuaSanPhamView() {
        super(8);
        setPadding(new Insets(20));
        setStyle("-fx-background-color: #ffffff;");

        Label lblTitle = new Label("Chỉnh sửa sản phẩm");
        lblTitle.setStyle("-fx-font-size: 18; -fx-text-fill: #333;");

        txtMaSanPham = new TextField();
        txtMaSanPham.setPromptText("Mã sản phẩm");
        txtMaSanPham.setEditable(false);

        txtTenSanPham = new TextField();
        txtTenSanPham.setPromptText("Tên sản phẩm");
        txtTenSanPham.setPrefWidth(300);

        txtLoai = new TextField();
        txtLoai.setPromptText("Loại");
        txtLoai.setPrefWidth(300);

        txtGia = new TextField();
        txtGia.setPromptText("Giá (VNĐ)");
        txtGia.setPrefWidth(300);

        txtSoLuongTon = new TextField();
        txtSoLuongTon.setPromptText("Số lượng tồn");
        txtSoLuongTon.setPrefWidth(300);

        btnLuu = new Button("Lưu");
        btnHuy = new Button("Hủy");
        btnLuu.setStyle(
                "-fx-background-color: #1e90ff; -fx-text-fill: white; -fx-font-weight: bold;");
        btnHuy.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #000; -fx-fnt-weight:bold;");

        HBox buttonBox = new HBox(8, btnHuy, btnLuu);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        getChildren().addAll(lblTitle, txtMaSanPham, txtTenSanPham, txtLoai, txtGia, txtSoLuongTon, buttonBox);
    }

    public TextField getTxtMaSanPham() {
        return txtMaSanPham;
    }

    public TextField getTxtTenSanPham() {
        return txtTenSanPham;
    }

    public TextField getTxtLoai() {
        return txtLoai;
    }

    public TextField getTxtGia() {
        return txtGia;
    }

    public TextField getTxtSoLuongTon() {
        return txtSoLuongTon;
    }

    public Button getBtnLuu() {
        return btnLuu;
    }

    public Button getBtnHuy() {
        return btnHuy;
    }

    public Label getLblThongBao() {
        return lblThongBao;
    }
}