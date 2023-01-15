package cn.hellp.touch.unitem.app;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IfCallableActuator extends CallableActuator{
    private final ISelector<?> predicate;
    private final List<CallableActuator> callableActuators = new ArrayList<>();
    private CallableActuator[] else_;

    public IfCallableActuator(CallableActuator else_[], ISelector<?> predicate, CallableActuator... callableActuators) {
        super(null);
        this.predicate=predicate;
        this.else_ = else_;
        Collections.addAll(this.callableActuators,callableActuators);
    }

    public void setElse_(CallableActuator[] else_) {
        this.else_ = else_;
    }

    @Override
    public void call(Player caller) {
        for (Object pre : predicate.select(caller)) {
            if(((Boolean) pre)) {
                for (CallableActuator callableActuator : callableActuators) {
                    callableActuator.call(caller);
                }
            } else {
                for (CallableActuator callableActuator : else_) {
                    callableActuator.call(caller);
                }
            }
        }
    }
}
