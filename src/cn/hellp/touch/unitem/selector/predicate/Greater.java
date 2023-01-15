package cn.hellp.touch.unitem.selector.predicate;

import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.auxiliary.Number;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Greater implements ISelector<Boolean> {
    private final ISelector<?> one;
    private final ISelector<?> two;

    public Greater(ISelector<?> one,ISelector<?> two) {
        this.one=one;
        this.two=two;
    }

    @Override
    public Boolean[] select(Player invoker) {
        List<Boolean> result = new ArrayList<>();
        Object[] objs1 = one.select(invoker);
        Object[] objs2 = two.select(invoker);
        final int length = Math.min(objs1.length, objs2.length);
        for (int i = 0; i < length; i++) {
            Object o1 = objs1[i];
            if (o1 == null) {
                result.add(false);
                continue;
            }
            Object o2 = objs2[i];
            if (o2 == null) {
                result.add(false);
                continue;
            }
            Boolean added;
            try {
                added = ((Number) o1).toDouble() > ((Number) o2).toDouble();
                result.add(added);
                continue;
            } catch (Exception ignored) {
            }
            throw new ERROR("can't use the operator > between " + o1.getClass() + " and " + o2.getClass());
        }
        return result.toArray(new Boolean[0]);
    }

    @Override
    public String selectorID() {
        return "greater";
    }
}
