package cn.hellp.touch.unitem.selector;

import cn.hellp.touch.unitem.app.PlaceholderManager;
import org.bukkit.entity.Player;

public class PlaceholderSelector implements ISelector{
    private final String placeholderName;
    private final PlaceholderManager manager;

    public PlaceholderSelector(String placeholderName, PlaceholderManager manager) {
        this.placeholderName = placeholderName;
        this.manager = manager;
    }

    @Override
    public Object[] select(Player invoker) {
        return manager.get(placeholderName).select(invoker);
    }

    @Override
    public String selectorID() {
        return "placeholder";
    }
}
