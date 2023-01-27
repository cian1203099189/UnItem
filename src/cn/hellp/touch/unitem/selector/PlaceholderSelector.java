package cn.hellp.touch.unitem.selector;

import cn.hellp.touch.unitem.app.VarManager;
import org.bukkit.entity.Player;

public class PlaceholderSelector implements ISelector{
    public final String name;
    public final VarManager manager;

    public PlaceholderSelector(String name, VarManager manager) {
        this.name=name;
        this.manager=manager;
    }

    @Override
    public Object[] select(Player invoker) {
        return manager.get(name).select(invoker);
    }

    @Override
    public String selectorID() {
        return "placeholder";
    }
}
