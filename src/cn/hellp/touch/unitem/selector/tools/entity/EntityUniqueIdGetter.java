package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.auxiliary.Getter;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Entity;

public class EntityUniqueIdGetter extends Getter<Entity> {
    @Override
    protected Object get(Entity invoker) {
        return invoker.getUniqueId();
    }
    public EntityUniqueIdGetter(ISelector<?> target) {
        super(target);
    }

    @Override
    public String selectorID() {
        return "getEntityUUID";
    }


}
