package fr.lavapower.heartbrowser.widgets.tabbuttons;

import fr.lavapower.heartbrowser.widgets.tabs.TabBrowser;
import fr.lavapower.heartbrowser.widgets.TabsList;
import javafx.scene.input.MouseButton;

public class TabBrowserButton extends TabButton
{
    public final TabBrowser tabBrowser;

    public TabBrowserButton(TabsList tabsList, String url) {
        super();
        tabBrowser = new TabBrowser(this, url);
        setOnAction(event -> tabsList.selectTab(tabBrowser));
        setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && event.getButton() == MouseButton.SECONDARY) {
                tabsList.deleteTab(tabBrowser);
            }
        });
    }
}
