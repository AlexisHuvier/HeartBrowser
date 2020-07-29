package fr.lavapower.heartbrowser.history;

import fr.lavapower.heartbrowser.HeartBrowser;
import fr.lavapower.heartbrowser.utils.Database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

public class HistoryManager
{
    private final ArrayList<History> histories = new ArrayList<>();

    public HistoryManager(Database database) {
        ResultSet history = database.executeWithReturn("SELECT * FROM history");
        try {
            while(history.next()) {
                histories.add(new History(history.getString("url"), history.getDate("date")));
            }
        }
        catch(SQLException e) { HeartBrowser.logger.log(Level.SEVERE, "SQL Error", e); }
    }

    public void saveHistory(Database database) {
        database.executeWithoutReturn("DELETE FROM history;");
        for(History history: histories)
            database.executeWithoutReturn("INSERT INTO history(url, date) VALUES (\""+history.url+"\", "+history.date+");");
        HeartBrowser.logger.log(Level.INFO, "History Saved");
    }

    public void addHistory(String url) { histories.add(new History(url, new Date(System.currentTimeMillis()))); }
    public void clearHistory() { histories.clear(); }
    public ArrayList<History> getHistory() { return histories; }
}
