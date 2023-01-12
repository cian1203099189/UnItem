package cn.hellp.touch.unitem.selector.tools.item;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GetItemType implements ISelector<Material> {
    private final ISelector<?> selector;

    public GetItemType(ISelector<?> selector) {
        this.selector=selector;
    }

    @Override
    public Material[] select(Player invoker) {
        List<Material> result = new ArrayList<>();
        for (Object item : selector.select(invoker)) {
            result.add(((ItemStack) item).getType());
        }
        return result.toArray(new Material[0]);
    }

    @Override
    public String selectorID() {
        return "getItemType";
    }
}
