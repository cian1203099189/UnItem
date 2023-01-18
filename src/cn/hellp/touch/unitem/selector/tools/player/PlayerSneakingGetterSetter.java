package cn.hellp.touch.unitem.selector.tools.player;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerSneakingGetterSetter extends PlayerGetterSetter {
    @NotNull
    @Override
    public String actuatorID() {
        return "setSneaking";
    }

    public PlayerSneakingGetterSetter(ISelector<?> target) {
        super(target);
    }

    public PlayerSneakingGetterSetter() {
        super(null);
    }

    @Override
    public String selectorID() {
        return "isSneaking";
    }

    @Override
    protected Object get(Player invoker) {
        return invoker.isSneaking();
    }

    @Override
    protected Object set(Player invoker, Object... value) {
        invoker.setSneaking(((Boolean) value[0]));
        return null;
    }
}
