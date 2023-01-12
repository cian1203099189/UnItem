package cn.hellp.touch.unitem.selector.tools;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

public class ToStringSelector implements ISelector<String> {
    private final ISelector<?> selector;

    public ToStringSelector(ISelector<?> selector) {
        this.selector = selector;
    }

    @Override
    public String[] select(Player invoker) {
        Object[] objects = selector.select(invoker);
        String[] result = new String[objects.length];
        for (int i = 0; i < objects.length; i++) {
            result[i]=objects[i].toString();
        }
        return result;
    }

    @Override
    public String selectorID() {
        return "toString";
    }
}
