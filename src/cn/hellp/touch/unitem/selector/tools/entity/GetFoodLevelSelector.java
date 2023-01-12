package cn.hellp.touch.unitem.selector.tools.entity;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

public class GetFoodLevelSelector implements ISelector<Integer>{
    private final ISelector<?> entities;

    public GetFoodLevelSelector(ISelector<?> entities) {
        this.entities=entities;
    }


    @Override
    public Integer[] select(Player invoker) {
        Object[] objects = entities.select(invoker);
        Integer[] result = new Integer[objects.length];
        for (int i = 0;i<objects.length;i++) {
            result[i]= ((Player) objects[i]).getFoodLevel();
        }
        return result;
    }

    @Override
    public String selectorID() {
        return "getFoodLevel";
    }
}
