package cn.hellp.touch.unitem.app.sentence.builder;

import cn.hellp.touch.unitem.actuator.IActuator;
import cn.hellp.touch.unitem.app.ActuatorList;
import cn.hellp.touch.unitem.app.CallableActuator;
import cn.hellp.touch.unitem.app.PlaceholderManager;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.selector.ISelector;

import java.util.regex.Pattern;

import static cn.hellp.touch.unitem.app.sentence.SentenceFactory.actuatorMap;
import static cn.hellp.touch.unitem.app.sentence.SentenceFactory.handlePragma;

public class ActuateBuilder extends SentenceBuilder{
    public ActuateBuilder() {
        pattern= Pattern.compile("#(?<actuator>\\w+?)\\((?<pragma>.*)\\)");
    }

    @Override
    public Result create(String raw, PlaceholderManager placeholderManager, ActuatorList actuatorList) {
        IActuator actuator = actuatorMap.get(matcher.group("actuator"));
        if (actuator == null) {
            throw new ERROR("Unknown actuator " + matcher.group("actuator"));
        }
        ISelector<?>[] pragma = handlePragma(matcher.group("pragma"),placeholderManager);
        return ResultType.DONE.withCallableActuator(new  CallableActuator(actuator,pragma));
    }
}
