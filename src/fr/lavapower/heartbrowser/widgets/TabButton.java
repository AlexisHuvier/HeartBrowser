package fr.lavapower.heartbrowser.widgets;

import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;

public class TabButton extends Button
{
    private final Tab tab;

    public TabButton(String url) {
        super();
        tab = new Tab(url);

        setShape(new Circle(20));
        setMinSize(40, 40);
        setMaxSize(40, 40);
    }

    public void setUp(Grid grid, TabsList tabsList) {
        setOnAction(event -> {
            if(tabsList.currentTab != null)
                grid.getChildren().remove(tabsList.currentTab);
            grid.add(tab, 2, 0);
            tabsList.currentTab = tab;
            Grid.setVgrow(tab, Priority.ALWAYS);
            Grid.setHgrow(tab, Priority.ALWAYS);
        });
    }
}
