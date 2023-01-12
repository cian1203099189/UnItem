package cn.hellp.touch.unitem.app.sentence;

import cn.hellp.touch.unitem.app.*;
import cn.hellp.touch.unitem.selector.ISelector;

public class ForActuatorSentence extends Sentence {
    public ForActuatorSentence(PlaceholderManager manager, ActuatorList actuatorList) {
        super(manager, actuatorList);
    }

    @Override
    public void init(Object... args) {
        ForCallableActuator actuator = new ForCallableActuator(((String) args[0]), ((PlaceholderManager) args[1]), ((ISelector) args[2]), ((ISelector) args[3]));
        if(args.length>=5) {
            Object[] objs = (Object[]) args[4];
            for (Object obj : objs) {
                actuator.addActuator(((CallableActuator) obj));
            }
        }
        actuatorList.push_back(actuator);
    }
}
