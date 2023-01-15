package cn.hellp.touch.unitem.selector.tools.location;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.auxiliary.Number;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ZOf implements ISelector<Number> {
    private final ISelector<?> loc;

    public ZOf(ISelector<?> selector) {
        loc=selector;
    }

    @Override
    public Number[] select(Player invoker) {
        Location[] location = (Location[]) loc.select(invoker);
        Number[] result = new Number[location.length];
        for (int i = 0; i < location.length; i++) {
            result[i]=new Number(location[i].getZ());
        }
        return result;
    }

    @Override
    public String selectorID() {
        return "ZOf";
    }
}
