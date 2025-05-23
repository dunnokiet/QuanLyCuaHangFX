package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DangNhapView {
    private VBox view;
    private TextField tenDangNhapField;
    private PasswordField matKhauField;
    private Button loginButton;
    private Label messageLabel;

    public DangNhapView() {
        view = new VBox(24);
        view.setPadding(new Insets(8));
        view.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Hẹ Thống Quản Lý Cửa Hàng Bán Lẻ");

        tenDangNhapField = new TextField();
        tenDangNhapField.setPromptText("Tên đăng nhập");
        tenDangNhapField.setMaxWidth(200);

        matKhauField = new PasswordField();
        matKhauField.setPromptText("Mật khẩu");
        matKhauField.setMaxWidth(200);

        loginButton = new Button("Đăng nhập");
        loginButton.setPrefWidth(100);

        messageLabel = new Label();

        view.getChildren().addAll(titleLabel, tenDangNhapField, matKhauField, loginButton, messageLabel);
    }

    public VBox getView() {
        return view;
    }

    public TextField getTenDangNhapField() {
        return tenDangNhapField;
    }

    public PasswordField getMatKhauField() {
        return matKhauField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
}