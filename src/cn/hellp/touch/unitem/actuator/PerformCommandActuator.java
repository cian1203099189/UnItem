package cn.hellp.touch.unitem.actuator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PerformCommandActuator implements IActuator{
    @Nullable
    @Override
    public Object actuate(Object... target) {
        return IActuator.super.actuate(target);
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "performCommand";
    }
}
