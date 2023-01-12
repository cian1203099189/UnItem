package cn.hellp.touch.unitem.actuator.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.jetbrains.annotations.NotNull;

public class TeleportEntityActuator implements IEntityActuator{
    @NotNull
    @Override
    public String actuatorID() {
        return "teleport";
    }

    @Override
    public Object actuate(Object... target) {
        IEntityActuator.super.actuate(target);
        Entity entity = (Entity) target[0];
        EntityTeleportEvent event;
        try {
            Location location = (Location) target[1];
            event = new EntityTeleportEvent(entity,entity.getLocation(),location);
            Bukkit.getPluginManager().callEvent(event);
            if(!event.isCancelled()) {
                entity.teleport(location);
            }
        }catch (ClassCastException e) {
            Entity entity1 = (Entity) target[1];
            event = new EntityTeleportEvent(entity,entity.getLocation(),entity1.getLocation());
            Bukkit.getPluginManager().callEvent(event);
            if(!event.isCancelled()) {
                entity.teleport(entity1);
            }
        }
        return null;
    }
}
