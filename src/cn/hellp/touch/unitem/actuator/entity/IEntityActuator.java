package cn.hellp.touch.unitem.actuator.entity;

import cn.hellp.touch.unitem.actuator.IActuator;
import org.bukkit.entity.Entity;

public interface IEntityActuator extends IActuator {

    @Override
    default Object actuate(Object... target) {
        if(!(target[0] instanceof Entity)) {
            throw new RuntimeException("Entity actuator can only call with entity , but review a " + ((target[0])!=null ? target[0].getClass().getName()+"("+target[0]+")" : null));
        }
        return null;
    }
}
