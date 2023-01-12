package cn.hellp.touch.unitem.selector.entity.living.player;

import cn.hellp.touch.unitem.listener.PlayerDamageListener;
import cn.hellp.touch.unitem.selector.entity.living.LastDamageSourceEntitySelector;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class LastDamageSourcePlayerSelector extends LastDamageSourceEntitySelector {


    @Override
    public LivingEntity[] select(Player invoker) {
        for (PlayerDamageListener.Damage damage : PlayerDamageListener.getDamageLog().get(invoker.getUniqueId())) {
            if(damage.source instanceof Player) {
                Player player = ((Player) damage.source);
                return new Player[] {player};
            }
        }
        return new Player[0];
    }

    @Override
    public String selectorID() {
        return "lastDamagePlayer";
    }
}
