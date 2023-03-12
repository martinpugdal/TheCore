package org.leux.theapi.utils;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class FileUtils {
    public static boolean isYamlFile(String paramString){
        return StringUtils.endsWith(paramString, "yml", "yaml");
    }

    /**
     * Copy an embedded file to another location
     *
     * @param inFile - the name of embedded file to be copied
     * @param outFile - the file where specified embedded file should be copied
     */
    public static void copyFile(JavaPlugin plugin, final String inFile, final File outFile) {
        outFile.getParentFile().mkdirs(); // Create output folder

        // Copy contents of inFile to outFile
        try (final InputStream input = plugin.getResource(inFile); final OutputStream output = Files.newOutputStream(outFile.toPath())) {
            final byte[] buf = new byte[1024];
            int number;
            while ((number = input.read(buf)) > 0)
                output.write(buf, 0, number);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
