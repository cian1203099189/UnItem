package cn.hellp.touch.unitem.actuator.entity;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetFoodLevelActuator implements IEntityActuator{
    @Override
    public Object actuate(Object... target) {
        Player player = ((Player) target[0]);
        Integer integer = ((Integer) target[1]);
        player.setFoodLevel(integer);
        return null;
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "setFoodLevel";
    }
}
