package fr.lavapower.heartbrowser.history;

import java.sql.Date;

public class History
{
    public String url;
    public Date date;

    public History(String url, Date date) {
        this.url = url;
        this.date = date;
    }
}
