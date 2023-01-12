package cn.hellp.touch.unitem.app;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ActuatorList {
    protected List<CallableActuator> actuatorList;

    public ActuatorList() {
        actuatorList=new ArrayList<>();
    }

    public void push_back(CallableActuator actuator) {
        actuatorList.add(actuator);
    }

    public CallableActuator pop_back() {
        if(actuatorList.isEmpty()) {
            throw new RuntimeException("pop_back from a empty ActuatorList.");
        }
        return actuatorList.remove(actuatorList.size()-1);
    }

    public void clear() {
        actuatorList.clear();
    }

    public void foreach(Consumer<CallableActuator> action) {
        actuatorList.forEach(action);
    }

    public CallableActuator get(int index) {
        return actuatorList.get(index);
    }

    public CallableActuator remove(int index) {
        return actuatorList.remove(index);
    }
}
