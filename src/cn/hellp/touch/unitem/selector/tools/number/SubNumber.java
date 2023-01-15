package cn.hellp.touch.unitem.selector.tools.number;

import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.auxiliary.Number;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class SubNumber implements ISelector {
    private final ISelector<?> one;
    private final ISelector<?> two;

    public SubNumber(ISelector<?> one, ISelector<?> two) {
        this.one = one;
        this.two = two;
    }


    @Override
    public Object[] select(Player invoker) {
        Object[] o = one.select(invoker);
        Object[] t = two.select(invoker);
        final int length = Math.min(o.length, t.length);
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Object o1 = o[i];
            if (o1 == null) {
                continue;
            }
            Object o2 = t[i];
            if (o2 == null) {
                continue;
            }
            Object added;
            try {
                added = ((Number) o1).sub(((Number) o2));
                result.add(added);
                continue;
            } catch (Exception ignored) {
            }
            try {
                ((Location) o2).setWorld(((Location) o1).getWorld());
                added = ((Location) o1).subtract(((Location) o2));
                result.add(added);
                continue;
            } catch (Exception ignored) {
            }
            try {
                added = ((Vector) o1).subtract(((Vector) o2));
                result.add(added);
                continue;
            } catch (Exception ignored) {
            }
            throw new ERROR(o1 + " can't be sub.");
        }
        return result.toArray(new Object[0]);
    }

    @Override
    public String selectorID() {
        return "sub";
    }
}
