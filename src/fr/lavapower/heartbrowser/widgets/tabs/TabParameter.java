package fr.lavapower.heartbrowser.widgets.tabs;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class TabParameter extends GridPane
{
    public TabParameter() {
        super();
        Label title = new Label("Paramètres");

        title.setAlignment(Pos.CENTER);

        setListenersAndEvents();

        add(title, 0, 0);
        setHgrow(title, Priority.ALWAYS);
    }

    public void setListenersAndEvents() {

    }
}
