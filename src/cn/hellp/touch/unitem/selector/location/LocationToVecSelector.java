package cn.hellp.touch.unitem.selector.location;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LocationToVecSelector implements ISelector<Vector> {
    private final ISelector<?> value;

    public LocationToVecSelector(ISelector<?> value) {
        this.value = value;
    }

    @Override
    public Vector[] select(Player invoker) {
        Object[] source = value.select(invoker);
        Vector[] result = new Vector[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i]= ((Location) source[i]).toVector();
        }
        return result;
    }

    @Override
    public String selectorID() {
        return "locToVector";
    }
}
