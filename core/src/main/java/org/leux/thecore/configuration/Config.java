package org.leux.thecore.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import org.leux.TheCore;
import org.leux.theapi.enums.DataType;
import org.leux.theapi.utils.ColorUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public enum Config {
    MYSQL_ENABLED("mysql.enabled", Boolean.valueOf(false), DataType.BOOLEAN),
    MYSQL_HOSTNAME("mysql.hostname", "localhost", DataType.STRING),
    MYSQL_PORT("mysql.port", Integer.valueOf(3306), DataType.INTEGER),
    MYSQL_DATABASE("mysql.database", "database", DataType.STRING),
    MYSQL_USERNAME("mysql.username", "username", DataType.STRING),
    MYSQL_PASSWORD("mysql.password", "password", DataType.STRING),
    MYSQL_USE_SSL("mysql.use_ssl", Boolean.valueOf(false), DataType.BOOLEAN),

    SPAWN_COOLDOWN("spawn.cooldown", Integer.valueOf(10), DataType.INTEGER);

    private final String path;

    private Object object;

    private final DataType dataType;

    Config(String path, Object object, DataType dataType) {
        this.path = path;
        this.object = object;
        this.dataType = dataType;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public static void init() {
        File file = new File(TheCore.getInstance().getDataFolder(), "config.yml");
        Config instance = values()[0];
        try {
            instance.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(File file) throws IOException {
        YamlConfiguration config;
        Config[] values = values();
        if (file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
        } else {
            config = new YamlConfiguration();
        }
        try {
            for (Config value : values) {
                String string;
                String bool;
                String i;
                String d;
                List<String> stringList;
                switch (value.getDataType()) {
                    case STRING:
                        string = config.getString(value.path);
                        if (string == null) {
                            value.saveValue(value, config);
                            break;
                        }
                        value.setObject(ColorUtils.getColored(string));
                        break;
                    case BOOLEAN:
                        bool = config.getString(value.path);
                        if (bool == null) {
                            value.saveValue(value, config);
                            break;
                        }
                        value.setObject(Boolean.valueOf(bool));
                        break;
                    case INTEGER:
                        i = config.getString(value.path);
                        if (i == null) {
                            value.saveValue(value, config);
                            break;
                        }
                        value.setObject(Integer.valueOf(i));
                        break;
                    case DOUBLE:
                        d = config.getString(value.path);
                        if (d == null) {
                            value.saveValue(value, config);
                            break;
                        }
                        value.setObject(Double.valueOf(d));
                        break;
                    case STRING_LIST:
                        stringList = config.getStringList(value.path);
                        if (stringList.isEmpty() && config.getString(value.path) == null) {
                            value.saveValue(value, config);
                            stringList = config.getStringList(value.path);
                        }
                        value.setObject(ColorUtils.getColored(stringList));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        config.save(file);
    }

    public void saveValue(Config value, YamlConfiguration config) {
        if (value.path.startsWith("_"))
            return;
        config.set(value.path, value.object);
    }

    public String getString() {
        return (String)this.object;
    }

    public boolean getBoolean() {
        return ((Boolean)this.object).booleanValue();
    }

    public int getInteger() {
        return ((Integer)this.object).intValue();
    }

    public double getDouble() {
        return ((Double)this.object).doubleValue();
    }

    public float getFloat() {
        return ((Float)this.object).floatValue();
    }

    public long getLong() {
        return ((Long)this.object).longValue();
    }

    public List<String> getStringList() {
        return (List<String>)this.object;
    }
}