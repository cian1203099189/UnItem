package cn.hellp.touch.unitem.selector.tools.number;

import cn.hellp.touch.unitem.auxiliary.Number;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RemainderNumber implements ISelector<Number> {
    private final ISelector<?> one;
    private final ISelector<?> two;

    public RemainderNumber(ISelector<?> one, ISelector<?> two) {
        this.one = one;
        this.two = two;
    }

    @Override
    public Number[] select(Player invoker) {
        Object[] o = one.select(invoker);
        Object[] t = two.select(invoker);
        final int length = Math.min(o.length,t.length);
        List<Number> result = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Object o1 = o[i];
            if(o1==null) {
                continue;
            }
            Object o2 = t[i];
            if(o2==null) {
                continue;
            }
            result.add(((Number) o1).remainder(((Number) o2)));
        }
        return result.toArray(new Number[0]);
    }

    @Override
    public String selectorID() {
        return "remainder";
    }
}
