package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.auxiliary.Number;
import org.bukkit.entity.Player;

public class GetFoodLevelSelector implements ISelector<Number>{
    private final ISelector<?> entities;

    public GetFoodLevelSelector(ISelector<?> entities) {
        this.entities=entities;
    }


    @Override
    public Number[] select(Player invoker) {
        Object[] objects = entities.select(invoker);
        Number[] result = new Number[objects.length];
        for (int i = 0;i<objects.length;i++) {
            result[i]= new Number (((Player) objects[i]).getFoodLevel());
        }
        return result;
    }

    @Override
    public String selectorID() {
        return "getFoodLevel";
    }
}
