package fr.lavapower.heartbrowser.widgets;

import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class TabButton extends Button
{
    public final Tab tab;
    public final TabsList tabsList;

    public TabButton(TabsList tabsList, String url) {
        super();
        this.tabsList = tabsList;
        tab = new Tab(this, url);
        setShape(new Circle(20));
        setMinSize(40, 40);
        setMaxSize(40, 40);
    }

    public void setUp() {
        setOnAction(event -> tabsList.selectTab(tab));
    }
}
