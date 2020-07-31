package fr.lavapower.heartbrowser.widgets.tabs;

import fr.lavapower.heartbrowser.HeartBrowser;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class TabParameter extends GridPane
{
    private ChoiceBox<String> stringChoiceBox;
    private TextField urlField;

    public TabParameter() {
        super();
        Label title = new Label("Paramètres");
        title.setFont(Font.font(20));
        title.setTextAlignment(TextAlignment.CENTER);
        title.setPadding(new Insets(20));

        HBox choice = new HBox(10);
        Label choiceLabel = new Label("Niveau de Log :");
        choiceLabel.setFont(Font.font(15));
        choiceLabel.setAlignment(Pos.CENTER);
        stringChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("INFO", "WARNING", "SEVERE"));
        stringChoiceBox.setValue(HeartBrowser.configuration.getLevelString());
        choice.getChildren().setAll(choiceLabel, stringChoiceBox);
        choice.setAlignment(Pos.CENTER);
        choice.setPadding(new Insets(10));

        HBox home = new HBox(10);
        Label homeLabel = new Label("Accueil :");
        homeLabel.setFont(Font.font(15));
        homeLabel.setAlignment(Pos.CENTER);
        urlField = new TextField();
        urlField.setPromptText("URL");
        urlField.setFont(Font.font(15));
        urlField.setText(HeartBrowser.configuration.home);
        home.getChildren().setAll(homeLabel, urlField);
        home.setAlignment(Pos.CENTER);
        home.setPadding(new Insets(10));

        Separator separator = new Separator();
        separator.setPadding(new Insets(5));

        Label informations = new Label("Créer par LavaPower\nVersion : 1.0.0\nVersion de Base de Données : "+HeartBrowser.configuration.version);
        informations.setFont(Font.font(18));
        informations.setTextAlignment(TextAlignment.CENTER);
        informations.setPadding(new Insets(10));

        setListenersAndEvents();

        add(title, 0, 0);
        add(choice, 0, 1);
        add(home, 0, 2);
        add(separator, 0, 3);
        add(informations, 0, 4);

        setHalignment(title, HPos.CENTER);
        setHalignment(choice, HPos.CENTER);
        setHalignment(home, HPos.CENTER);
        setHalignment(informations, HPos.CENTER);
        setHgrow(separator, Priority.ALWAYS);
    }

    public void setListenersAndEvents() {
        urlField.textProperty().addListener(((observable, oldValue, newValue) -> {
            HeartBrowser.configuration.home = newValue;
        }));

        stringChoiceBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            HeartBrowser.configuration.setLogLevelFromString(newValue);
        }));
    }
}
