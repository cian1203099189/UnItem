package cn.hellp.touch.unitem.actuator.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public class SetBlockTypeActuator implements IBlockActuator{
    @NotNull
    @Override
    public String actuatorID() {
        return "setBlockType";
    }

    @Override
    public Object actuate(Object... target) {
        IBlockActuator.super.actuate(target);
        Block block = ((Block) target[0]);
        try {
            block.setType(((Material) target[1]));
        } catch (ClassCastException e) {
            block.setType(Material.valueOf(((String) target[1]).toUpperCase()));
        }
        return block;
    }
}
