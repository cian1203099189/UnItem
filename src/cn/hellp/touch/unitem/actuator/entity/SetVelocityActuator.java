package cn.hellp.touch.unitem.actuator.entity;

import cn.hellp.touch.unitem.actuator.IActuator;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SetVelocityActuator implements IActuator {
    @Nullable
    @Override
    public Object actuate(Object... target) {
        ((Entity) target[0]).setVelocity(((Vector) target[1]));
        return target[0];
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "setVelocity";
    }
}
