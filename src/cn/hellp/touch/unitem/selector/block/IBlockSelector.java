package cn.hellp.touch.unitem.selector.block;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface IBlockSelector<T extends Block> extends ISelector<T> {
    @Override
    T[] select(Player invoker);
}
