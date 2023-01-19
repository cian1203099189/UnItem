package cn.hellp.touch.unitem.auxiliary;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Getter<T> implements ISelector{
    private final ISelector<?> target;

    public Getter(ISelector<?> target) {
        this.target = target;
    }

    public Getter() {
        this(null);
    }

    protected abstract Object get(T invoker);

    @Override
    public Object[] select(Player invoker) {
        Object o;
        List<Object> result = new ArrayList<>();
        for (Object player : target.select(invoker)) {
            o= get(((T) player));
            if(o instanceof java.lang.Number) {
                o = new Number(o);
            }
            if(o!=null) {
                result.add(o);
            }
        }
        return result.toArray(new Object[0]);
    }
}
