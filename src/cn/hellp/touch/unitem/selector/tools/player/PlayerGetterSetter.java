package cn.hellp.touch.unitem.selector.tools.player;

import cn.hellp.touch.unitem.auxiliary.GetterSetter;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class PlayerGetterSetter extends GetterSetter<Player> {
    private final PlayerGetter getter;
    private final PlayerSetter setter;

    public PlayerGetterSetter(ISelector<?> target) {
        this.getter = new PlayerGetter(target) {
            @Override
            protected Object get(Player invoker) {
                return PlayerGetterSetter.this.get(invoker);
            }

            @Override
            public String selectorID() {
                return PlayerGetterSetter.this.selectorID();
            }
        };
        this.setter = new PlayerSetter() {
            @Override
            protected Object set(Player invoker, Object... parameter) {
                return PlayerGetterSetter.this.set(invoker, parameter);
            }

            @NotNull
            @Override
            public String actuatorID() {
                return PlayerGetterSetter.this.selectorID();
            }
        };
    }

    public PlayerGetterSetter() {
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
    protected abstract Object get(Player invoker);

    /**
     *
     * @param invoker 调用者
     * @param value   要设置的值(参数)
     * @return 设置后的字段值
     */
    protected abstract Object set(Player invoker,Object... value);
}
