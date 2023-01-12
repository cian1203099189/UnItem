package cn.hellp.touch.unitem.selector;

import org.bukkit.entity.Player;

public class ValueSelector<T> implements ISelector<T>{
    protected final T value;

    public ValueSelector(T value) {
        this.value=value;
    }

    @Override
    public T[] select(Player invoker) {
        return (T[]) new Object[] {value};
    }

    @Override
    public String selectorID() {
        return null;
    }
}
