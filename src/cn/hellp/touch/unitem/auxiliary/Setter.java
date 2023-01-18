package cn.hellp.touch.unitem.auxiliary;

import cn.hellp.touch.unitem.actuator.IActuator;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public abstract class Setter<T> implements IActuator {

    @Nullable
    @Override
    public Object actuate(Object... target) {
        T invoker = ((T) target[0]);
        return set(invoker, Arrays.copyOfRange(target,1,target.length));
    }

    protected abstract Object set(T invoker,Object... parameter);
}
