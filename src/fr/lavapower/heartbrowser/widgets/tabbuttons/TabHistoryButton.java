package fr.lavapower.heartbrowser.widgets.tabbuttons;

import fr.lavapower.heartbrowser.widgets.TabsList;
import fr.lavapower.heartbrowser.widgets.tabs.TabHistory;
import javafx.scene.control.Tooltip;

public class TabHistoryButton extends TabButton
{
    public final TabHistory tabHistory;

    public TabHistoryButton(TabsList tabsList) {
        super();
        setText("H");
        setTooltip(new Tooltip("Historique"));
        tabHistory = new TabHistory();
        setOnAction(event -> {
            tabsList.selectTab(tabHistory);
            tabHistory.update();
        });
    }
}
