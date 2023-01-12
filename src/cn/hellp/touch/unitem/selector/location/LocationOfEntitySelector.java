package cn.hellp.touch.unitem.selector.location;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class LocationOfEntitySelector implements ISelector<Location> {
    private final ISelector<?> entity;

    public LocationOfEntitySelector(ISelector<?> entity) {
        this.entity=entity;
    }

    @Override
    public Location[] select(Player invoker) {
        Entity[] entities = (Entity[]) entity.select(invoker);
        Location[] result = new Location[entities.length];
        for (int i = 0; i < entities.length; i++) {
            result[i]=entities[i].getLocation();
        }
        return result;
    }

    @Override
    public String selectorID() {
        return "locationOfEntity";
    }
}
