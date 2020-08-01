package fr.lavapower.heartbrowser.widgets.tabs;

import com.sun.javafx.scene.control.skin.ContextMenuContent;
import fr.lavapower.heartbrowser.HeartBrowser;
import fr.lavapower.heartbrowser.utils.FaviconManager;
import fr.lavapower.heartbrowser.utils.HeartUtils;
import fr.lavapower.heartbrowser.widgets.tabbuttons.TabBrowserButton;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.Iterator;

public class TabBrowser extends GridPane
{
    private final TextField urlInput;
    private final Button backButton;
    private final Button forwardButton;
    private ContextMenu contextMenu;
    public final WebView view;
    public final TabBrowserButton tabBrowserButton;
    private String locationMain = null;

    public TabBrowser(TabBrowserButton button, String url)
    {
        super();
        url = HeartUtils.formatUrl(url);

        tabBrowserButton = button;

        view = new WebView();
        view.getEngine().setUserAgent("HeartBrowser 1.0 - AppleWebKil/555.99 JavaFX 8.0");
        view.getEngine().load(url);
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
        add(view, 0, 1, 3, 1);

        setHgap(10);
        setVgrow(view, Priority.ALWAYS);
        setHgrow(view, Priority.ALWAYS);
        setHgrow(urlInput, Priority.ALWAYS);
    }

    private void setListenersAndEvents()
    {
        view.getEngine().titleProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue != null && !newValue.isEmpty())
                tabBrowserButton.setTooltip(new Tooltip(newValue));
        }));

        view.getEngine().locationProperty().addListener(((observable, oldValue, newValue) -> {
            if(oldValue != null)
                backButton.setDisable(false);

            if(newValue != null && !newValue.isEmpty())
            {
                urlInput.setText(newValue);
                HeartBrowser.historyManager.addHistory(newValue);
                String urlToFavicon = FaviconManager.formatUrlToFavicon(newValue);
                if(locationMain == null || !locationMain.equals(urlToFavicon))
                {
                    Image icon = FaviconManager.getFavicon(urlToFavicon);
                    if(icon != null)
                    {
                        ImageView imageView = new ImageView(icon);
                        imageView.setScaleX(1.6);
                        imageView.setScaleY(1.6);
                        tabBrowserButton.setGraphic(imageView);
                        locationMain = urlToFavicon;
                    }
                }
            }
        }));

        view.setOnContextMenuRequested(event -> getContextMenu());

        urlInput.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER)
                view.getEngine().load(HeartUtils.formatUrl(urlInput.getText()));
        });

        backButton.setOnAction(event -> {
            if(view.getEngine().getHistory().getCurrentIndex() > 0)
            {
                view.getEngine().getHistory().go(-1);
                forwardButton.setDisable(false);
            }

            if(view.getEngine().getHistory().getCurrentIndex() == 0)
                backButton.setDisable(true);
        });

        forwardButton.setOnAction(event -> {
            if(view.getEngine().getHistory().getCurrentIndex() < view.getEngine().getHistory().getEntries().size() - 1)
            {
                view.getEngine().getHistory().go(1);
                backButton.setDisable(false);
            }

            if(view.getEngine().getHistory().getCurrentIndex() == view.getEngine().getHistory().getEntries().size() - 1)
                forwardButton.setDisable(true);
        });
    }

    private void createContextMenu()
    {
        contextMenu = new ContextMenu();
        MenuItem reload = new MenuItem("Recharger");
        reload.setOnAction(event -> view.getEngine().reload());
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
                                    System.out.println(item.getItem().getOnAction());
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
}
