package cn.hellp.touch.unitem.selector.tools.location;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.ValueSelector;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class Sub extends OperatorLocationSelector{
    public Sub(ISelector<?> selector, ValueSelector<?> pragma) {
        super(selector, pragma);
    }

    @Override
    public Location operator(Location location, Player invoker) {
        Location vec = (Location) pragma.select(invoker)[0];
        vec.setWorld(location.getWorld());
        return location.subtract(vec);
    }

    @Override
    public String selectorID() {
        return "subLoc";
    }
}
