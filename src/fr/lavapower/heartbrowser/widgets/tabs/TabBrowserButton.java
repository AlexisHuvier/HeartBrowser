package fr.lavapower.heartbrowser.widgets.tabs;

import javafx.scene.input.MouseButton;

public class TabBrowserButton extends TabButton
{
    public final TabBrowser tabBrowser;

    public TabBrowserButton(TabsList tabsList, String url) {
        super(tabsList);
        tabBrowser = new TabBrowser(this, url);
        setUp();
    }

    @Override
    public void setUp() {
        setOnAction(event -> tabsList.selectTab(tabBrowser));
        setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && event.getButton() == MouseButton.SECONDARY) {
                tabsList.deleteTab(tabBrowser);
            }
        });
    }
}
