package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class GetVelocitySelector implements ISelector<Vector> {
    private final ISelector<?> selector;

    public GetVelocitySelector(ISelector<?> selector) {
        this.selector=selector;
    }

    @Override
    public Vector[] select(Player invoker) {
        Object[] objects = selector.select(invoker);
        Vector[] results = new Vector[objects.length];
        for (int i = 0; i < objects.length; i++) {
            results[i]= ((Entity) objects[i]).getVelocity();
        }
        return results;
    }

    @Override
    public String selectorID() {
        return "getVelocity";
    }
}
