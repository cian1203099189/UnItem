package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.auxiliary.Number;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;

public class GetHealthSelector implements ISelector<Number> {
    private final ISelector<?> entities;

    public GetHealthSelector(ISelector<?> entities) {
        this.entities = entities;
    }

    @Override
    public Number[] select(Player invoker) {
        Object[] entities = this.entities.select(invoker);
        Number[] result = new Number[entities.length];
        for (int i = 0;i<entities.length;i++) {
            Damageable damageable=(Damageable)entities[i];
            result[i]= new Number(damageable.getHealth());
        }
        return result;
    }

    @Override
    public String selectorID() {
        return "getHealth";
    }
}
