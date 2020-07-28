package fr.lavapower.heartbrowser.windows;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class QuestionYesCancel
{
    private final Alert alert;
    private final ButtonType yesButtonType;
    private final ButtonType cancelButtonType;

    public QuestionYesCancel(String title, String content) {
        yesButtonType = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert = new Alert(Alert.AlertType.NONE, content, yesButtonType, cancelButtonType);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public boolean isYes() {
        return alert.getResult() == yesButtonType;
    }

    public boolean isCancel() {
        return alert.getResult() == cancelButtonType;
    }
}
