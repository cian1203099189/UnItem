package cn.hellp.touch.unitem.app;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScopeCallableActuator extends CallableActuator{
    private final List<CallableActuator> actuators = new ArrayList<>();

    public ScopeCallableActuator(CallableActuator...actuators) {
        super(null);
        Collections.addAll(this.actuators,actuators);
    }

    @Override
    public void call(Player caller) {
        for (CallableActuator callableActuator : actuators) {
            callableActuator.call(caller);
        }
    }

    public List<CallableActuator> getActuators() {
        return actuators;
    }

    public void addActuator(CallableActuator actuator) {
        actuators.add(actuator);
    }
}
