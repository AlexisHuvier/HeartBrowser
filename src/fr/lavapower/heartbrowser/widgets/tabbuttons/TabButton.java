package fr.lavapower.heartbrowser.widgets.tabbuttons;

import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class TabButton extends Button
{
    public TabButton() {
        setShape(new Circle(20));
        setMinSize(40, 40);
        setMaxSize(40, 40);
    }
}
