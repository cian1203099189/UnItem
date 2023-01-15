package cn.hellp.touch.unitem.selector.location;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.ValueSelector;
import cn.hellp.touch.unitem.auxiliary.Number;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LocationValueSelector extends ValueSelector<Location> {
    private final ISelector<?> world;
    private final ISelector<?> x;
    private final ISelector<?> y;
    private final ISelector<?> z;


    public LocationValueSelector(ISelector<?> world, ISelector<?> x, ISelector<?> y, ISelector<?> z) {
        super(null);
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public LocationValueSelector(ISelector<?> x, ISelector<?> y, ISelector<?> z) {
        this(null, x, y, z);
    }

    @Override
    public Location[] select(Player invoker) {
        List<Location> result = new ArrayList<>();
        if (world != null) {
            for (Object world : world.select(invoker)) {
                addLocation(invoker, result, (World) world);
            }
        } else {
            addLocation(invoker, result,null);
        }
        return result.toArray(new Location[0]);
    }

    private void addLocation(Player invoker, List<Location> result, World world2) {
        for (Object x : x.select(invoker)) {
            for (Object y : y.select(invoker)) {
                for (Object z : z.select(invoker)) {
                    result.add(new Location(world2, ((Number) x).toDouble(), ((Number) y).toDouble(), ((Number) z).toDouble()));
                }
            }
        }
    }
}
