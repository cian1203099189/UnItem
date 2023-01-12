package cn.hellp.touch.unitem.selector.tools.location;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.ValueSelector;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Add extends OperatorLocationSelector {
    public Add(ISelector<?> selector, ValueSelector<?> pragma) {
        super(selector, pragma);
    }

    @Override
    public Location operator(Location location, Player invoker) {
        Location vec = (Location) pragma.select(invoker)[0];
        vec.setWorld(location.getWorld());
        return location.add(vec);
    }

    @Override
    public String selectorID() {
        return "addLoc";
    }
}
