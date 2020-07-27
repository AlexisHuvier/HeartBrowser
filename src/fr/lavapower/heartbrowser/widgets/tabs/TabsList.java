package fr.lavapower.heartbrowser.widgets.tabs;

import fr.lavapower.heartbrowser.widgets.Grid;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class TabsList extends VBox
{
    private final ArrayList<TabButton> buttons = new ArrayList<>();
    private final TabBasicButton plusButtons;
    public TabBrowser currentTabBrowser = null;
    public final Grid grid;

    public TabsList(Grid grid) {
        super(10);
        this.grid = grid;
        plusButtons = new TabBasicButton(this, "+", event -> addTab("http://google.fr"));
        setupChildren();
        setPadding(new Insets(10));
    }

    private void setupChildren() {
        List<Node> list = new ArrayList<>(buttons);
        list.add(new Separator());
        list.add(plusButtons);
        getChildren().setAll(list);
    }

    public void addTab(String url) {
        TabBrowserButton tabBrowserButton = new TabBrowserButton(this, url);
        buttons.add(tabBrowserButton);
        setupChildren();
        selectTab(tabBrowserButton.tabBrowser);
    }

    public void selectTab(TabBrowser tabBrowser) {
        if(currentTabBrowser != null)
            grid.getChildren().remove(currentTabBrowser);
        grid.add(tabBrowser, 1, 0);
        currentTabBrowser = tabBrowser;
        Grid.setVgrow(tabBrowser, Priority.ALWAYS);
        Grid.setHgrow(tabBrowser, Priority.ALWAYS);
    }


    public ArrayList<TabButton> getButtons() { return buttons; }
}
