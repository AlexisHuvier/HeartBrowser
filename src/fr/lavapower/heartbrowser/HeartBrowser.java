package fr.lavapower.heartbrowser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HeartBrowser extends Application
{
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception
    {
        HeartBrowser.stage = stage;

        Parent parent = FXMLLoader.load(getClass().getResource("browser.fxml"));
        Scene scene = new Scene(parent);

        stage.setMaximized(true);
        stage.setTitle("HeartBrowser");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
