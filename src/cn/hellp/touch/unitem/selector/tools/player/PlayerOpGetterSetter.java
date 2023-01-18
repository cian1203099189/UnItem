package cn.hellp.touch.unitem.selector.tools.player;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerOpGetterSetter extends PlayerGetterSetter {
    @NotNull
    @Override
    public String actuatorID() {
        return "setOp";
    }

    public PlayerOpGetterSetter(ISelector<?> target) {
        super(target);
    }

    public PlayerOpGetterSetter() {
        super(null);
    }

    @Override
    public String selectorID() {
        return "isOp";
    }

    @Override
    protected Object get(Player invoker) {
        return invoker.isOp();
    }

    @Override
    protected Object set(Player invoker, Object... value) {
        invoker.setOp(((Boolean) value[0]));
        return null;
    }
}
