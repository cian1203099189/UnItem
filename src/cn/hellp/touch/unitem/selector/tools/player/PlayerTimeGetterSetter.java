package cn.hellp.touch.unitem.selector.tools.player;

import cn.hellp.touch.unitem.auxiliary.Number;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerTimeGetterSetter extends PlayerGetterSetter{
    @NotNull
    @Override
    public String actuatorID() {
        return "setPlayerTime";
    }

    @Override
    public String selectorID() {
        return "getPlayerTime";
    }

    public PlayerTimeGetterSetter(ISelector<?> target) {
        super(target);
    }

    public PlayerTimeGetterSetter() {
        super(null);
    }

    @Override
    protected Object get(Player invoker) {
        return invoker.getPlayerTime();
    }

    @Override
    protected Object set(Player invoker, Object... value) {
        invoker.setPlayerTime(((Number) value[0]).toLong(),true);
        return null;
    }
}
