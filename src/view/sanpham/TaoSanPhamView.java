package view.sanpham;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TaoSanPhamView extends VBox {
    private TextField txtMaSanPham;
    private TextField txtTenSanPham;
    private TextField txtLoai;
    private TextField txtGia;
    private TextField txtSoLuongTon;
    private Button btnTao;
    private Button btnHuy;
    private Label lblThongBao;

    public TaoSanPhamView() {
        super(8);
        setPadding(new Insets(24));
        setStyle("-fx-background-color: #ffffff;");

        Label lblTitle = new Label("Tạo sản phẩm mới");
        lblTitle.setStyle("-fx-font-size: 18; -fx-text-fill: #333;");

        txtMaSanPham = new TextField();
        txtMaSanPham.setPromptText("Mã sản phẩm");

        txtTenSanPham = new TextField();
        txtTenSanPham.setPromptText("Tên sản phẩm");

        txtLoai = new TextField();
        txtLoai.setPromptText("Loại");

        txtGia = new TextField();
        txtGia.setPromptText("Giá (VNĐ)");

        txtSoLuongTon = new TextField();
        txtSoLuongTon.setPromptText("Số lượng tồn");

        btnTao = new Button("Tạo");
        btnHuy = new Button("Hủy");
        btnTao.setStyle(
                "-fx-background-color: #1e90ff; -fx-text-fill: white; -fx-font-weight: bold;");
        btnHuy.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #000; -fx-font-weight: bold");

        HBox buttonBox = new HBox(8, btnHuy, btnTao);
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

    public Button getBtnTao() {
        return btnTao;
    }

    public Button getBtnHuy() {
        return btnHuy;
    }

    public Label getLblThongBao() {
        return lblThongBao;
    }

}