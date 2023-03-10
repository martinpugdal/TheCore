package org.leux.theapi.utils;

public class FileUtils {
    public static boolean isYamlFile(String paramString){
        return StringUtils.endsWith(paramString, "yml", "yaml");
    }
}
