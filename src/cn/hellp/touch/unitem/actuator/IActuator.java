package cn.hellp.touch.unitem.actuator;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IActuator {
    /**
     *
     * @param target the target the function want to handle.
     * @return the result of call , default null.
     */

    @Nullable
    default Object actuate(Object... target) {
        return null;
    }
    @Nonnull String actuatorID();
}
