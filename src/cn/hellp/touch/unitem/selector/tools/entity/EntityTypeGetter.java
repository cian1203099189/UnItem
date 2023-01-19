package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.auxiliary.Getter;
import org.bukkit.entity.Entity;

public class EntityTypeGetter extends Getter<Entity> {

    @Override
    public String selectorID() {
        return "getEntityType";
    }

    @Override
    protected Object get(Entity invoker) {
        return invoker.getType();
    }
}
