package cn.hellp.touch.unitem.selector.tools;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class VectorToLocationSelector implements ISelector<Location> {
    private final ISelector<?> vector;

    public VectorToLocationSelector(ISelector<?> vector) {
        this.vector = vector;
    }

    @Override
    public Location[] select(Player invoker) {
        List<Location> result = new ArrayList<>();
        for (Object vec : vector.select(invoker)) {
            result.add(((Vector) vec).toLocation(null));
        }
        return result.toArray(new Location[0]);
    }

    @Override
    public String selectorID() {
        return "vecToLocation";
    }
}
