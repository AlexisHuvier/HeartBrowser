package fr.lavapower.heartbrowser.widgets;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class Grid extends GridPane
{
    public Grid() {
        super();

        TabsList tabsList = new TabsList();
        Separator separator = new Separator(Orientation.VERTICAL);

        setHgap(5);
        setVgap(5);
        setVgrow(tabsList, Priority.ALWAYS);
        add(tabsList, 0, 0);
        add(separator, 1, 0);

        tabsList.addTab("http://youtube.fr");
    }
}
