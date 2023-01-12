package cn.hellp.touch.unitem.selector.entity.living.player;

import cn.hellp.touch.unitem.selector.entity.living.ILivingEntitySelector;
import org.bukkit.entity.Player;

public interface IPlayerSelector extends ILivingEntitySelector<Player> {
    @Override
    Player[] select(Player invoker);
}
