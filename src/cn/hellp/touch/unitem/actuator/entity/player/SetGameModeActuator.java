package cn.hellp.touch.unitem.actuator.entity.player;

import cn.hellp.touch.unitem.actuator.IActuator;
import cn.hellp.touch.unitem.auxiliary.Number;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SetGameModeActuator implements IActuator {
    @Nullable
    @Override
    public Object actuate(Object... target) {
        Player player = ((Player) target[0]);
        if(target[1] instanceof GameMode) {
            player.setGameMode(((GameMode) target[1]));
        } else if (target[1] instanceof String){
            try {
                player.setGameMode(GameMode.valueOf(((String) target[1]).toUpperCase()));
            }catch (Exception e) {
                player.setGameMode(GameMode.getByValue(Integer.parseInt(((String) target[1]))));
            }
        } else {
            player.setGameMode(GameMode.getByValue(((Number) target[1]).toInteger()));
        }
        return player;
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "setGameMode";
    }
}
