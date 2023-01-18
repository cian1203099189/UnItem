package cn.hellp.touch.unitem.selector.entity.living.player;

import cn.hellp.touch.unitem.listener.PlayerDamageListener;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.entity.living.LastDamageSourceEntitySelector;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LastDamageSourcePlayerSelector extends LastDamageSourceEntitySelector {

    public LastDamageSourcePlayerSelector(@Nullable ISelector<?> target) {
        super(target);
    }

    public LastDamageSourcePlayerSelector() {
    }

    public static Player[] getSourcePlayer(Player target) {
        for (PlayerDamageListener.Damage damage : PlayerDamageListener.getDamageLog().get(target.getUniqueId())) {
            if(damage.source instanceof Player) {
                Player player = ((Player) damage.source);
                return new Player[] {player};
            }
        }
        return new Player[0];
    }

    @Override
    public LivingEntity[] select(Player invoker) {
        if(target==null) {
            return getSourcePlayer(invoker);
        } else {
            List<Player> result = new ArrayList<>();
            Collections.addAll(result,getSourcePlayer(invoker));
            return result.toArray(new Player[0]);
        }
    }

    @Override
    public String selectorID() {
        return "lastDamagePlayer";
    }
}
