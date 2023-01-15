package cn.hellp.touch.unitem.selector.tools;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.ValueSelector;
import cn.hellp.touch.unitem.auxiliary.Number;
import org.bukkit.entity.Player;

public class GetFromIndexSelector<t> extends ValueSelector<t> {
    private final ISelector<?> selector;
    private final ValueSelector<?> index;


    public GetFromIndexSelector(ISelector<?> selector,ValueSelector<?> index) {
        super(null);
        this.selector=selector;
        this.index=index;
    }

    @Override
    public t[] select(Player invoker) {
        t[] ts = (t[]) selector.select(invoker);
        return (t[]) new Object[] {ts[((Number) index.select(invoker)[0]).toInteger()]};
    }

    @Override
    public String selectorID() {
        return "getFromIndex";
    }
}
