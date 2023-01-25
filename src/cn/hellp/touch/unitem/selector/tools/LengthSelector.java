package cn.hellp.touch.unitem.selector.tools;

import cn.hellp.touch.unitem.auxiliary.Number;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

public class LengthSelector implements ISelector<Number> {
    private final ISelector<?> selector;

    public LengthSelector(ISelector<?> selector) {
        this.selector = selector;
    }

    @Override
    public Number[] select(Player invoker) {
        return new Number[] {
                new Number(selector.select(invoker).length)
        };
    }

    @Override
    public String selectorID() {
        return "lengthOf";
    }
}
