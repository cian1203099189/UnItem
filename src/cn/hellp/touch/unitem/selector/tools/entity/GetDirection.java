package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class GetDirection implements ISelector<Vector> {
    private final ISelector<?> targets;

    public GetDirection(ISelector<?> targets) {
        this.targets = targets;
    }

    @Override
    public Vector[] select(Player invoker) {
        List<Vector> result = new ArrayList<>();
        for (Object living : targets.select(invoker)) {
            result.add(((LivingEntity) living).getEyeLocation().getDirection());
        }
        return result.toArray(new Vector[0]);
    }

    @Override
    public String selectorID() {
        return "getDirection";
    }
}
