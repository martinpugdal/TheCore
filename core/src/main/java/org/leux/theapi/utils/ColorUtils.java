package org.leux.theapi.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class ColorUtils {

    private ColorUtils() {
        throw new IllegalStateException("Utility class, cannot be instantiated");
    }

    public static String[] getColored(String... stringList){
        if(stringList == null) return null;
        for(int i = 0; i< stringList.length;i++)
            stringList[i] = getColored(stringList[i]);
        return stringList;
    }

    public static List<String> getColored(List<String> stringList){
        if(stringList == null) return null;
        for(int i = 0; i< stringList.size();i++)
            stringList.set(i, getColored(stringList.get(i)));
        return stringList;
    }

    public static String getColored(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
