package fr.lavapower.heartbrowser.widgets.tabbuttons;

import fr.lavapower.heartbrowser.widgets.TabsList;
import fr.lavapower.heartbrowser.widgets.tabs.TabHistory;

public class TabHistoryButton extends TabButton
{
    public final TabHistory tabHistory;

    public TabHistoryButton(TabsList tabsList) {
        super();
        setText("H");
        tabHistory = new TabHistory();
        setOnAction(event -> tabsList.selectTab(tabHistory));
    }
}
