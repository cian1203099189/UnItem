package cn.hellp.touch.unitem.selector.tools;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class LocationToVec implements ISelector<Vector> {
    private final ISelector<?> selector;

    public LocationToVec(ISelector<?> selector) {
        this.selector = selector;
    }

    @Override
    public Vector[] select(Player invoker) {
        List<Vector> result = new ArrayList<>();
        for (Object loc : selector.select(invoker)) {
            result.add(((Vector) loc));
        }
        return result.toArray(new Vector[0]);
    }

    @Override
    public String selectorID() {
        return "locationToVec";
    }
}
