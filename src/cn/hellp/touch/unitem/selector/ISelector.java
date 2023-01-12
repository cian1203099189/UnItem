package cn.hellp.touch.unitem.selector;

import org.bukkit.entity.Player;

public interface ISelector<T>{
    T[] select(Player invoker);
    String selectorID();
}
