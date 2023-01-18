package cn.hellp.touch.unitem.selector.tools.player;

import cn.hellp.touch.unitem.auxiliary.Number;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerExpGetterSetter extends PlayerGetterSetter{
    @NotNull
    @Override
    public String actuatorID() {
        return "setPlayerExp";
    }

    @Override
    public String selectorID() {
        return "getPlayerExp";
    }

    @Override
    protected Object get(Player invoker) {
        return invoker.getExp();
    }

    public PlayerExpGetterSetter(ISelector<?> target) {
        super(target);
    }

    public PlayerExpGetterSetter() {
        super(null);
    }

    @Override
    protected Object set(Player invoker, Object... value) {
        invoker.setExp(((Number) value[0]).toFloat());
        return get(invoker);
    }
}
