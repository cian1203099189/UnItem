package cn.hellp.touch.unitem.selector.entity.living;

import cn.hellp.touch.unitem.selector.entity.IEntitySelector;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface ILivingEntitySelector<T extends LivingEntity> extends IEntitySelector<T> {
    @Override
    T[] select(Player invoker);
}
