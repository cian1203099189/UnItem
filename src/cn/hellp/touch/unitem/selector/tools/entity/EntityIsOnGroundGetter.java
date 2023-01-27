package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.auxiliary.Getter;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Entity;

public class EntityIsOnGroundGetter extends Getter<Entity> {

    public EntityIsOnGroundGetter(ISelector<?> target) {
        super(target);
    }

    @Override
    protected Object get(Entity invoker) {
        return invoker.isOnGround();
    }

    @Override
    public String selectorID() {
        return "isOnGround";
    }
}
