import view.DangNhapView;
import controller.DangNhapController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.NguoiDungModel;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        NguoiDungModel nguoiDungModel = new NguoiDungModel();
        DangNhapView dangNhapView = new DangNhapView();
        new DangNhapController(dangNhapView, nguoiDungModel, primaryStage);

        Scene scene = new Scene(dangNhapView, 500, 400);
        scene.getStylesheets().add(getClass().getResource("/resources/primer-light.css").toExternalForm());

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/shop.png")));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Đăng nhập - Hệ Thống Quản Lý Cửa Hàng");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
