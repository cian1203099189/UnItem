package cn.hellp.touch.unitem.selector.block;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GetBlockFromLocationSelector implements IBlockSelector<Block>{
    private final ISelector<?> locations;

    public GetBlockFromLocationSelector(ISelector<?> selector) {
        locations=selector;
    }

    @Override
    public String selectorID() {
        return "getBlockFromLocation";
    }

    @Override
    public Block[] select(Player invoker) {
        List<Block> result = new ArrayList<>();
        for (Object loc : locations.select(invoker)) {
            result.add(((Location) loc).getBlock());
        }
        return result.toArray(new Block[0]);
    }
}
