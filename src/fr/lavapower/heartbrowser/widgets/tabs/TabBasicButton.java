package fr.lavapower.heartbrowser.widgets.tabs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TabBasicButton extends TabButton
{
    private final EventHandler<ActionEvent> actionEventHandler;

    public TabBasicButton(TabsList tabsList, String text, EventHandler<ActionEvent> actionEventHandler) {
        super(tabsList);
        setText(text);
        this.actionEventHandler = actionEventHandler;
        setUp();
    }

    public TabBasicButton(TabsList tabsList, EventHandler<ActionEvent> actionEventHandler) {
        super(tabsList);
        this.actionEventHandler = actionEventHandler;
        setUp();
    }

    @Override
    public void setUp() { setOnAction(actionEventHandler); }

}
