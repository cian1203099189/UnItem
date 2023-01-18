package cn.hellp.touch.unitem.selector.tools.player;

import cn.hellp.touch.unitem.auxiliary.Number;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerLevelGetterSetter extends PlayerGetterSetter {
    @NotNull
    @Override
    public String actuatorID() {
        return "setPlayerLevel";
    }

    @Override
    public String selectorID() {
        return "getPlayerLevel";
    }

    public PlayerLevelGetterSetter(ISelector<?> target) {
        super(target);
    }

    public PlayerLevelGetterSetter() {
        super(null);
    }

    @Override
    protected Object get(Player invoker) {
        return invoker.getLevel();
    }

    @Override
    protected Object set(Player invoker, Object... value) {
        invoker.setLevel(((Number) value[0]).toInteger());
        return get(invoker);
    }
}
