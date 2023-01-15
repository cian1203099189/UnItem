package cn.hellp.touch.unitem.app.sentence.builder;

import cn.hellp.touch.unitem.app.ActuatorList;
import cn.hellp.touch.unitem.app.CallableActuator;
import cn.hellp.touch.unitem.app.PlaceholderManager;
import cn.hellp.touch.unitem.app.ScopeCallableActuator;
import cn.hellp.touch.unitem.app.sentence.SentenceFactory;
import cn.hellp.touch.unitem.auxiliary.ERROR;

import javax.annotation.Nullable;
import java.util.regex.Pattern;

public class ElseBuilder extends SentenceBuilder{
    public ElseBuilder() {
        pattern=pattern0;
    }

    public static final Pattern pattern0 = Pattern.compile("else(\\s+)(?<body>[\\s\\S]*)");

    @Override
    public Result create(String raw, PlaceholderManager placeholderManager, ActuatorList actuatorList, @Nullable SentenceBuilder lastSentence) {
        if(!(lastSentence instanceof IfBuilder)) {
            throw new ERROR("can't use else with out if!");
        }
        ActuatorList list1 = SentenceFactory.splitAndCreateSentences(placeholderManager,matcher.group("body"));
        return ResultType.DONE.withCallableActuator(new ScopeCallableActuator(list1.toArray(new CallableActuator[0])));
    }
}
