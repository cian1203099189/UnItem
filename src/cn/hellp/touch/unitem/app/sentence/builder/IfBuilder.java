package cn.hellp.touch.unitem.app.sentence.builder;

import cn.hellp.touch.unitem.app.ActuatorList;
import cn.hellp.touch.unitem.app.CallableActuator;
import cn.hellp.touch.unitem.app.IfCallableActuator;
import cn.hellp.touch.unitem.app.PlaceholderManager;
import cn.hellp.touch.unitem.selector.ISelector;

import java.util.regex.Pattern;

import static cn.hellp.touch.unitem.app.sentence.SentenceFactory.getValue;
import static cn.hellp.touch.unitem.app.sentence.SentenceFactory.splitAndCreateSentences;

public class IfBuilder extends SentenceBuilder {
    private IfCallableActuator actuator;

    public IfBuilder() {
        pattern = Pattern.compile("if(\\s)*\\((?<predicate>(.|\\n)*)\\)(\\s)*(?<body>[\\s\\S]*)");
    }

    @Override
    public Result callback(CallbackReason reason, String s,PlaceholderManager placeholderManager) {
        if(reason==CallbackReason.COMPLETE) {
            ActuatorList else_ = splitAndCreateSentences(placeholderManager,s);
            actuator.setElse_(else_.toArray(new CallableActuator[0]));
            return ResultType.DONE.withCallableActuator(actuator);
        } else if (reason == CallbackReason.UNMATCHED) {
            return ResultType.DONE.withCallableActuator(actuator);
        }
        return ResultType.REMATCH.empty();
    }

    @Override
    public Result create(String raw, PlaceholderManager placeholderManager, ActuatorList actuatorList) {
        ISelector<?> predicate = getValue(matcher.group("predicate"), placeholderManager);
        PlaceholderManager manager1 = placeholderManager.clone();
        ActuatorList list1 = splitAndCreateSentences(manager1, matcher.group("body"));
        actuator = new IfCallableActuator(new CallableActuator[0],predicate,list1.toArray(new CallableActuator[0]));
        return ResultType.UNDONE.withNeedNext(ElseBuilder.pattern);
    }
}
