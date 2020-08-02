package fr.lavapower.heartbrowser.widgets;

import com.sun.javafx.scene.control.skin.ContextMenuContent;
import fr.lavapower.heartbrowser.HeartBrowser;
import fr.lavapower.heartbrowser.utils.FaviconManager;
import fr.lavapower.heartbrowser.widgets.tabs.TabBrowser;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Window;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventTarget;

import java.util.ArrayList;
import java.util.Iterator;

public class Browser extends GridPane
{
    private final WebView view;
    private final WebEngine engine;
    private String linkUrl;

    public Browser() {
        view = new WebView();
        engine = view.getEngine();
        linkUrl = null;
        engine.setUserAgent("HeartBrowser 1.0 - AppleWebKil/555.99 JavaFX 8.0");
        add(view, 0, 0);
        setHgrow(view, Priority.ALWAYS);
        setVgrow(view, Priority.ALWAYS);
    }

    public void setListenersAndEvents(TabBrowser tabBrowser) {
        engine.titleProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue != null && !newValue.isEmpty())
                tabBrowser.tabBrowserButton.setTooltip(new Tooltip(newValue));
        }));

        engine.locationProperty().addListener(((observable, oldValue, newValue) -> {
            if(oldValue != null)
                tabBrowser.backButton.setDisable(false);

            if(newValue != null && !newValue.isEmpty())
            {
                tabBrowser.urlInput.setText(newValue);
                HeartBrowser.historyManager.addHistory(newValue);
                String urlToFavicon = FaviconManager.formatUrlToFavicon(newValue);
                if(tabBrowser.locationMain == null || !tabBrowser.locationMain.equals(urlToFavicon))
                {
                    Image icon = FaviconManager.getFavicon(urlToFavicon);
                    if(icon != null)
                    {
                        ImageView imageView = new ImageView(icon);
                        imageView.setScaleX(1.6);
                        imageView.setScaleY(1.6);
                        tabBrowser.tabBrowserButton.setGraphic(imageView);
                        tabBrowser.locationMain = urlToFavicon;
                    }
                }
            }
        }));

        view.setOnContextMenuRequested(event -> getContextMenu());

        engine.getLoadWorker().stateProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue == Worker.State.SUCCEEDED) {
                Document doc = engine.getDocument();
                NodeList nodeList = doc.getElementsByTagName("a");
                for(int i = 0; i < nodeList.getLength(); i++) {
                    ((EventTarget) nodeList.item(i)).addEventListener("contextmenu", evt -> linkUrl = evt.getCurrentTarget().toString(), false);
                }
            }
        }));
    }



    private void getContextMenu()
    {
        @SuppressWarnings("deprecation")
        final Iterator<Window> windows = Window.impl_getWindows();

        while(windows.hasNext())
        {
            final Window window = windows.next();

            if(window instanceof ContextMenu)
            {
                if(window.getScene() != null && window.getScene().getRoot() != null)
                {
                    Parent root = window.getScene().getRoot();

                    // access to context menu content
                    if(root.getChildrenUnmodifiable().size() > 0)
                    {
                        Node popup = root.getChildrenUnmodifiable().get(0);
                        if(popup.lookup(".context-menu") != null)
                        {
                            Node bridge = popup.lookup(".context-menu");
                            ContextMenuContent cmc = (ContextMenuContent)((Parent)bridge).getChildrenUnmodifiable().get(0);
                            ArrayList<ContextMenuContent.MenuItemContainer> menuItemContainers = new ArrayList<>();

                            VBox itemsContainer = cmc.getItemsContainer();
                            for(Node n : itemsContainer.getChildren())
                            {
                                ContextMenuContent.MenuItemContainer item = (ContextMenuContent.MenuItemContainer)n;
                                if(item.getItem().getText().equals("Ouvrir le lien dans une nouvelle fenêtre")) {
                                    MenuItem openOnglet = new MenuItem("Ouvrir le lien dans un nouvel onglet");
                                    if(linkUrl != null)
                                        openOnglet.setOnAction(event -> HeartBrowser.grid.tabsList.addTab(linkUrl));
                                    menuItemContainers.add(cmc.new MenuItemContainer(openOnglet));
                                }
                                else
                                    menuItemContainers.add(item);
                            }

                            itemsContainer.getChildren().clear();
                            itemsContainer.getChildren().setAll(menuItemContainers);
                        }
                    }
                }
            }
        }
    }

    public WebEngine getEngine() { return engine; }
}
