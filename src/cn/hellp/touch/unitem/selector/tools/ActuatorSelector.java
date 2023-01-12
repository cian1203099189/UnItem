package cn.hellp.touch.unitem.selector.tools;

import cn.hellp.touch.unitem.actuator.IActuator;
import cn.hellp.touch.unitem.app.CallableActuator;
import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

public class ActuatorSelector implements ISelector {
    private final CallableActuator actuator;

    public ActuatorSelector(IActuator actuator,ISelector<?>... pragma) {
        this.actuator=new CallableActuator(actuator,pragma);
    }


    @Override
    public Object[] select(Player invoker) {
        return actuator.call(invoker);
    }

    @Override
    public String selectorID() {
        return "actuator";
    }
}
