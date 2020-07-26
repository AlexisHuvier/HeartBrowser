package fr.lavapower.heartbrowser.widgets;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebView;

public class Tab extends GridPane
{
    public Tab(String url) {
        super();
        WebView webView = new WebView();
        webView.getEngine().load(url);
        add(webView, 0, 0);
        setVgrow(webView, Priority.ALWAYS);
        setHgrow(webView, Priority.ALWAYS);
    }
}
