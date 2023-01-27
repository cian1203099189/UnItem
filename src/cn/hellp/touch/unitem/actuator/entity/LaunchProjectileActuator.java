package cn.hellp.touch.unitem.actuator.entity;

import cn.hellp.touch.unitem.actuator.IActuator;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LaunchProjectileActuator implements IActuator {
    @Nullable
    @Override
    public Object actuate(Object... target) {
        Location source = getLocation(target[0]);
        Entity projectile = ((Entity) target[1]);
        projectile.teleport(source);
        if(target.length>2) {
            Vector velocity = ((Vector) target[2]);
            projectile.setVelocity(velocity);
        } else if (target[0] instanceof LivingEntity) {
            projectile.setVelocity(((LivingEntity) target[0]).getEyeLocation().getDirection());
        }
        return projectile;
    }

    private static Location getLocation(Object raw) {
        if(raw instanceof Location) {
            return ((Location) raw);
        }
        if(raw instanceof Entity) {
            if(raw instanceof LivingEntity) {
                return ((LivingEntity) raw).getEyeLocation();
            }
            return ((Entity) raw).getLocation();
        }
        throw new IllegalArgumentException("Unexpected projectile source type "+ (raw==null ? "null" : raw.getClass()));
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "launchProjectile";
    }
}
