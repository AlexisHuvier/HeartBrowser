package fr.lavapower.heartbrowser.widgets;

import fr.lavapower.heartbrowser.HeartBrowser;
import fr.lavapower.heartbrowser.widgets.tabbuttons.TabBasicButton;
import fr.lavapower.heartbrowser.widgets.tabbuttons.TabBrowserButton;
import fr.lavapower.heartbrowser.widgets.tabbuttons.TabHistoryButton;
import fr.lavapower.heartbrowser.widgets.tabbuttons.TabParameterButton;
import fr.lavapower.heartbrowser.widgets.tabs.TabBrowser;
import fr.lavapower.heartbrowser.windows.QuestionYesCancel;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class TabsList extends VBox
{
    private final ArrayList<TabBrowserButton> buttons = new ArrayList<>();
    private final TabBasicButton plusButtons;
    private final TabParameterButton tabParameterButton;
    private final TabHistoryButton tabHistoryButton;
    public GridPane currentTab = null;
    public final Grid grid;

    public TabsList(Grid grid) {
        super(10);
        this.grid = grid;
        plusButtons = new TabBasicButton("+", "Nouveau Onglet", event -> addTab(HeartBrowser.configuration.home));
        tabParameterButton = new TabParameterButton(this);
        tabHistoryButton = new TabHistoryButton(this);
        setupGrid();
        setupChildren();
        setPadding(new Insets(10));
    }

    private void setupGrid() {
        setOnMouseDragReleased(event -> {
            int index = getChildren().indexOf(event.getGestureSource());
            rotateNodes(index, buttons.size()-1);
        });
    }

    public void rotateNodes(final int index, final int indexOfDropTarget) {
        if (index >= 0 && indexOfDropTarget >= 0) {
            final Node node = getChildren().remove(index);
            getChildren().add(indexOfDropTarget, node);
        }
    }

    private void setupChildren() {
        List<Node> list = new ArrayList<>(buttons);
        list.add(new Separator());
        list.add(plusButtons);
        list.add(tabHistoryButton);
        list.add(tabParameterButton);
        getChildren().setAll(list);
    }

    public void addTab(String url) {
        TabBrowserButton tabBrowserButton = new TabBrowserButton(this, url);
        buttons.add(tabBrowserButton);
        setupChildren();
        selectTab(tabBrowserButton.tabBrowser);
    }

    public void selectTab(GridPane tab) {
        if(currentTab != null)
            grid.getChildren().remove(currentTab);
        grid.add(tab, 1, 0);
        currentTab = tab;
        Grid.setVgrow(tab, Priority.ALWAYS);
        Grid.setHgrow(tab, Priority.ALWAYS);
    }

    public void deleteTab(TabBrowser tabBrowser) {
        if(buttons.size() == 1) {
            QuestionYesCancel closeBrowser = new QuestionYesCancel("Fermeture du dernier onglet",
                    "Vous voulez fermer le dernier onglet.\nVoulez vous fermer le navigateur ?");
            if(closeBrowser.isYes())
                HeartBrowser.close();
            return;
        }
        if(currentTab == tabBrowser) {
            int index = buttons.indexOf(tabBrowser.tabBrowserButton);
            if(index == buttons.size() - 1)
                index -= 1;
            else
                index = 1;
            selectTab(buttons.get(index).tabBrowser);
        }
        buttons.remove(tabBrowser.tabBrowserButton);
        setupChildren();
    }

    public int getTabCount() {
        return buttons.size();
    }
}
