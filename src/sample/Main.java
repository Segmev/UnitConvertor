package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void init() {
        Vars = new Variables();
        Vars.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(Vars.rootPane, 600, 400));
        primaryStage.show();
    }

    private Variables Vars;
    public static void main(String[] args) {
        launch(args);
    }
}
