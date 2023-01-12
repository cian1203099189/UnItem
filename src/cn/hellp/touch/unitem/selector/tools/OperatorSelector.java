package cn.hellp.touch.unitem.selector.tools;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.ValueSelector;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class OperatorSelector<T> implements ISelector<T> {
    protected final ISelector<?> selector;
    protected final ValueSelector<?> pragma;

    public OperatorSelector(ISelector<?> selector, ValueSelector<?> pragma) {
        this.selector=selector;
        this.pragma=pragma;
    }

    @Override
    public T[] select(Player invoker) {
        T[] l = (T[]) selector.select(invoker);
        for (int i = 0;i< l.length;i++) {
            T location = l[i];
             l[i]=operator(location,invoker);
        }
        return l;
    }

    public abstract T operator(T location,Player invoker);
}
