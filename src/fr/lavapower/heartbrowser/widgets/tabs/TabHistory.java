package fr.lavapower.heartbrowser.widgets.tabs;

import fr.lavapower.heartbrowser.HeartBrowser;
import fr.lavapower.heartbrowser.history.History;
import fr.lavapower.heartbrowser.widgets.tabbuttons.TabBrowserButton;
import fr.lavapower.heartbrowser.widgets.tabbuttons.TabHistoryButton;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class TabHistory extends GridPane
{
    public final TableView<History> historyTableView;
    public final Button clearAllButton;

    public TabHistory() {
        super();

        Label title = new Label("Historique");
        title.setFont(Font.font(20));
        title.setTextAlignment(TextAlignment.CENTER);
        title.setPadding(new Insets(10));

        clearAllButton = new Button("Tout Effacer");
        clearAllButton.setFont(Font.font(15));
        clearAllButton.setPadding(new Insets(5));

        historyTableView = new TableView<>();
        TableColumn<History, String> urlCol = new TableColumn<>("URL");
        TableColumn<History, String> dateCol = new TableColumn<>("Date");
        urlCol.setCellValueFactory(new PropertyValueFactory<>("url"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        historyTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        historyTableView.getColumns().setAll(urlCol, dateCol);

        historyTableView.setPadding(new Insets(5));

        setListenersAndEvents();

        add(title, 0, 0);
        add(historyTableView, 0, 1);
        add(clearAllButton, 0, 2);
        setHgrow(historyTableView, Priority.ALWAYS);
        setVgrow(historyTableView, Priority.ALWAYS);
        setHalignment(clearAllButton, HPos.CENTER);
        setHalignment(title, HPos.CENTER);
    }

    public void update() {
        historyTableView.setItems(FXCollections.observableArrayList(HeartBrowser.historyManager.getHistory()));
    }

    public void setListenersAndEvents() {
        historyTableView.setOnMousePressed(event -> {
            History item = historyTableView.getSelectionModel().getSelectedItem();
            if(item != null && event.getClickCount() == 2) {
                if(event.getButton() == MouseButton.PRIMARY)
                    HeartBrowser.grid.tabsList.addTab(item.getUrl());
                else if(event.getButton() == MouseButton.SECONDARY) {
                    HeartBrowser.historyManager.removeHistory(item);
                    update();
                }
            }
        });

        clearAllButton.setOnAction(event -> {
            HeartBrowser.historyManager.clearHistory();
            update();
        });
    }
}
