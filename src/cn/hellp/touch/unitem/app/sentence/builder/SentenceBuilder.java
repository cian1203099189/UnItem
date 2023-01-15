package cn.hellp.touch.unitem.app.sentence.builder;

import cn.hellp.touch.unitem.app.ActuatorList;
import cn.hellp.touch.unitem.app.PlaceholderManager;

import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SentenceBuilder {



    protected Pattern pattern;
    protected Matcher matcher;

    public boolean matches(String s) {
        return (matcher=pattern.matcher(s)).matches();
    }

    public abstract Result create(String raw, PlaceholderManager placeholderManager, ActuatorList actuatorList , @Nullable SentenceBuilder lastSentence);
    public Result callback(CallbackReason reason,String s,PlaceholderManager manager) {
        return ResultType.DONE.empty();
    }

    public Pattern getPattern() {
        return pattern;
    }
}
