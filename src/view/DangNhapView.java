package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class DangNhapView extends VBox {
    private TextField txtTenDangNhap;
    private PasswordField pwdMatKhau;
    private Button btnDangNhap;
    private Label lblThongBao;

    public DangNhapView() {
        setAlignment(Pos.CENTER);
        setSpacing(8);
        setPadding(new Insets(24));
        setStyle("-fx-background-color: #ffffff;");

        Label lblTitle = new Label("Đăng nhập");
        lblTitle.setFont(new Font("Arial", 20));
        lblTitle.setStyle("-fx-text-fill: #333;");

        txtTenDangNhap = new TextField();
        txtTenDangNhap.setPromptText("Tên đăng nhập");
        txtTenDangNhap.setMaxWidth(250);
        txtTenDangNhap.setStyle(
                "-fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10; -fx-font-size: 14;");

        pwdMatKhau = new PasswordField();
        pwdMatKhau.setPromptText("Mật khẩu");
        pwdMatKhau.setMaxWidth(250);
        pwdMatKhau.setStyle(
                "-fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10; -fx-font-size: 14;");

        btnDangNhap = new Button("Đăng nhập");
        btnDangNhap.setPrefWidth(250);
        btnDangNhap.setStyle(
                "-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-size: 14; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 10;");

        lblThongBao = new Label("");
        lblThongBao.setStyle("-fx-text-fill: #d32f2f; -fx-font-size: 12;");

        getChildren().addAll(lblTitle, txtTenDangNhap, pwdMatKhau, btnDangNhap, lblThongBao);
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