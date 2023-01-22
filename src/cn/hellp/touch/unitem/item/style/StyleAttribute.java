package cn.hellp.touch.unitem.item.style;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public enum StyleAttribute {
    DYNAMIC {
        @Override
        public Style create(FileConfiguration raw) {
            DynamicStyle dynamicStyle = new DynamicStyle(raw.getString("name"));
            for (String text : raw.getStringList("texts")) {
                dynamicStyle.addStyle(Style.create(text));
            }
            return dynamicStyle;
        }
    },
    NORMAL {
        @Override
        public Style create(FileConfiguration raw) {
            String id = raw.getString("name");
            Object text = raw.get("texts");
            if(text instanceof List) {
                return new NormalStyle(id, ((String) ((List<?>) text).get(0)));
            } else {
                return new NormalStyle(id, text.toString());
            }
        }
    };

    public static StyleAttribute getFromString(String s) {
        return valueOf(s.trim().toUpperCase());
    }

    public abstract Style create(FileConfiguration raw);
}
