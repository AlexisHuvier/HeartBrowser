package fr.lavapower.heartbrowser.history;

import fr.lavapower.heartbrowser.HeartBrowser;
import fr.lavapower.heartbrowser.utils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

public class HistoryManager
{
    private final ArrayList<History> histories = new ArrayList<>();

    public HistoryManager(Database database) {
        ResultSet history = database.executeWithReturn("SELECT * FROM history");
        try {
            while(history.next()) {
                histories.add(new History(history.getString("url"), history.getString("date")));
            }
        }
        catch(SQLException e) { HeartBrowser.logger.log(Level.SEVERE, "SQL Error", e); }
        HeartBrowser.logger.log(Level.INFO, "History Loaded");
    }

    public void saveHistory(Database database) {
        database.executeWithoutReturn("DELETE FROM history;");
        for(History history: histories)
            database.executeWithoutReturn("INSERT INTO history(url, date) VALUES (\""+history.getUrl()+"\", \""+history.getDate()+"\");");
        HeartBrowser.logger.log(Level.INFO, "History Saved");
    }

    public void addHistory(String url) { histories.add(new History(url, DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(new Date()))); }
    public void removeHistory(History history) { histories.remove(history); }
    public void clearHistory() { histories.clear(); }
    public ArrayList<History> getHistory() { return histories; }
}
