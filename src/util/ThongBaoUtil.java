package util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ThongBaoUtil {

    public static void thongTin(String tieuDe, String noiDung) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(tieuDe);
        alert.setHeaderText(null);
        alert.setContentText(noiDung);
        alert.showAndWait();
    }

    public static void baoLoi(String tieuDe, String noiDung) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(tieuDe);
        alert.setHeaderText(null);
        alert.setContentText(noiDung);
        alert.showAndWait();
    }

    public static void canhBao(String tieuDe, String noiDung) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(tieuDe);
        alert.setHeaderText(null);
        alert.setContentText(noiDung);
        alert.showAndWait();
    }

    public static boolean xacNhan(String tieuDe, String noiDung) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(tieuDe);
        alert.setHeaderText(null);
        alert.setContentText(noiDung);

        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
            return true;
        }
        return false;
    }
}
