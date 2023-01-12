package cn.hellp.touch.unitem.selector.entity.living.player;

import org.bukkit.entity.Player;

public class CallingPlayerSelector implements IPlayerSelector{
    @Override
    public String selectorID() {
        return "owner_player";
    }

    @Override
    public Player[] select(Player invoker) {
        return new Player[]{invoker};
    }
}
