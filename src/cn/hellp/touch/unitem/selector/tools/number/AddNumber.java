package cn.hellp.touch.unitem.selector.tools.number;

import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AddNumber implements ISelector {
    private final ISelector<?> one;
    private final ISelector<?> two;

    public AddNumber(ISelector<?> one, ISelector<?> two) {
        this.one=one;
        this.two=two;
    }


    @Override
    public Object[] select(Player invoker) {
        Object[] o = one.select(invoker);
        Object[] t = two.select(invoker);
        List<Object> result = new ArrayList<>();
        for(Object o1 : o) {
            if(o1==null) {
                continue;
            }
            for (Object o2 : t) {
                if(o2==null) {
                    continue;
                }
                Object added;
                try {
                    added= Integer.valueOf(((Integer) o1).intValue()+ ((Integer) o2).intValue());
                    result.add(added);
                    continue;
                } catch (Exception ignored) {}
                try {
                    added= Double.valueOf(((Double) o1).doubleValue()+ ((Double) o2).doubleValue());
                    result.add(added);
                    continue;
                } catch (Exception ignored) {}
                try {
                    added= Long.valueOf(((Long) o1).longValue()+ ((Long) o2).longValue());
                    result.add(added);
                    continue;
                } catch (Exception ignored) {}
                try {
                    added= Float.valueOf(((Float) o1).floatValue()+ ((Float) o2).floatValue());
                    result.add(added);
                    continue;
                } catch (Exception ignored) {}
                try {
                    added= ((Short) o1 + (Short) o2);
                    result.add(added);
                    continue;
                } catch (Exception ignored) {}
                try {
                    added= ((Location) o1).add(((Location) o2));
                    result.add(added);
                    continue;
                } catch (Exception ignored) {}
                try {
                    added= ((Vector) o1).add(((Vector) o2));
                    result.add(added);
                    continue;
                } catch (Exception ignored) {}
                try {
                    Method add = o1.getClass().getDeclaredMethod("add",o1.getClass());
                    added=add.invoke(o1,o2);
                    result.add(added);
                    continue;
                }catch (Exception ignored) {}
                throw new ERROR("can't add object "+o1);
            }
        }
        return result.toArray(new Object[0]);
    }

    @Override
    public String selectorID() {
        return "add";
    }

}
