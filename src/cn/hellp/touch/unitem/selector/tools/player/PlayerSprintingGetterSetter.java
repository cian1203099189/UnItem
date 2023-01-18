package cn.hellp.touch.unitem.selector.tools.player;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerSprintingGetterSetter extends PlayerGetterSetter {
    @NotNull
    @Override
    public String actuatorID() {
        return "setSprinting";
    }

    public PlayerSprintingGetterSetter(ISelector<?> target) {
        super(target);
    }

    public PlayerSprintingGetterSetter() {
        super(null);
    }

    @Override
    public String selectorID() {
        return "isSprinting";
    }

    @Override
    protected Object get(Player invoker) {
        return invoker.isSprinting();
    }

    @Override
    protected Object set(Player invoker, Object... value) {
        invoker.setSprinting(((Boolean) value[0]));
        return null;
    }
}
