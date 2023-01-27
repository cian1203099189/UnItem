package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.auxiliary.GetterSetter;
import cn.hellp.touch.unitem.auxiliary.Number;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityFireTicksGetterSetter extends GetterSetter<Entity> {
    public EntityFireTicksGetterSetter(ISelector<?> target) {
        super(target);
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "setEntityFireTicks";
    }

    @Override
    protected Object get(Entity invoker) {
        return invoker.getFireTicks();
    }

    @Override
    protected Object set(Entity invoker, Object... value) {
        invoker.setFireTicks(((Number) value[0]).toInteger());
        return get(invoker);
    }

    @Override
    public String selectorID() {
        return "getEntityFireTicks";
    }
}
