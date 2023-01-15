package cn.hellp.touch.unitem.selector.entity.living;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.auxiliary.Number;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NearByLivingEntitySelector implements ILivingEntitySelector<LivingEntity>{
    private final ISelector<?> radius;
    @Override
    public String selectorID() {
        return "nearLivingEntities";
    }

    public NearByLivingEntitySelector(ISelector<?> radius) {
        this.radius= radius;
    }

    public List<LivingEntity> getNearbyLivingEntities(Player invoker,int radius) {
        Collection<Entity> nearbyEntities = invoker.getWorld().getNearbyEntities(invoker.getLocation(), radius, radius, radius);
        List<LivingEntity> result = new ArrayList<>();
        for (Entity entity : nearbyEntities) {
            if(entity instanceof LivingEntity && !entity.getUniqueId().equals(invoker.getUniqueId())) {
                result.add(((LivingEntity) entity));
            }
        }
        return result;
    }

    @Override
    public LivingEntity[] select(Player invoker) {
        Object[] ints =  radius.select(invoker);
        List<LivingEntity> result = new ArrayList<>();
        for (int i = 0; i < ints.length; i++) {
            result.addAll(getNearbyLivingEntities(invoker, ((Number) ints[i]).toInteger()));
        }
        return result.toArray(new LivingEntity[0]);
    }
}
