package fr.lavapower.heartbrowser.widgets.tabs;

import fr.lavapower.heartbrowser.utils.HeartUtils;
import javafx.concurrent.Worker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TabBrowser extends GridPane
{
    private final TextField urlInput;
    public final WebView view;
    public final TabBrowserButton tabBrowserButton;
    private String locationMain = null;

    public TabBrowser(TabBrowserButton button, String url) {
        super();
        tabBrowserButton = button;
        view = new WebView();
        urlInput = new TextField();
        setUp(url);
        add(urlInput, 0, 0);
        add(view, 0, 1);
        setVgrow(view, Priority.ALWAYS);
        setHgrow(view, Priority.ALWAYS);
    }

    private void setUp(String url) {

        view.getEngine().titleProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue != null && !newValue.isEmpty())
                tabBrowserButton.setTooltip(new Tooltip(newValue));
        }));

        view.getEngine().locationProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue != null && !newValue.isEmpty()) {
                urlInput.setText(newValue);
                try {
                    String[] urlParts = newValue.split("/");
                    String urlToFavicon;
                    if(urlParts.length > 3)
                        urlToFavicon = urlParts[0] + "/" + urlParts[1] + "/" + urlParts[2];
                    else
                        urlToFavicon = newValue;
                    if(locationMain == null || !locationMain.equals(urlToFavicon))
                    {
                        String faviconUrl = String.format("http://www.google.com/s2/favicons?domain_url=%s", URLEncoder.encode(urlToFavicon, "UTF-8"));
                        ImageView imageView = new ImageView(new Image(faviconUrl, true));
                        imageView.setScaleX(1.6);
                        imageView.setScaleY(1.6);
                        tabBrowserButton.setGraphic(imageView);
                        locationMain = urlToFavicon;
                    }
                }
                catch(UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
        }));

        urlInput.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                view.getEngine().load(HeartUtils.formatUrl(urlInput.getText()));
        });

        view.getEngine().setUserAgent("HeartBrowser 1.0 - AppleWebKil/555.99 JavaFX 8.0");

        view.getEngine().load(url);
        urlInput.setText(url);
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
