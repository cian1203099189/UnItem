package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.auxiliary.GetterSetter;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class EntityVelocityGetterSetter extends GetterSetter<Entity> {
    @NotNull
    @Override
    public String actuatorID() {
        return "setEntityVelocity";
    }

    public EntityVelocityGetterSetter(ISelector<?> target) {
        super(target);
    }

    @Override
    protected Object get(Entity invoker) {
        return invoker.getVelocity();
    }

    @Override
    protected Object set(Entity invoker, Object... value) {
        invoker.setVelocity(((Vector) value[0]));
        return get(invoker);
    }

    @Override
    public String selectorID() {
        return "getEntityVelocity";
    }
}
