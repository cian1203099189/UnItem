package cn.hellp.touch.unitem.support;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public enum PlaceholderAPISupport {
    INSTANCE;

    private boolean isLoaded;

    public boolean init() {
        return isLoaded = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public String parse(Player player,String text) {
        if(!isLoaded) {
            throw new RuntimeException("Can't use placeholderAPI about without PlaceholderAPI plugin.");
        }
        return PlaceholderAPI.setPlaceholders(player, text);
    }
}
