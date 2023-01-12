package cn.hellp.touch.unitem.actuator.itemstack;

import cn.hellp.touch.unitem.actuator.IActuator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SetItemStackTypeActuator implements IActuator {
    @Nullable
    @Override
    public Object actuate(Object... target) {
        ItemStack itemStack = ((ItemStack) target[0]);
        try {
            String typeName = ((String) target[1]);
            itemStack.setType(Material.getMaterial(typeName));
        }catch (ClassCastException exception) {
            Material material = ((Material) target[1]);
            itemStack.setType(material);
        }
        return itemStack;
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "setItemType";
    }
}
