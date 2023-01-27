package cn.hellp.touch.unitem.actuator.entity.player;

import cn.hellp.touch.unitem.actuator.IActuator;
import cn.hellp.touch.unitem.auxiliary.Number;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TakeItemActuator implements IActuator {
    @NotNull
    @Override
    public String actuatorID() {
        return "takeItem";
    }

    @Nullable
    @Override
    public Object actuate(Object... target) {
        Inventory source = target[0] instanceof Inventory ? ((Inventory) target[0]) : ((Player) target[0]).getInventory();
        boolean isMaterial;
        int removeCount = 1;
        if(target.length > 2) {
            removeCount = ((Number) target[2]).toInteger();
        }
        if((isMaterial = (target[1] instanceof Material)) || target[1] instanceof String) {
            Material material = isMaterial ? ((Material) target[1]) : Material.valueOf(((String) target[1]).toUpperCase());
            for (ItemStack itemStack : source.getContents()){
                if(itemStack.getType() == material && itemStack.getAmount()>0) {
                    int amount = itemStack.getAmount();
                    itemStack.setAmount(Math.max(0,amount - removeCount));
                    removeCount-=amount;
                    if(removeCount<=0) {
                        return true;
                    }
                }
            }
            return false;
        }
        if(target[1] instanceof ItemStack) {
            ItemStack itemStackToRemove = ((ItemStack) target[1]);
            for (ItemStack itemStack : source.getContents()) {
                if(itemStack.isSimilar(itemStackToRemove) && itemStack.getAmount()>0) {
                    int amount = itemStack.getAmount();
                    itemStack.setAmount(Math.max(0,amount - removeCount));
                    removeCount-=amount;
                    if(removeCount<=0) {
                        return true;
                    }
                }
            }
            return false;
        }
        throw new IllegalArgumentException("Illegal item/material to remove");
    }
}
