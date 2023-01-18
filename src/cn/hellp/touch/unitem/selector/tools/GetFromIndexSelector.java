package cn.hellp.touch.unitem.selector.tools;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.ValueSelector;
import cn.hellp.touch.unitem.auxiliary.Number;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GetFromIndexSelector extends ValueSelector {
    private final ISelector<?> selector;
    private final ISelector<?> index;


    public GetFromIndexSelector(ISelector<?> selector,ISelector<?> index) {
        super(null);
        this.selector=selector;
        this.index=index;
    }

    @Override
    public Object[] select(Player invoker) {
        List<Object> result = new ArrayList<>();
        Object[] objs = selector.select(invoker);
        for (Object o : index.select(invoker)) {
            result.add(objs[((Number) o).toInteger()]);
        }
        return result.toArray(new Object[0]);
    }

    @Override
    public String selectorID() {
        return "getFromIndex";
    }
}
