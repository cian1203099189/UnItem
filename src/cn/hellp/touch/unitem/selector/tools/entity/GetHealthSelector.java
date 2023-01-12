package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;

public class GetHealthSelector implements ISelector<Double> {
    private final ISelector<?> entities;

    public GetHealthSelector(ISelector<?> entities) {
        this.entities = entities;
    }

    @Override
    public Double[] select(Player invoker) {
        Object[] entities = this.entities.select(invoker);
        Double[] result = new Double[entities.length];
        for (int i = 0;i<entities.length;i++) {
            Damageable damageable=(Damageable)entities[i];
            result[i]=damageable.getHealth();
        }
        return result;
    }

    @Override
    public String selectorID() {
        return "getHealth";
    }
}
