package fr.lavapower.heartbrowser.widgets;

import fr.lavapower.heartbrowser.widgets.tabs.TabsList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class Grid extends GridPane
{
    public final TabsList tabsList;
    public Grid() {
        super();

        tabsList = new TabsList(this);
        ScrollPane scrollPane = new ScrollPane(tabsList);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        setHgap(5);
        setVgap(5);
        setVgrow(scrollPane, Priority.ALWAYS);
        add(scrollPane, 0, 0);

        tabsList.addTab("https://www.infowebmaster.fr/outils/mon-user-agent.php#:~:text=Le%20user%20agent%20est%20un,est%20utilisé%20par%20un%20internaute.");
    }
}
