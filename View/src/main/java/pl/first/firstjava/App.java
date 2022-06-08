package pl.first.firstjava;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception  {
        MenuController menuController = new MenuController();
        menuController.showStage();
    }

    public static void main(String[] args) {
        launch(args);
    }

}