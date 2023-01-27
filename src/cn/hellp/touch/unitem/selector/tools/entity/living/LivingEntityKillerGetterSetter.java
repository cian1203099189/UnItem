package cn.hellp.touch.unitem.selector.tools.entity.living;

import cn.hellp.touch.unitem.auxiliary.GetterSetter;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LivingEntityKillerGetterSetter extends GetterSetter<LivingEntity> {
    public LivingEntityKillerGetterSetter(ISelector<?> target) {
        super(target);
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "setKiller";
    }

    @Override
    protected Object get(LivingEntity invoker) {
        return invoker.getKiller();
    }

    @Override
    protected Object set(LivingEntity invoker, Object... value) {
        invoker.setKiller(((Player) value[0]));
        return get(invoker);
    }

    @Override
    public String selectorID() {
        return "getKiller";
    }
}
