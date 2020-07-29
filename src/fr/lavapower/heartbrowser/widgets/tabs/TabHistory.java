package fr.lavapower.heartbrowser.widgets.tabs;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class TabHistory extends GridPane
{
    public TabHistory() {
        super();
        Label title = new Label("Historique");

        title.setAlignment(Pos.CENTER);

        setListenersAndEvents();

        add(title, 0, 0);
        setHgrow(title, Priority.ALWAYS);
    }

    public void setListenersAndEvents() {

    }
}
