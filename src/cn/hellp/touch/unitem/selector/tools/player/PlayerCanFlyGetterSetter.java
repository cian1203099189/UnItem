package cn.hellp.touch.unitem.selector.tools.player;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerCanFlyGetterSetter extends PlayerGetterSetter {
    @NotNull
    @Override
    public String actuatorID() {
        return "setPlayerCanFly";
    }

    public PlayerCanFlyGetterSetter() {
        super(null);
    }

    public PlayerCanFlyGetterSetter(ISelector<?> target) {
        super(target);
    }

    @Override
    public String selectorID() {
        return "getPlayerCanFly";
    }

    @Override
    protected Object get(Player invoker) {
        return invoker.getAllowFlight();
    }

    @Override
    protected Object set(Player invoker, Object... value) {
        invoker.setAllowFlight(((Boolean) value[0]));
        return get(invoker);
    }
}
