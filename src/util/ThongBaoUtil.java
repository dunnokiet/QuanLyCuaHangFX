package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
}
