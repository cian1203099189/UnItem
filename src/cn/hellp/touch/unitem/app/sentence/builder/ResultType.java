package cn.hellp.touch.unitem.app.sentence.builder;

import cn.hellp.touch.unitem.app.CallableActuator;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

public enum ResultType {
    DONE,
    UNDONE,
    REMATCH;

    public Result withCallableActuator(@Nonnull CallableActuator actuator) {
        return new Result(DONE,actuator);
    }

    public Result withCallableActuatorAndNeedNext(@Nonnull CallableActuator actuator, @Nonnull Pattern need) {
        return new Result(DONE,actuator,need);
    }

    public Result withNeedNext(@Nonnull Pattern need) {
        return new Result(DONE,null,need);
    }

    public Result empty() {
        return new Result(DONE,null);
    }
}
