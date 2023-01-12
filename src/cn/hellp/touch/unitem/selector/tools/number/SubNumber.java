package cn.hellp.touch.unitem.selector.tools.number;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SubNumber implements ISelector {
    private final ISelector<?> one;
    private final ISelector<?> two;

    public SubNumber(ISelector<?> one, ISelector<?> two) {
        this.one=one;
        this.two=two;
    }


    @Override
    public Object[] select(Player invoker) {
        Object[] o = one.select(invoker);
        Object[] t = two.select(invoker);
        List<Object> result = new ArrayList<>();
        for(Object o1 : o) {
            for (Object o2 : t) {
                Object added;
                try {
                    added= Integer.valueOf(((Integer) o1).intValue()- ((Integer) o2).intValue());
                    result.add(added);
                    continue;
                } catch (Exception ignored) {}
                try {
                    added= Double.valueOf(((Double) o1).doubleValue()- ((Double) o2).doubleValue());
                    result.add(added);
                    continue;
                } catch (Exception ignored) {}
                try {
                    added= Long.valueOf(((Long) o1).longValue()- ((Long) o2).longValue());
                    result.add(added);
                    continue;
                } catch (Exception ignored) {}
                try {
                    added= Float.valueOf(((Float) o1).floatValue()- ((Float) o2).floatValue());
                    result.add(added);
                    continue;
                } catch (Exception ignored) {}
                throw new RuntimeException(o1+" is not a number");
            }
        }
        return result.toArray(new Object[0]);
    }

    @Override
    public String selectorID() {
        return "sub";
    }
}
