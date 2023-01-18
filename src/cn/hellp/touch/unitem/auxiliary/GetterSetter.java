package cn.hellp.touch.unitem.auxiliary;

import cn.hellp.touch.unitem.actuator.IActuator;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class GetterSetter<T> implements ISelector, IActuator {
    private final Getter<T> getter;
    private final Setter<T> setter;

    public GetterSetter(ISelector<?> target) {
        this.getter = new Getter<T>(target) {
            @Override
            protected Object get(T invoker) {
                return GetterSetter.this.get(invoker);
            }

            @Override
            public String selectorID() {
                return GetterSetter.this.selectorID();
            }
        };
        this.setter = new Setter<T>() {
            @Override
            protected Object set(T invoker, Object... parameter) {
                return GetterSetter.this.set(invoker, parameter);
            }

            @NotNull
            @Override
            public String actuatorID() {
                return GetterSetter.this.selectorID();
            }
        };
    }

    public GetterSetter() {
        this(null);
    }

    @Override
    public Object[] select(Player invoker) {
        return getter.select(invoker);
    }

    @Nullable
    @Override
    public Object actuate(Object... target) {
        return setter.actuate(target);
    }

    /**
     *
     * @param invoker 调用的对象
     * @return invoker的该字段
     */
    protected abstract Object get(T invoker);

    /**
     *
     * @param invoker 调用者
     * @param value   要设置的值(参数)
     * @return 设置后的字段值
     */
    protected abstract Object set(T invoker,Object... value);
}
