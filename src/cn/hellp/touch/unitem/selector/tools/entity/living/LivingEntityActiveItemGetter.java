package cn.hellp.touch.unitem.selector.tools.entity.living;

import cn.hellp.touch.unitem.auxiliary.Getter;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.LivingEntity;

public class LivingEntityActiveItemGetter extends Getter<LivingEntity> {
    public LivingEntityActiveItemGetter(ISelector<?> target) {
        super(target);
    }

    @Override
    protected Object get(LivingEntity invoker) {
        return invoker.getActiveItem();
    }

    @Override
    public String selectorID() {
        return "getEntityActiveItem";
    }
}
