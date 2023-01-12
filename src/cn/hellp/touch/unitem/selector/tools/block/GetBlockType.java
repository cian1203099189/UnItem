package cn.hellp.touch.unitem.selector.tools.block;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GetBlockType implements ISelector<Material> {
    private final ISelector<?> selector;

    public GetBlockType(ISelector<?> selector) {
        this.selector=selector;
    }

    @Override
    public Material[] select(Player invoker) {
        List<Material> result = new ArrayList<>();
        for (Object block : selector.select(invoker)) {
            result.add(((Block) block).getType());
        }
        return result.toArray(new Material[0]);
    }

    @Override
    public String selectorID() {
        return "getBlockType";
    }
}
