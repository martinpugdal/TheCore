package dk.martinersej.theCore.pluginmodule;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import dk.martinersej.theCore.TheCore;
import dk.martinersej.theapi.yaml.YamlConfigurationFile;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Set;

public class PluginModuleManager extends YamlConfigurationFile {

    private static final String PACKAGE_NAME = "dk.martinersej.theCore.pluginmodule.modules";

    private final ClassToInstanceMap<PluginModule> modulesByClass = MutableClassToInstanceMap.create();

    public PluginModuleManager(JavaPlugin instance) {
        super(instance, "modules.yml");

        TheCore.get().getLogger().info("Registering modules...");

        Set<Class<? extends PluginModule>> newModules = findAllModulesExtendingPluginModule();

        setupModuleSection();

        // Update the file with new modules and remove old modules, just in case we updated the plugin
        purgeOldModulesFromConfig(newModules); // Remove old modules from the config
        updateFileWithNewModules(newModules); // Add new modules to the config
    }

    // Reflection to find all classes extending PluginModule
    public static Set<Class<? extends PluginModule>> findAllModulesExtendingPluginModule() {
        Reflections reflections = new Reflections(PACKAGE_NAME);
        return reflections.getSubTypesOf(PluginModule.class);
    }

    private void setupModuleSection() {
        if (!getConfig().contains("modules")) {
            getConfig().createSection("modules");
            save();
        }
    }

    private ConfigurationSection getModuleConfig() {
        return getConfig().getConfigurationSection("modules");
    }

    private void updateFileWithNewModules(Set<Class<? extends PluginModule>> newModules) {
        for (Class<?> moduleClass : newModules) {
            // Get the full package path
            String packageName = moduleClass.getPackage().getName();

            // Extract the part of the package after PACKAGE_NAME (without the class)
            String relativePath = packageName.replace(PACKAGE_NAME + ".", "");

            // Only add the subfolder key if it doesn't exist
            if (!getModuleConfig().contains(relativePath)) {
                getModuleConfig().set(relativePath + ".enabled", false);
            }
        }
        save();
    }

    private void purgeOldModulesFromConfig(Set<Class<? extends PluginModule>> newModules) {
        for (String key : getModuleConfig().getKeys(false)) {

            // Rebuild the full package path from the config key
            String fullPackagePath = PACKAGE_NAME + "." + key;

            boolean found = false;
            for (Class<?> moduleClass : newModules) {
                // Get the package of the module class
                String modulePackage = moduleClass.getPackage().getName();

                // Check if the package matches, not the class
                if (modulePackage.equals(fullPackagePath)) {
                    found = true;
                    break;
                }
            }

            // If no match, remove the key from the config
            if (!found) {
                getConfig().set(key, null);
                TheCore.get().getLogger().info("Removed module from " + key + " in modules.yml.");
            }
        }
        save();
    }

    public void loadModules() {
        for (String key : getModuleConfig().getKeys(false)) {
            // Get the full package path from the config key
            String fullPackagePath = PACKAGE_NAME + "." + key;

            // enabled?
            if (!getModuleConfig().getBoolean(key + ".enabled")) {
                TheCore.get().getLogger().info("Skipping disabled module in " + fullPackagePath + ".");
                continue;
            }

            try {
                // Load all classes in the subpackage
                Reflections reflections = new Reflections(fullPackagePath);
                Set<Class<? extends PluginModule>> moduleClasses = reflections.getSubTypesOf(PluginModule.class);
                for (Class<? extends PluginModule> moduleClass : moduleClasses) {
                    PluginModule module = moduleClass.newInstance();
                    registerModule(module);
                    TheCore.get().getLogger().info("Registered module " + module.getName() + ".");
                }
            } catch (Exception e) {
                TheCore.get().getLogger().severe("Failed to load modules from package " + fullPackagePath + ". Skipping.");
            }
        }
    }

    public void registerModule(PluginModule module) {
        if (this.modulesByClass.containsKey(module.getClass())) {
            TheCore.get().getLogger().severe("Module " + module.getClass() + " has already been registered.");
            return;
        }

        this.modulesByClass.put(module.getClass(), module);
        try {
            module.enableModule();
        } catch (Exception e) {
            TheCore.get().getLogger().severe("Failed to enable module " + module.getName() + ". Disabling.");
            module.disableModule();
        }
    }

    public void disableModules() {
        for (PluginModule module : this.modulesByClass.values()) {
            module.disableModule();
        }
    }

    public <T extends PluginModule> T getModule(Class<T> moduleClass) {
        return this.modulesByClass.getInstance(moduleClass);
    }
}
