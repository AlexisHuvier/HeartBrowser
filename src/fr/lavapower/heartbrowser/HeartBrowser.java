package fr.lavapower.heartbrowser;

import fr.lavapower.heartbrowser.widgets.Grid;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HeartBrowser extends Application
{
    private static Grid grid;
    public static Stage stage;

    @Override
    public void start(Stage stage)
    {
        HeartBrowser.stage = stage;

        grid = new Grid();
        Scene scene = new Scene(grid, 780, 640);

        stage.setMaximized(true);
        stage.setTitle("HeartBrowser");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
