package cn.hellp.touch.unitem.app.sentence.builder;

import cn.hellp.touch.unitem.app.*;
import cn.hellp.touch.unitem.app.sentence.SentenceFactory;

import java.util.regex.Pattern;

public class ScopeBuilder extends SentenceBuilder{
    public ScopeBuilder() {
        pattern = Pattern.compile("\\{(?<body>[\\s\\S]*)}");
    }

    @Override
    public Result create(String raw, PlaceholderManager placeholderManager, ActuatorList actuatorList) {
        ActuatorList list1 = SentenceFactory.splitAndCreateSentences(placeholderManager,matcher.group("body"));
        ScopeCallableActuator actuator = new ScopeCallableActuator(list1.toArray(new CallableActuator[0]));
        return ResultType.DONE.withCallableActuator(actuator);
    }
}
