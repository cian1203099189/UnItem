package cn.hellp.touch.unitem.selector.entity.living.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class OnlinePlayerSelector implements IPlayerSelector{
    @Override
    public String selectorID() {
        return "onlinePlayers";
    }

    @Override
    public Player[] select(Player invoker) {
        return Bukkit.getOnlinePlayers().toArray(new Player[0]);
    }
}
