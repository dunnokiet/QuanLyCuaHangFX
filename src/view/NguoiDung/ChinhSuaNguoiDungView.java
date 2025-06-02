package view.NguoiDung;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChinhSuaNguoiDungView extends VBox {
    private TextField txtMaNguoiDung;
    private TextField txtTenNguoiDung;
    private RadioButton rdQuanLy;
    private RadioButton rdNhanVien;
    private TextField txtMatKhau;

    private Button btnLuu;
    private Button btnHuy;

    public ChinhSuaNguoiDungView() {
        super(8);
        setPadding(new Insets(20));

        Label lblTitle = new Label("Chỉnh Sửa Người Dùng");
        lblTitle.getStyleClass().add("title-1");

        Label lblMaNguoiDung = new Label("Mã người dùng");
        txtMaNguoiDung = new TextField();
        txtMaNguoiDung.setPromptText("Mã người dùng");
        txtMaNguoiDung.setDisable(true);
        VBox maNguoidungBox = new VBox(4, lblMaNguoiDung, txtMaNguoiDung);

        Label lblTenNguoiDung = new Label("Tên người dùng");
        txtTenNguoiDung = new TextField();
        txtTenNguoiDung.setPromptText("Tên người dùng");
        VBox tenNguoiDungBox = new VBox(4, lblTenNguoiDung, txtTenNguoiDung);

        Label lblVaiTro = new Label("Vai trò");
        rdQuanLy = new RadioButton("Quản lý");
        rdNhanVien = new RadioButton("Nhân viên");

        ToggleGroup groupVaiTro = new ToggleGroup();
        rdQuanLy.setToggleGroup(groupVaiTro);
        rdNhanVien.setToggleGroup(groupVaiTro);

        HBox vaiTroBox = new HBox(10, lblVaiTro, rdQuanLy, rdNhanVien);
        vaiTroBox.setPadding(new Insets(0, 0, 10, 0));

        Label lblMatKhau = new Label("Mật khẩu");
        txtMatKhau = new TextField();
        txtMatKhau.setPromptText("Mật khẩu");
        VBox matKhauBox = new VBox(4, lblMatKhau, txtMatKhau);

        btnLuu = new Button("Lưu");
        btnLuu.getStyleClass().add("success");
        btnHuy = new Button("Hủy");
        btnHuy.getStyleClass().add("danger");
        HBox buttonBox = new HBox(8, btnHuy, btnLuu);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        getChildren().addAll(lblTitle, maNguoidungBox, tenNguoiDungBox, vaiTroBox, matKhauBox, buttonBox);
    }

    public TextField getTxtMaNguoiDung() {
        return txtMaNguoiDung;
    }

    public TextField getTxtTenNguoiDung() {
        return txtTenNguoiDung;
    }

    public TextField getTxtMatKhau() {
        return txtMatKhau;
    }

    public String getRVaiTro() {
        if (rdQuanLy.isSelected())
            return "Quản lý";
        if (rdNhanVien.isSelected())
            return "Nhân viên";
        return "";
    }

    public void setVaiTro(String vaiTro) {
        if ("Quản lý".equalsIgnoreCase(vaiTro)) {
            rdQuanLy.setSelected(true);
        } else if ("Nhân viên".equalsIgnoreCase(vaiTro)) {
            rdNhanVien.setSelected(true);
        }
    }

    public Button getBtnLuu() {
        return btnLuu;
    }

    public Button getBtnHuy() {
        return btnHuy;
    }

}