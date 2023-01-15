package cn.hellp.touch.unitem.actuator.entity;

import cn.hellp.touch.unitem.auxiliary.Number;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetFoodLevelActuator implements IEntityActuator{
    @Override
    public Object actuate(Object... target) {
        Player player = ((Player) target[0]);
        Number integer = ((Number) target[1]);
        player.setFoodLevel(integer.toInteger());
        return null;
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "setFoodLevel";
    }
}
