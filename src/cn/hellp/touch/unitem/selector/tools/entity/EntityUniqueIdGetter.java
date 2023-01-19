package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.auxiliary.Getter;
import org.bukkit.entity.Entity;

public class EntityUniqueIdGetter extends Getter<Entity> {
    @Override
    protected Object get(Entity invoker) {
        return invoker.getUniqueId();
    }

    @Override
    public String selectorID() {
        return "getEntityUUID";
    }
}
