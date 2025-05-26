import view.DangNhapView;
import controller.DangNhapController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.NguoiDungModel;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        NguoiDungModel nguoiDungModel = new NguoiDungModel();
        DangNhapView dangNhapView = new DangNhapView();
        new DangNhapController(dangNhapView, nguoiDungModel, primaryStage);

        primaryStage.setScene(new Scene(dangNhapView, 400, 300));
        primaryStage.setTitle("Đăng nhập - Hệ Thống Quản Lý Cửa Hàng");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
