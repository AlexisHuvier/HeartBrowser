package fr.lavapower.heartbrowser.widgets.tabs;

import fr.lavapower.heartbrowser.utils.HeartUtils;
import fr.lavapower.heartbrowser.widgets.Browser;
import fr.lavapower.heartbrowser.widgets.tabbuttons.TabBrowserButton;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;

public class TabBrowser extends GridPane
{
    public final TextField urlInput;
    public final Button backButton;
    public final Button forwardButton;
    public ContextMenu contextMenu;
    public final Browser browser;
    public final TabBrowserButton tabBrowserButton;
    public String locationMain = null;

    public TabBrowser(TabBrowserButton button, String url)
    {
        super();
        url = HeartUtils.formatUrl(url);

        tabBrowserButton = button;

        browser = new Browser();
        browser.getEngine().load(url);
        createContextMenu();

        urlInput = new TextField();
        urlInput.setText(url);
        urlInput.setFont(Font.font(13));
        urlInput.setPadding(new Insets(5));
        urlInput.setPromptText("Entrr une recherche ou une adresse web");

        backButton = new Button("<");
        backButton.setDisable(true);
        urlInput.setFont(Font.font(13));

        forwardButton = new Button(">");
        forwardButton.setDisable(true);
        urlInput.setFont(Font.font(13));

        setListenersAndEvents();

        add(backButton, 0, 0);
        add(forwardButton, 1, 0);
        add(urlInput, 2, 0);
        add(browser, 0, 1, 3, 1);

        setHgap(10);
        setVgrow(browser, Priority.ALWAYS);
        setHgrow(browser, Priority.ALWAYS);
        setHgrow(urlInput, Priority.ALWAYS);
    }

    private void setListenersAndEvents()
    {
        browser.setListenersAndEvents(this);

        urlInput.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER)
                browser.getEngine().load(HeartUtils.formatUrl(urlInput.getText()));
        });

        backButton.setOnAction(event -> {
            if(browser.getEngine().getHistory().getCurrentIndex() > 0)
            {
                browser.getEngine().getHistory().go(-1);
                forwardButton.setDisable(false);
            }

            if(browser.getEngine().getHistory().getCurrentIndex() == 0)
                backButton.setDisable(true);
        });

        forwardButton.setOnAction(event -> {
            if(browser.getEngine().getHistory().getCurrentIndex() < browser.getEngine().getHistory().getEntries().size() - 1)
            {
                browser.getEngine().getHistory().go(1);
                backButton.setDisable(false);
            }

            if(browser.getEngine().getHistory().getCurrentIndex() == browser.getEngine().getHistory().getEntries().size() - 1)
                forwardButton.setDisable(true);
        });
    }

    private void createContextMenu()
    {
        contextMenu = new ContextMenu();
        MenuItem reload = new MenuItem("Recharger");
        reload.setOnAction(event -> browser.getEngine().reload());
    }
}
