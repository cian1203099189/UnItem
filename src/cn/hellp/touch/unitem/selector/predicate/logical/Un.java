package cn.hellp.touch.unitem.selector.predicate.logical;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

public class Un implements ISelector<Boolean> {
    public final ISelector<?> selector;

    public Un(ISelector<?> s) {
        selector=s;
    }

    @Override
    public Boolean[] select(Player invoker) {
        Object[] objects = selector.select(invoker);
        Boolean[] result = new Boolean[objects.length];
        for (int i = 0; i < objects.length; i++) {
            result[i]=!((Boolean) objects[i]);
        }
        return result;
    }

    @Override
    public String selectorID() {
        return "un";
    }
}
