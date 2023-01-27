package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.auxiliary.Getter;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Entity;

public class EntityTypeGetter extends Getter<Entity> {

    public EntityTypeGetter(ISelector<?> target) {
        super(target);
    }

    @Override
    public String selectorID() {
        return "getEntityType";
    }

    @Override
    protected Object get(Entity invoker) {
        return invoker.getType();
    }
}
