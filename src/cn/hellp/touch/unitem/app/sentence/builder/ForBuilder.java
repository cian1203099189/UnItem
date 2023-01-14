package cn.hellp.touch.unitem.app.sentence.builder;

import cn.hellp.touch.unitem.app.ActuatorList;
import cn.hellp.touch.unitem.app.CallableActuator;
import cn.hellp.touch.unitem.app.ForCallableActuator;
import cn.hellp.touch.unitem.app.PlaceholderManager;
import cn.hellp.touch.unitem.selector.ISelector;

import java.util.regex.Pattern;

import static cn.hellp.touch.unitem.app.sentence.SentenceFactory.handlePragma;
import static cn.hellp.touch.unitem.app.sentence.SentenceFactory.splitAndCreateSentences;

public class ForBuilder extends SentenceBuilder{
    public ForBuilder() {
        pattern= Pattern.compile("for(\\s+)(?<placeholder>\\S+?)(\\s+)in(\\s+)range(\\s*)\\((?<range>(.|\\s)*?)\\)(\\s*)\\{(?<body>([\\s\\S]*))}");
    }

    @Override
    public Result create(String raw, PlaceholderManager placeholderManager, ActuatorList actuatorList) {
        String placeholder = matcher.group("placeholder");
        ISelector<?>[] range = handlePragma(matcher.group("range"),placeholderManager);
        PlaceholderManager manager1 = placeholderManager.clone();
        ActuatorList list1 = splitAndCreateSentences(manager1,matcher.group("body"));
        ForCallableActuator actuator = new ForCallableActuator(placeholder,placeholderManager,range[0],range[1],list1.toArray(new CallableActuator[0]));
        return ResultType.DONE.withCallableActuator(actuator);
    }
}
