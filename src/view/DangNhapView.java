package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DangNhapView extends VBox {
    private TextField txtTenDangNhap;
    private PasswordField pwdMatKhau;
    private Button btnDangNhap;
    private Label lblThongBao;

    public DangNhapView() {
        setSpacing(20);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(24));

        Label lblTitle = new Label("Đăng Nhập");
        lblTitle.getStyleClass().add("title-1");

        Label lblTenDangNhap = new Label("Tên đăng nhập");
        txtTenDangNhap = new TextField();
        txtTenDangNhap.setPromptText("Tên đăng nhập");
        VBox tenDangNhapBox = new VBox(4, lblTenDangNhap, txtTenDangNhap);

        Label lblMatKhau = new Label("Mật khẩu");
        pwdMatKhau = new PasswordField();
        pwdMatKhau.setPromptText("Mật khẩu");
        VBox matKhauBox = new VBox(4, lblMatKhau, pwdMatKhau);

        btnDangNhap = new Button("Đăng nhập");
        btnDangNhap.getStyleClass().add("success");

        lblThongBao = new Label("");
        lblThongBao.getStyleClass().add("warning");

        getChildren().addAll(lblTitle, tenDangNhapBox, matKhauBox, btnDangNhap, lblThongBao);
    }

    public TextField getTxtTenDangNhap() {
        return txtTenDangNhap;
    }

    public PasswordField getPwdMatKhau() {
        return pwdMatKhau;
    }

    public Button getBtnDangNhap() {
        return btnDangNhap;
    }

    public Label getLblThongBao() {
        return lblThongBao;
    }
}