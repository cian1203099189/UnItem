package cn.hellp.touch.unitem.app;

import cn.hellp.touch.unitem.auxiliary.Number;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.ValueSelector;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ForCallableActuator extends CallableActuator{
    private final List<CallableActuator> actuators;
    private final ISelector<?> minValue;
    private final ISelector<?> maxValue;
    private final String placeholder;
    private final PlaceholderManager manager;

    public ForCallableActuator(String placeholderName,PlaceholderManager manager,ISelector<?> min,ISelector<?> max,CallableActuator... actuators) {
        super(null);
        this.minValue=min;
        this.placeholder=placeholderName;
        this.manager=manager;
        this.maxValue=max;
        this.actuators= new ArrayList<>();
        Collections.addAll(this.actuators, actuators);
    }

    private static int min(Object... ints) {
        int result = Integer.MAX_VALUE;
        for (Object anInt : ints) {
            result=Math.min(result, ((Number) anInt).toInteger());
        }
        return result;
    }

    private static int max(Object... ints) {
        int result = Integer.MIN_VALUE;
        for (Object anInt : ints) {
            result=Math.max(result,((Number) anInt).toInteger());
        }
        return result;
    }

    @Override
    public void call(Player caller) {
        int min = min((Object[]) minValue.select(caller));
        int max = max((Object[]) maxValue.select(caller));
        for(int i = min ;i<max;i++) {
            manager.put(placeholder,new ValueSelector<>(new Number(i)));
            for (CallableActuator callableActuator : actuators) {
                callableActuator.call(caller);
            }
        }
    }

    public void addActuator(@Nonnull CallableActuator actuator) {
        actuators.add(actuator);
    }
}
