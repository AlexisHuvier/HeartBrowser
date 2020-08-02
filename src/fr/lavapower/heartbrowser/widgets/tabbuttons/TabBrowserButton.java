package fr.lavapower.heartbrowser.widgets.tabbuttons;

import fr.lavapower.heartbrowser.widgets.tabs.TabBrowser;
import fr.lavapower.heartbrowser.widgets.TabsList;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

public class TabBrowserButton extends TabButton
{
    public final TabBrowser tabBrowser;
    public final TabsList tabsList;

    public TabBrowserButton(TabsList tabsList, String url) {
        super();
        this.tabsList = tabsList;
        tabBrowser = new TabBrowser(this, url);
        setOnAction(event -> tabsList.selectTab(tabBrowser));

        setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && event.getButton() == MouseButton.SECONDARY) {
                tabsList.deleteTab(tabBrowser);
            }
        });

        setOnDragDetected(event -> {
            startFullDrag();
            event.consume();
        });

        setOnMouseDragReleased(event -> {
            int indexOfDraggingNode = tabsList.getChildren().indexOf(event.getGestureSource());
            int indexOfDropTarget = tabsList.getChildren().indexOf(this);
            tabsList.rotateNodes(indexOfDraggingNode, indexOfDropTarget);
            event.consume();
        });
    }
}
