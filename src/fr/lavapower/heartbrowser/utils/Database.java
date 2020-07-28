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
        executeWithoutReturn("CREATE TABLE IF NOT EXISTS configuration(id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, logLevel TEXT, home TEXT,"
                + "version INTEGER);");
        executeWithoutReturn("CREATE TABLE IF NOT EXISTS history(id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, url TEXT, "
                + "date DATETIME DEFAULT CURRENT_TIMESTAMP);");
        executeWithoutReturn("CREATE TABLE IF NOT EXISTS favorites(url TEXT PRIMARY KEY UNIQUE);");
        try {
            if(executeWithReturn("SELECT * FROM configuration;").getFetchSize() == 0)
                executeWithoutReturn("INSERT INTO configuration(logLevel, home, version) VALUES(\"INFO\", \"google.fr\", 1);");

            int version = executeWithReturn("SELECT * FROM configuration;").getInt("version");
            if(version != 1)
                HeartBrowser.logger.log(Level.SEVERE, "Unknown Version of BDD ("+version+")");
        }
        catch(SQLException e) { HeartBrowser.logger.log(Level.SEVERE, "SQL Error", e); }
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