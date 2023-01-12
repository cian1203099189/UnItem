package cn.hellp.touch.unitem.actuator.block;

import cn.hellp.touch.unitem.actuator.IActuator;
import org.bukkit.block.Block;

public interface IBlockActuator extends IActuator {
    @Override
    default Object actuate(Object... target) {
        if(!(target[0] instanceof Block)) {
            throw new RuntimeException("Block actuator can only call with block.");
        }
        return null;
    }
}
