package cn.hellp.touch.unitem.actuator.entity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.annotation.Nonnull;

public class KillEntityActuator implements IEntityActuator{
    @Nonnull
    @Override
    public String actuatorID() {
        return "kill";
    }

    @Override
    public Object actuate(Object... target) {
        IEntityActuator.super.actuate(target);
        Entity entity = (Entity) target[0];
        EntityDamageEvent event = new EntityDamageEvent(entity, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 114514);
        Bukkit.getPluginManager().callEvent(event);
        if(!event.isCancelled()) {
            if(entity instanceof Damageable) {
                Damageable damageable =(Damageable)entity;
                damageable.setHealth(0);
            } else {
                entity.remove();
            }
        }
        return null;
    }
}
