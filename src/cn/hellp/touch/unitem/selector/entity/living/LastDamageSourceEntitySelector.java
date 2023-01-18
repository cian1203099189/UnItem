package cn.hellp.touch.unitem.selector.entity.living;

import cn.hellp.touch.unitem.listener.PlayerDamageListener;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LastDamageSourceEntitySelector implements ILivingEntitySelector<LivingEntity>{
    protected @Nullable
    final ISelector<?> target;

    public LastDamageSourceEntitySelector(@Nullable ISelector<?> target) {
        this.target = target;
    }

    public LastDamageSourceEntitySelector() {
        this(null);
    }

    public static LivingEntity[] getSource(Player invoker) {
        if (!PlayerDamageListener.getDamageLog().containsKey(invoker.getUniqueId())) {
            return new LivingEntity[0];
        }
        LivingEntity[] result = new LivingEntity[1];
        for (PlayerDamageListener.Damage damage : PlayerDamageListener.getDamageLog().get(invoker.getUniqueId())) {
            if (damage.source instanceof LivingEntity) {
                LivingEntity entity = ((LivingEntity) damage.source);
                result[0] = entity;
            }
        }
        return result;
    }

    @Override
    public LivingEntity[] select(Player invoker) {
        if(target==null) {
            return getSource(invoker);
        } else {
            List<LivingEntity> result = new ArrayList<>();
            for (Object player : target.select(invoker)) {
                Collections.addAll(result,getSource(((Player) player)));
            }
            return result.toArray(new LivingEntity[0]);
        }
    }

    @Override
    public String selectorID() {
        return "lastDamageEntity";
    }

}
