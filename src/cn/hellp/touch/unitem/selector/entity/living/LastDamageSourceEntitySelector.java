package cn.hellp.touch.unitem.selector.entity.living;

import cn.hellp.touch.unitem.listener.PlayerDamageListener;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class LastDamageSourceEntitySelector implements ILivingEntitySelector<LivingEntity>{
    @Override
    public LivingEntity[] select(Player invoker) {
        if(!PlayerDamageListener.getDamageLog().containsKey(invoker.getUniqueId())) {
            return new LivingEntity[0];
        }
        LivingEntity[] result = new LivingEntity[1];
        for (PlayerDamageListener.Damage damage : PlayerDamageListener.getDamageLog().get(invoker.getUniqueId())) {
            if(damage.source instanceof LivingEntity) {
                LivingEntity entity = ((LivingEntity) damage.source);
                result[0]=entity;
            }
        }
        return result;
    }

    @Override
    public String selectorID() {
        return "lastDamageEntity";
    }

}
