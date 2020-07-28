package fr.lavapower.heartbrowser.utils;

import fr.lavapower.heartbrowser.HeartBrowser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class Configuration
{
    public Level logLevel;
    public String home;
    public int version;

    public Configuration(Database db) {
        ResultSet configuration = db.executeWithReturn("SELECT * FROM configuration;");
        try {
            setLogLevelFromString(configuration.getString("logLevel"));
            home = configuration.getString("home");
            version = configuration.getInt("version");
        }
        catch(SQLException e) { HeartBrowser.logger.log(Level.SEVERE, "SQL Error", e); }
        HeartBrowser.logger.log(Level.INFO, "Configuration Loaded");
    }

    public void setLogLevelFromString(String level) {
        switch(level) {
            case "INFO":
                logLevel = Level.INFO;
                break;
            case "WARNING":
                logLevel = Level.WARNING;
                break;
            case "SEVERE":
                logLevel = Level.SEVERE;
                break;
            default:
                break;
        }
        HeartBrowser.logger.setLevel(logLevel);
    }

    public String getLevelString() {
        if(Level.INFO.equals(logLevel))
        {
            return "INFO";
        }
        else if(Level.WARNING.equals(logLevel))
        {
            return "WARNING";
        }
        else if(Level.SEVERE.equals(logLevel))
        {
            return "SEVERE";
        }
        return "INFO";
    }

    public void saveConfig(Database db) {
        db.executeWithoutReturn("UPDATE configuration SET logLevel = \"" + getLevelString() + "\", home=\"" + home + "\", version = "+version+";");
        HeartBrowser.logger.log(Level.INFO, "Configuration Saved");
    }
}
