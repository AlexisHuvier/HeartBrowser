package fr.lavapower.heartbrowser.widgets.tabbuttons;

import fr.lavapower.heartbrowser.widgets.TabsList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;

public class TabBasicButton extends TabButton
{
    public TabBasicButton(String text, String tooltip, EventHandler<ActionEvent> actionEventHandler) {
        super();
        setText(text);
        setTooltip(new Tooltip(tooltip));
        setOnAction(actionEventHandler);
    }
}
