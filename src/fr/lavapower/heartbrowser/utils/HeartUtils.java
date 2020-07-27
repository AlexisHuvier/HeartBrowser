package fr.lavapower.heartbrowser.utils;

public class HeartUtils
{
    public static String formatUrl(String url) {
        if(url.matches("^[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)"))
        {
            if(!url.contains("http://") && !url.contains("https://"))
                url = "http://"+url;
        }
        else {
            url = "https://www.google.com/search?q="+url+"&ie=UTF-8&oe=UTF-8";
        }
        return url;
    }
}
