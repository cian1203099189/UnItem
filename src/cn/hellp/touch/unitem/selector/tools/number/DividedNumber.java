package cn.hellp.touch.unitem.selector.tools.number;

import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.auxiliary.Number;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class DividedNumber implements ISelector {
    private final ISelector<?> one;
    private final ISelector<?> two;

    public DividedNumber(ISelector<?> one, ISelector<?> two) {
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
                added = ((Number) o1).divide(((Number) o2));
                result.add(added);
                continue;
            } catch (Exception ignored) {
            }
            try {
                added = ((Vector) o1).divide(((Vector) o2));
                result.add(added);
                continue;
            } catch (Exception ignored) {
            }
            throw new ERROR(o1 + " can't be divided.");
        }
        return result.toArray(new Object[0]);
    }

    @Override
    public String selectorID() {
        return "divided";
    }
}
