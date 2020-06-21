package fr.lavapower.heartbrowser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class BrowserController implements Initializable
{
    @FXML WebView webView;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        webView.getEngine().load("http://google.com");
    }
}
