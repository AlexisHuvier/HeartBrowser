package fr.lavapower.heartbrowser.widgets;

import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TabsList extends VBox
{
    private final ArrayList<TabButton> buttons = new ArrayList<>();
    public Tab currentTab = null;
    public final Grid grid;

    public TabsList(Grid grid) {
        super(10);
        this.grid = grid;
        getChildren().setAll(buttons);
        setPadding(new Insets(10));
    }

    public void addTab(String url) {
        TabButton tabButton = new TabButton(this, url);
        tabButton.setUp();
        buttons.add(tabButton);
        getChildren().setAll(buttons);
        selectTab(tabButton.tab);
    }

    public void selectTab(Tab tab) {
        if(currentTab != null)
            grid.getChildren().remove(currentTab);
        grid.add(tab, 1, 0);
        currentTab = tab;
        Grid.setVgrow(tab, Priority.ALWAYS);
        Grid.setHgrow(tab, Priority.ALWAYS);
    }

    public ArrayList<TabButton> getButtons() { return buttons; }
}
