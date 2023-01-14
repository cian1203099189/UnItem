package cn.hellp.touch.unitem.app.sentence.builder;

import cn.hellp.touch.unitem.app.CallableActuator;

import java.util.regex.Pattern;

public class Result {
    public ResultType type;
    public CallableActuator actuator;
    public Pattern need;

    public Result(ResultType type, CallableActuator actuator) {
        this.type = type;
        this.actuator = actuator;
    }

    public Result(ResultType type, CallableActuator actuator, Pattern need) {
        this.type = type;
        this.actuator = actuator;
        this.need=need;
    }

    public boolean hasActuator() {
        return actuator!=null;
    }

    public boolean hasNeed() {
        return need!=null;
    }
}
