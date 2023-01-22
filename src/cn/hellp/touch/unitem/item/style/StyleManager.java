package cn.hellp.touch.unitem.item.style;

import cn.hellp.touch.unitem.plugin.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StyleManager {
    private static final Map<String,Style> knownStyles = new HashMap<>();

    public static void register(Style style) {
        knownStyles.put(style.id(), style);
    }

    public static Style get(String id) {
        return knownStyles.get(id);
    }

    public static void clear() {
        knownStyles.clear();
    }

    public static void loadAllStyles() {
        File parent = new File(Main.getResourceFolder("items"),"styles");
        if(!parent.exists()) {
            if(!parent.mkdirs()) {
                throw new RuntimeException("Can't create items/styles folder, please report to the author.");
            }
        }
        for (File file : Objects.requireNonNull(parent.listFiles(file -> file.getName().endsWith(".style")))) {
            YamlConfiguration configuration = new YamlConfiguration();
            try {
                configuration.load(file);
                StyleAttribute styleAttribute = StyleAttribute.getFromString(configuration.getString("attribute"));
                Style style = styleAttribute.create(configuration);
                StyleManager.register(style);
            } catch (IOException | InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
