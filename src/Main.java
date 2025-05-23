import controller.DangNhapController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.DangNhapView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DangNhapView view = new DangNhapView();
        new DangNhapController(view);

        Scene scene = new Scene(view.getView(), 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MVC JavaFX Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
