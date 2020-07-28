package fr.lavapower.heartbrowser;

import fr.lavapower.heartbrowser.widgets.Grid;
import fr.lavapower.heartbrowser.windows.QuestionYesCancel;
import javafx.application.Application;
import javafx.application.Platform;
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

        Platform.setImplicitExit(false);
        stage.setOnCloseRequest(event -> {
            if(grid.tabsList.getTabCount() > 1)
            {
                QuestionYesCancel closeBrowser = new QuestionYesCancel("Fermeture du navigateur",
                        "Il y a plusieurs onglets ouverts.\nVoulez vous quand même fermer le navigateur ?");
                if(closeBrowser.isCancel())
                    event.consume();
                else
                    System.exit(0);
            }
            else
                System.exit(0);
        });

        stage.setMaximized(true);
        stage.setTitle("HeartBrowser");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop()
    {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
