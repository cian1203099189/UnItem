package cn.hellp.touch.unitem.actuator.itemstack;

import cn.hellp.touch.unitem.actuator.IActuator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SetItemStackDisplayNameActuator implements IActuator {
    @Nullable
    @Override
    public Object actuate(Object... target) {
        ItemStack itemStack = ((ItemStack) target[0]);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(target[1].toString());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "setItemDisplayName";
    }
}
