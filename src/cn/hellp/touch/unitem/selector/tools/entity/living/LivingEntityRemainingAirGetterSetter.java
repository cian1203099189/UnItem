package cn.hellp.touch.unitem.selector.tools.entity.living;

import cn.hellp.touch.unitem.auxiliary.GetterSetter;
import cn.hellp.touch.unitem.auxiliary.Number;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class LivingEntityRemainingAirGetterSetter extends GetterSetter<LivingEntity> {
    public LivingEntityRemainingAirGetterSetter(ISelector<?> target) {
        super(target);
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "setEntityRemainingAir";
    }

    @Override
    protected Object get(LivingEntity invoker) {
        return invoker.getRemainingAir();
    }

    @Override
    protected Object set(LivingEntity invoker, Object... value) {
        invoker.setRemainingAir(((Number) value[0]).toInteger());
        return get(invoker);
    }

    @Override
    public String selectorID() {
        return "getEntityRemainingAir";
    }
}
