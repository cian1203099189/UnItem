package cn.hellp.touch.unitem.selector.tools.player;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerFoodLevelGetterSetter extends PlayerGetterSetter{
    @NotNull
    @Override
    public String actuatorID() {
        return "setFoodLevel";
    }

    public PlayerFoodLevelGetterSetter(ISelector<?> target) {
        super(target);
    }

    public PlayerFoodLevelGetterSetter() {
        super(null);
    }

    @Override
    public String selectorID() {
        return "getFoodLevel";
    }

    @Override
    protected Object get(Player invoker) {
        return invoker.getFoodLevel();
    }

    @Override
    protected Object set(Player invoker, Object... value) {
        invoker.setFoodLevel(((Number) value[0]).intValue());
        return get(invoker);
    }
}
