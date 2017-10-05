package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void init() {
        Vars = new Variables();
        Vars.init();
        Logics = new Logics(Vars);
        Logics.fromImperialConversion();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(Vars.rootPane, 500, 140));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Variables Vars;
    private Logics Logics;
    public static void main(String[] args) {
        launch(args);
    }
}
