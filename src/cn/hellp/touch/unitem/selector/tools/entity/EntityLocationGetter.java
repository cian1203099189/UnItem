package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.auxiliary.Getter;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Entity;

public class EntityLocationGetter extends Getter<Entity> {
    public EntityLocationGetter(ISelector<?> target) {
        super(target);
    }

    @Override
    protected Object get(Entity invoker) {
        return invoker.getLocation();
    }

    @Override
    public String selectorID() {
        return "getEntityLocation";
    }
}
