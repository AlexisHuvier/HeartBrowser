package fr.lavapower.heartbrowser.widgets.tabbuttons;

import fr.lavapower.heartbrowser.widgets.TabsList;
import fr.lavapower.heartbrowser.widgets.tabs.TabParameter;

public class TabParameterButton extends TabButton
{
    public final TabParameter tabParameter;

    public TabParameterButton(TabsList tabsList) {
        super();
        setText("P");
        tabParameter = new TabParameter();
        setOnAction(event -> tabsList.selectTab(tabParameter));
    }
}
