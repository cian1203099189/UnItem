package cn.hellp.touch.unitem.app;

import cn.hellp.touch.unitem.actuator.IActuator;
import cn.hellp.touch.unitem.plugin.Main;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CallableActuator {
    protected IActuator actuator;
    protected List<ISelector<?>> pragmas;

    public CallableActuator(IActuator actuator, ISelector<?>... selectors) {
        this.actuator=actuator;
        this.pragmas=new ArrayList<>();
        Collections.addAll(pragmas, selectors);
    }

    public Object[] call(Player caller) {
        Object[][] temp = new Object[pragmas.size()][];
        int length = Integer.MAX_VALUE;
        for (int i = 0; i < pragmas.size(); i++) {
            temp[i]=pragmas.get(i).select(caller);
            length=Math.min(length,temp[i].length);
        }
        List<Object> result = new ArrayList<>();
        Object[] pragma = new Object[pragmas.size()];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < pragmas.size(); j++) {
                pragma[j]=temp[j][i];
            }
            try {
                result.add(actuator.actuate(pragma));
            } catch (Exception e) {
                Main.getMainLogger().warning("can't use actuator \""+actuator.actuatorID()+"\" with ");
                e.printStackTrace();
            }
        }
        return result.toArray(new Object[0]);
    }



    public void pushBackSelector(ISelector<?> selector) {
        pragmas.add(selector);
    }

    public ISelector<?> removeBack() {
        if(pragmas.isEmpty()) {
            throw new RuntimeException("removeBack from a empty CallableActuator");
        }
        return pragmas.remove(pragmas.size()-1);
    }
}
