package dk.martinersej.theCore.pluginmodule;

import dk.martinersej.theCore.TheCore;
import dk.martinersej.theapi.yaml.YamlConfigurationFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC) // Public constructor for reflection to work
public abstract class PluginModule {

    private YamlConfigurationFile moduleFile;
    @Setter(AccessLevel.PACKAGE)
    private boolean enabled;

    protected boolean requiresConfig() {
        return false;
    }

    public FileConfiguration getConfig() {
        return this.moduleFile.getConfig();
    }

    protected abstract void onEnable();

    protected abstract void onDisable();

    public String getName() {
        if (this.getClass().getSimpleName().toLowerCase().endsWith("module")) {
            return this.getClass().getSimpleName().substring(0, this.getClass().getSimpleName().length() - 6);
        }
        return this.getClass().getSimpleName();
    }

    protected void reload() {
        onDisable();
        onEnable();
    }

    public void reload(boolean reloadConfig) {
        if (reloadConfig && requiresConfig()) {
            moduleFile.reload();
        }
        reload();
    }

    void enableModule() {
        if (this.enabled) return;
        this.enabled = true;

        if (this.requiresConfig()) {
            this.moduleFile = new YamlConfigurationFile(TheCore.get(), "modules/" + this.getName().toLowerCase() + ".yml");
        }
        this.onEnable();

        Bukkit.getLogger().fine(String.format("Module \"%s\" has been enabled.", this.getName()));
    }

    public void disableModule() {
        if (!this.enabled) return;
        this.enabled = false;

        this.onDisable();
        if (this.requiresConfig() && this.moduleFile != null) {
            this.moduleFile.save();
        }

        Bukkit.getLogger().warning(String.format("Module \"%s\" has been disabled.", this.getName()));
    }

    void reloadModule() {
        this.disableModule();
        this.enableModule();
    }
}
