package view.SanPham;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    public ChinhSuaSanPhamView() {
        setSpacing(8);
        setPadding(new Insets(20));

        Label lblTitle = new Label("Chỉnh Sửa Sản Phẩm");
        lblTitle.getStyleClass().addAll("title-1");

        Label lblMaSanPham = new Label("Mã sản phẩm");

        txtMaSanPham = new TextField();
        txtMaSanPham.setPromptText("Mã sản phẩm");
        txtMaSanPham.setDisable(true);
        VBox maSanPhamBox = new VBox(4, lblMaSanPham, txtMaSanPham);

        Label lblTenSanPham = new Label("Tên sản phẩm");
        txtTenSanPham = new TextField();
        txtTenSanPham.setPromptText("Tên sản phẩm");
        VBox tenSanPhamBox = new VBox(4, lblTenSanPham, txtTenSanPham);

        Label lblLoai = new Label("Loại");
        txtLoai = new TextField();
        txtLoai.setPromptText("Loại");
        VBox loaiBox = new VBox(4, lblLoai, txtLoai);

        Label lblGia = new Label("Giá (VNĐ)");
        txtGia = new TextField();
        txtGia.setPromptText("Giá (VNĐ)");
        VBox giaBox = new VBox(4, lblGia, txtGia);

        Label lblSoLuongTon = new Label("Số lượng tồn");
        txtSoLuongTon = new TextField();
        txtSoLuongTon.setPromptText("Số lượng tồn");
        VBox soLuongTonBox = new VBox(4, lblSoLuongTon, txtSoLuongTon);

        btnLuu = new Button("Lưu");
        btnLuu.getStyleClass().add("success");
        btnHuy = new Button("Hủy");
        btnHuy.getStyleClass().add("danger");

        HBox buttonBox = new HBox(8, btnHuy, btnLuu);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        getChildren().addAll(lblTitle, maSanPhamBox, tenSanPhamBox, loaiBox, giaBox, soLuongTonBox, buttonBox);
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
}