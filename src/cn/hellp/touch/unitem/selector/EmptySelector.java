package cn.hellp.touch.unitem.selector;

import org.bukkit.entity.Player;

public class EmptySelector implements ISelector {
    @Override
    public Object[] select(Player invoker) {
        return new Object[0];
    }

    @Override
    public String selectorID() {
        return " ";
    }
}
