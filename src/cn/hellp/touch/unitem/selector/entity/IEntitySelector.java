package cn.hellp.touch.unitem.selector.entity;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface IEntitySelector<T extends Entity> extends ISelector<T> {
    @Override
    T[] select(Player invoker);
}
