package cn.hellp.touch.unitem.actuator.entity;

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
            if(target[1] instanceof Double) {
                Double health = ((Double) target[1]);
                damageable.setHealth(health);
            } else {
                Integer integer = ((Integer) target[1]);
                damageable.setHealth(integer);
            }
        }
        return null;
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "setHealth";
    }
}
