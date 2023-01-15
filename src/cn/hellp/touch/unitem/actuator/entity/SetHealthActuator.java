package cn.hellp.touch.unitem.actuator.entity;

import cn.hellp.touch.unitem.auxiliary.Number;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SetHealthActuator implements IEntityActuator {
    @Override
    public @Nullable Object actuate(Object... target) {
        IEntityActuator.super.actuate(target);
        Entity entity = ((Entity) target[0]);
        if(entity instanceof Damageable) {
            Damageable damageable = (Damageable) entity;
            Number health = ((Number) target[1]);
            damageable.setHealth(health.toDouble());
        }
        return null;
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "setHealth";
    }
}
