package cn.hellp.touch.unitem.selector.tools;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArraySelector implements ISelector {
    protected ISelector<?>[] selectors = null;
    protected Object[] values = null;

    public ArraySelector(ISelector<?>[] selectors) {
        this.selectors=selectors;
    }
    public ArraySelector(Object... selectors) {
        this.values=selectors;
    }

    @Override
    public Object[] select(Player invoker) {
        if(values!=null) {
            return values;
        }
        List<Object> result = new ArrayList<>();
        for (ISelector<?> selector : selectors) {
            result.addAll(Arrays.asList(selector.select(invoker)));
        }
        return result.toArray();
    }

    @Override
    public String selectorID() {
        return "arrayOf";
    }
}
