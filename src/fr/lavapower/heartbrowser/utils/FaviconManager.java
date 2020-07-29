package fr.lavapower.heartbrowser.utils;

import javafx.scene.image.Image;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public class FaviconManager
{
    private static final HashMap<String, Image> icons = new HashMap<>();

    public static String formatUrlToFavicon(String url) {
        String[] urlParts = url.split("/");
        if(urlParts.length > 3)
            return urlParts[0] + "/" + urlParts[1] + "/" + urlParts[2];
        else
            return url;
    }

    public static Image getFavicon(String location) {
        location = formatUrlToFavicon(location);

        if(icons.containsKey(location))
            return icons.get(location);
        else {
            try
            {
                String faviconUrl = String.format("http://www.google.com/s2/favicons?domain_url=%s", URLEncoder.encode(location, "UTF-8"));
                Image icon = new Image(faviconUrl, true);
                icons.put(location, icon);
                return icon;
            }
            catch(UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}
