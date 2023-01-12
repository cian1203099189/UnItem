package cn.hellp.touch.unitem.app.sentence;

import cn.hellp.touch.unitem.actuator.IActuator;
import cn.hellp.touch.unitem.app.ActuatorList;
import cn.hellp.touch.unitem.app.CallableActuator;
import cn.hellp.touch.unitem.app.PlaceholderManager;
import cn.hellp.touch.unitem.app.Sentence;
import cn.hellp.touch.unitem.selector.ISelector;

public class ActuateSentence extends Sentence {

    public ActuateSentence(PlaceholderManager manager, ActuatorList actuatorList) {
        super(manager, actuatorList);
    }

    @Override
    public void init(Object... args) {
        IActuator actuator = (IActuator) args[0];
        CallableActuator callableActuator = new CallableActuator(actuator);
        ISelector<?>[] selectors = (ISelector<?>[]) args[1];
        for (ISelector<?> selector : selectors) {
            callableActuator.pushBackSelector(selector);
        }
        actuatorList.push_back(callableActuator);
    }
}
