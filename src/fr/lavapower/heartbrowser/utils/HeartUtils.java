package fr.lavapower.heartbrowser.utils;

import fr.lavapower.heartbrowser.HeartBrowser;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.logging.Level;

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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static boolean setTooltipTimers(long openDelay, long visibleDuration, long closeDelay)
    {
        try
        {
            Field f = Tooltip.class.getDeclaredField("BEHAVIOR");
            f.setAccessible(true);


            Class[] classes = Tooltip.class.getDeclaredClasses();
            for (Class clazz : classes)
            {
                if (clazz.getName().equals("javafx.scene.control.Tooltip$TooltipBehavior"))
                {
                    Constructor ctor = clazz.getDeclaredConstructor(Duration.class, Duration.class, Duration.class, boolean.class);
                    ctor.setAccessible(true);
                    Object tooltipBehavior = ctor.newInstance(new Duration(openDelay), new Duration(visibleDuration), new Duration(closeDelay), false);
                    f.set(null, tooltipBehavior);
                    break;
                }
            }
        }
        catch (Exception e)
        {
            HeartBrowser.logger.log(Level.SEVERE, "Error when setup Tooltip Timers", e);
            return false;
        }
        return true;
    }
}
