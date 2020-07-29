package fr.lavapower.heartbrowser;

import fr.lavapower.heartbrowser.utils.Configuration;
import fr.lavapower.heartbrowser.utils.Database;
import fr.lavapower.heartbrowser.widgets.Grid;
import fr.lavapower.heartbrowser.windows.QuestionYesCancel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HeartBrowser extends Application
{
    public static final Logger logger = Logger.getLogger("HeartBrowser");

    private static Grid grid;
    public static Stage stage;
    public static Configuration configuration;

    @Override
    public void start(Stage stage) throws IOException
    {
        HeartBrowser.stage = stage;

        loadLogger();

        Database db = new Database("base.db");
        db.setUp();
        configuration = new Configuration(db);
        logger.setLevel(configuration.logLevel);
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
                    close();
            }
            else
                close();
        });

        stage.setMaximized(true);
        stage.setTitle("HeartBrowser");
        stage.setScene(scene);
        stage.show();

        db.close();
    }

    @Override
    public void stop() { close(); }

    public static void close() {
        Database db = new Database("base.db");
        configuration.saveConfig(db);
        db.close();
        logger.log(Level.INFO, "Browser Closed");
        System.exit(0);
    }

    public void loadLogger() throws IOException
    {
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        logger.addHandler(ch);

        FileHandler fh = new FileHandler("heartbrowser.log", 20000, 1);
        fh.setLevel(Level.ALL);
        logger.addHandler(fh);
        logger.log(Level.INFO, "Logger Loaded");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
