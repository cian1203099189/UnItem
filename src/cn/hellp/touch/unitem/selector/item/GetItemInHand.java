package cn.hellp.touch.unitem.selector.item;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GetItemInHand implements ISelector<ItemStack> {
    private final ISelector<?> player;

    public GetItemInHand(ISelector<?> player) {
        this.player=player;
    }

    public GetItemInHand() {
        this(null);
    }

    @Override
    public ItemStack[] select(Player invoker) {
        if(player==null) {
            return new ItemStack[]{invoker.getInventory().getItemInHand()};
        } else {
            List<ItemStack> result = new ArrayList<>();
            for (Object player : player.select(invoker)) {
                result.add(((Player) player).getItemInHand());
            }
            return result.toArray(new ItemStack[0]);
        }
    }

    @Override
    public String selectorID() {
        return "getItemInHand";
    }
}
