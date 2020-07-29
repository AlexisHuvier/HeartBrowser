package fr.lavapower.heartbrowser.widgets.tabs;

import fr.lavapower.heartbrowser.HeartBrowser;
import fr.lavapower.heartbrowser.utils.FaviconManager;
import fr.lavapower.heartbrowser.utils.HeartUtils;
import fr.lavapower.heartbrowser.widgets.tabbuttons.TabBrowserButton;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;

public class TabBrowser extends GridPane
{
    private final TextField urlInput;
    private final Button backButton;
    private final Button forwardButton;
    public final WebView view;
    public final TabBrowserButton tabBrowserButton;
    private String locationMain = null;

    public TabBrowser(TabBrowserButton button, String url) {
        super();
        url = HeartUtils.formatUrl(url);

        tabBrowserButton = button;

        view = new WebView();
        view.getEngine().setUserAgent("HeartBrowser 1.0 - AppleWebKil/555.99 JavaFX 8.0");
        view.getEngine().load(url);

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
        add(view, 0, 1, 3, 1);

        setHgap(10);
        setVgrow(view, Priority.ALWAYS);
        setHgrow(view, Priority.ALWAYS);
        setHgrow(urlInput, Priority.ALWAYS);
    }

    private void setListenersAndEvents() {
        view.getEngine().titleProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue != null && !newValue.isEmpty())
                tabBrowserButton.setTooltip(new Tooltip(newValue));
        }));

        view.getEngine().locationProperty().addListener(((observable, oldValue, newValue) -> {
            if(oldValue != null)
                backButton.setDisable(false);

            if(newValue != null && !newValue.isEmpty()) {
                urlInput.setText(newValue);
                HeartBrowser.historyManager.addHistory(newValue);
                String urlToFavicon = FaviconManager.formatUrlToFavicon(newValue);
                if(locationMain == null || !locationMain.equals(urlToFavicon))
                {
                    Image icon = FaviconManager.getFavicon(urlToFavicon);
                    if(icon != null) {
                        ImageView imageView = new ImageView(icon);
                        imageView.setScaleX(1.6);
                        imageView.setScaleY(1.6);
                        tabBrowserButton.setGraphic(imageView);
                        locationMain = urlToFavicon;
                    }
                }
            }
        }));

        urlInput.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                view.getEngine().load(HeartUtils.formatUrl(urlInput.getText()));
        });

        backButton.setOnAction(event -> {
            if( view.getEngine().getHistory().getCurrentIndex() > 0) {
                view.getEngine().getHistory().go(-1);
                forwardButton.setDisable(false);
            }

            if(view.getEngine().getHistory().getCurrentIndex() == 0)
                backButton.setDisable(true);
        });

        forwardButton.setOnAction(event -> {
            if(view.getEngine().getHistory().getCurrentIndex() < view.getEngine().getHistory().getEntries().size() - 1) {
                view.getEngine().getHistory().go(1);
                backButton.setDisable(false);
            }

            if(view.getEngine().getHistory().getCurrentIndex() == view.getEngine().getHistory().getEntries().size() - 1)
                forwardButton.setDisable(true);
        });
    }
}
