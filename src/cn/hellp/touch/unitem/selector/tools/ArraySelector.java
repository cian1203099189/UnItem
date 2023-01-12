package cn.hellp.touch.unitem.selector.tools;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArraySelector<T> implements ISelector<T> {
    protected ISelector<?>[] selectors;

    public ArraySelector(ISelector<?>[] selectors) {
        this.selectors=selectors;
    }

    @Override
    public T[] select(Player invoker) {
        List<T> result = new ArrayList<>();
        for (ISelector<?> selector : selectors) {
            for (Object obj : selector.select(invoker)) {
                result.add((T) obj);
            }
        }
        return (T[]) result.toArray();
    }

    @Override
    public String selectorID() {
        return "arrayOf";
    }
}
