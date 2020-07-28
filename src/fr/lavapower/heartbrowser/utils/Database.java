package fr.lavapower.heartbrowser.utils;

import fr.lavapower.heartbrowser.HeartBrowser;

import java.sql.*;
import java.util.logging.Level;

/*
Structure BDD :
-> Configuration :
  -> logLevel (string)
  -> home (string)
  -> version (integer)
-> History :
  -> path (string)
  -> date (date)
-> Favorites :
  -> path (string)
*/
public class Database
{
    private Connection connection = null;
    private Statement statement = null;

    public Database(String path) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"+path);
            statement = connection.createStatement();
        }
        catch(ClassNotFoundException e) { HeartBrowser.logger.log(Level.SEVERE, "Driver not found", e); }
        catch(SQLException e) { HeartBrowser.logger.log(Level.SEVERE, "SQL Error", e); }
    }

    public void setUp() {
        //TODO
    }

    public ResultSet executeWithReturn(String query) {
        try { return statement.executeQuery(query); }
        catch(SQLException e) { HeartBrowser.logger.log(Level.SEVERE, "SQL Error", e); }
        return null;
    }

    public void executeWithoutReturn(String query) {
        try { statement.executeUpdate(query); }
        catch(SQLException e) { HeartBrowser.logger.log(Level.SEVERE, "SQL Error", e); }
    }

    public void close() {
        try { connection.close(); }
        catch(SQLException e) { HeartBrowser.logger.log(Level.SEVERE, "Cannot close connection or statement", e); }
    }
}