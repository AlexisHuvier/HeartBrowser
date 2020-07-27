package fr.lavapower.heartbrowser.widgets.tabs;

import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public abstract class TabButton extends Button
{
    public final TabsList tabsList;

    public TabButton(TabsList tabsList) {
        this.tabsList = tabsList;
        setShape(new Circle(20));
        setMinSize(40, 40);
        setMaxSize(40, 40);
    }

    public abstract void setUp();
}
