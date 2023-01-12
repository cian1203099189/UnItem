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

    public static List<List> a(List<List> p,Object[][] temp,int i) {
        if(i>=temp.length) {
            return p;
        }
        ArrayList<List> re = new ArrayList<>();
        for(List<Object> o : p) {
            for(int j = 0;j<temp[i].length;j++) {
                ArrayList list = new ArrayList();
                list.addAll(o);
                list.add(temp[i][j]);
                re.add(list);
            }
        }
        return a(re,temp,++i);
    }

    public Object[] call(Player caller) {
        Object[][] temp = new Object[pragmas.size()][];
        List result = new ArrayList();
        int size = -1;
        for (int i = 0;i< pragmas.size();i++) {
            ISelector<?> selector = pragmas.get(i);
            Object[] objs = selector.select(caller);
            temp[i]= objs;
            size=Math.max(size,objs.length);
            if(objs.length<=0) {
                return new Object[0];
            }
        }
        List<List> l = new ArrayList();
        l.add(new ArrayList());
        List<List> a = a(l,temp,0);
        for (List pragma : a) {
            result.add(actuator.actuate(pragma.toArray()));
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
