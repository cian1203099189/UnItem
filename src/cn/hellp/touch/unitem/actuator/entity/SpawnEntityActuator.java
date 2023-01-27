package cn.hellp.touch.unitem.actuator.entity;

import cn.hellp.touch.unitem.actuator.IActuator;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpawnEntityActuator implements IActuator {

    @NotNull
    @Override
    public String actuatorID() {
        return "spawnEntity";
    }

    @Nullable
    @Override
    public Object actuate(Object... target) {
        Location location = (Location) target[0];
        EntityType entityType;
        if(target[1] instanceof String) {
            entityType = EntityType.valueOf(target[1].toString());
        } else {
            entityType = ((EntityType) target[1]);
        }
        return location.getWorld().spawnEntity(location,entityType);
    }
}
