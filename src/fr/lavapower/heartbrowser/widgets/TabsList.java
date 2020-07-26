package fr.lavapower.heartbrowser.widgets;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TabsList extends VBox
{
    private final ArrayList<TabButton> buttons = new ArrayList<>();
    public Tab currentTab = null;

    public TabsList() {
        super(10);
        getChildren().setAll(buttons);
        setPadding(new Insets(7, 2, 7, 7));
    }

    public void addTab(Grid grid, String url) {
        TabButton tabButton = new TabButton(url);
        tabButton.setUp(grid, this);
        buttons.add(tabButton);
        getChildren().setAll(buttons);
    }

    public ArrayList<TabButton> getButtons() { return buttons; }
}
