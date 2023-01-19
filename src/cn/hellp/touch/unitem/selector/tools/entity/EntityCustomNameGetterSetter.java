package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.auxiliary.GetterSetter;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityCustomNameGetterSetter extends GetterSetter<Entity> {
    @NotNull
    @Override
    public String actuatorID() {
        return "setEntityCustomName";
    }

    @Override
    protected Object get(Entity invoker) {
        return invoker.getCustomName();
    }

    @Override
    protected Object set(Entity invoker, Object... value) {
        invoker.setCustomName(((String) value[0]));
        return get(invoker);
    }

    @Override
    public String selectorID() {
        return "getEntityCustomName";
    }
}
