package cn.hellp.touch.unitem.actuator.entity;

import cn.hellp.touch.unitem.actuator.IActuator;
import cn.hellp.touch.unitem.auxiliary.Number;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DamageEntityActuator implements IActuator {
    @Override
    public @Nullable Object actuate(Object... target) {
        Damageable entity = ((Damageable) target[0]);
        Number g = ((Number) target[1]);
        if(target.length>=3) {
            Entity damager = ((Entity) target[2]);
            entity.damage(g.toDouble(),damager);
        } else {
            entity.damage(g.toDouble());
        }
        return null;
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "damageEntity";
    }
}
