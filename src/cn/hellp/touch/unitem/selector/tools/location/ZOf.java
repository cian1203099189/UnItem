package cn.hellp.touch.unitem.selector.tools.location;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ZOf implements ISelector<Double> {
    private final ISelector<?> loc;

    public ZOf(ISelector<?> selector) {
        loc=selector;
    }

    @Override
    public Double[] select(Player invoker) {
        Location[] location = (Location[]) loc.select(invoker);
        Double[] result = new Double[location.length];
        for (int i = 0; i < location.length; i++) {
            result[i]=location[i].getZ();
        }
        return result;
    }

    @Override
    public String selectorID() {
        return "ZOf";
    }
}
