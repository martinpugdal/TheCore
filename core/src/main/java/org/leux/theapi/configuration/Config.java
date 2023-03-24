package org.leux.theapi.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import org.leux.TheCore;
import org.leux.theapi.enums.DataType;
import org.leux.theapi.utils.ColorUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Config {

    private final String path;
    private Object object;
    private final DataType dataType;

    private Config(String path, Object object, DataType dataType) {
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
//        Config instance = values()[0];
//
//        try {
//            instance.load(file);
//        } catch (Exception var3) {
//            var3.printStackTrace();
//        }
    }

    public void load(File file) throws IOException {
        Config[] values = new Config[]{new Config("e", "e", DataType.BOOLEAN)};
        YamlConfiguration config;
        if (file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
        } else {
            config = new YamlConfiguration();
        }

        try {
            Config[] configs = values;
            int configLength = values.length;
            for(int j = 0; j < configLength; ++j) {
                Config value = configs[j];
                switch (value.getDataType()) {
                    case STRING:
                        String string = config.getString(value.path);
                        if (string == null) {
                            value.saveValue(value, config);
                        } else {
                            value.setObject(ColorUtils.getColored(string));
                        }
                        break;
                    case BOOLEAN:
                        String bool = config.getString(value.path);
                        if (bool == null) {
                            value.saveValue(value, config);
                        } else {
                            value.setObject(Boolean.valueOf(bool));
                        }
                        break;
                    case INTEGER:
                        String i = config.getString(value.path);
                        if (i == null) {
                            value.saveValue(value, config);
                        } else {
                            value.setObject(Integer.valueOf(i));
                        }
                        break;
                    case DOUBLE:
                        String d = config.getString(value.path);
                        if (d == null) {
                            value.saveValue(value, config);
                        } else {
                            value.setObject(Double.valueOf(d));
                        }
                        break;
                    case STRING_LIST:
                        List<String> stringList = config.getStringList(value.path);
                        if (stringList.isEmpty() && config.getString(value.path) == null) {
                            value.saveValue(value, config);
                            stringList = config.getStringList(value.path);
                        }

                        value.setObject(ColorUtils.getColored(stringList));
                }
            }
        } catch (Exception var13) {
            var13.printStackTrace();
        }

        config.save(file);
    }

    public void saveValue(Config value, YamlConfiguration config) {
        if (!value.path.startsWith("_")) {
            config.set(value.path, value.object);
        }
    }

    public String getString() {
        return (String)this.object;
    }

    public boolean getBoolean() {
        return (Boolean)this.object;
    }

    public int getInteger() {
        return (Integer)this.object;
    }

    public double getDouble() {
        return (Double)this.object;
    }

    public float getFloat() {
        return (Float)this.object;
    }

    public long getLong() {
        return (Long)this.object;
    }

    public List<String> getStringList() {
        return (List)this.object;
    }
}
