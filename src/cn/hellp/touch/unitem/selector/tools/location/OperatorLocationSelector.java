package cn.hellp.touch.unitem.selector.tools.location;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.ValueSelector;
import cn.hellp.touch.unitem.selector.tools.OperatorSelector;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class OperatorLocationSelector extends OperatorSelector<Location> {
    public OperatorLocationSelector(ISelector<?> selector, ValueSelector<?> pragma) {
        super(selector, pragma);
    }

    @Override
    public Location[] select(Player invoker) {
        return super.select(invoker);
    }

    public abstract Location operator(Location location, Player invoker);
}
