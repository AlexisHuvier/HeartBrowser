package fr.lavapower.heartbrowser.widgets.tabbuttons;

import fr.lavapower.heartbrowser.widgets.TabsList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TabBasicButton extends TabButton
{
    public TabBasicButton(String text, EventHandler<ActionEvent> actionEventHandler) {
        super();
        setText(text);
        setOnAction(actionEventHandler);
    }

    public TabBasicButton(EventHandler<ActionEvent> actionEventHandler) {
        super();
        setOnAction(actionEventHandler);
    }
}
