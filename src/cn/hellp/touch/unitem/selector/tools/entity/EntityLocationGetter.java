package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.auxiliary.Getter;
import org.bukkit.entity.Entity;

public class EntityLocationGetter extends Getter<Entity> {
    @Override
    protected Object get(Entity invoker) {
        return invoker.getLocation();
    }

    @Override
    public String selectorID() {
        return "getEntityLocation";
    }
}
