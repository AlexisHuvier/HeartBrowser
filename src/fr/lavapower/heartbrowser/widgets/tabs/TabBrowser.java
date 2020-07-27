package fr.lavapower.heartbrowser.widgets.tabs;

import javafx.concurrent.Worker;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TabBrowser extends GridPane
{
    public final WebView view;
    public final TabBrowserButton tabBrowserButton;

    public TabBrowser(TabBrowserButton button, String url) {
        super();
        tabBrowserButton = button;
        view = new WebView();
        setUp(url);
        add(view, 0, 0);
        setVgrow(view, Priority.ALWAYS);
        setHgrow(view, Priority.ALWAYS);
    }

    private void setUp(String url) {
        view.getEngine().setUserAgent("HeartBrowser 1.0 - AppleWebKil/555.99 JavaFX 8.0");
        view.getEngine().getLoadWorker().stateProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue == Worker.State.SUCCEEDED)
                tabBrowserButton.setTooltip(new Tooltip(view.getEngine().getTitle()));
            else
                tabBrowserButton.setTooltip(new Tooltip(getWorkerStateString(newValue)));
        }));

        try {
            String[] urlParts = url.split("/");
            String urlToFavicon;
            if(urlParts.length > 3)
                urlToFavicon = urlParts[0] + "/" + urlParts[1] + "/" + urlParts[2];
            else
                urlToFavicon = url;
            String faviconUrl = String.format("http://www.google.com/s2/favicons?domain_url=%s", URLEncoder.encode(urlToFavicon, "UTF-8"));
            ImageView imageView = new ImageView(new Image(faviconUrl, true));
            imageView.setScaleX(1.6);
            imageView.setScaleY(1.6);
            tabBrowserButton.setGraphic(imageView);
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        view.getEngine().load(url);
    }

    private static String getWorkerStateString(Worker.State state ) {
        switch(state) {
            case READY:
                return "Chargement prêt";
            case FAILED:
                return "Chargement raté";
            case RUNNING:
                return "Chargement en cours";
            case CANCELLED:
                return "Chargement annulé";
            case SCHEDULED:
                return "Chargement prévu";
            case SUCCEEDED:
                return "Chargement réussi";
        }
        return "";
    }
}
