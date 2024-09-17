package dk.martinersej.theCore.managers;

import dk.martinersej.theapi.utils.StringUtils;
import dk.martinersej.theapi.yaml.YamlConfigurationFile;
import org.bukkit.plugin.java.JavaPlugin;

public class MessageManager extends YamlConfigurationFile {

    public MessageManager(JavaPlugin instance) {
        super(instance, "messages.yml");
    }

    public String getRawMessage(String key) {
        return getConfig().getString(key);
    }

    public String getMessage(String key, Object... args) {
        return StringUtils.format(getRawMessage(key), args);
    }

    public void setMessage(String key, String message) {
        getConfig().set(key, message);
        save();
    }
}
