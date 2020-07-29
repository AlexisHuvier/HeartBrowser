package fr.lavapower.heartbrowser.history;

public class History
{
    private String url;
    private String date;

    public History(String url, String date) {
        this.url = url;
        this.date = date;
    }

    public String getUrl() { return url; }
    public String getDate() { return date; }

    @Override
    public String toString()
    {
        return "History{" + "url='" + url + '\'' + ", date='" + date + "'}";
    }
}
