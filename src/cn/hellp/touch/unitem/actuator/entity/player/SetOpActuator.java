package cn.hellp.touch.unitem.actuator.entity.player;

import cn.hellp.touch.unitem.actuator.IActuator;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SetOpActuator implements IActuator {
    @Nullable
    @Override
    public Object actuate(Object... target) {
        Player player = ((Player) target[0]);
        player.setOp(((Boolean) target[1]));
        return player;
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "setOp";
    }
}
