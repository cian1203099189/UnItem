package cn.hellp.touch.unitem.app.sentence.builder;

import cn.hellp.touch.unitem.app.*;
import cn.hellp.touch.unitem.selector.ISelector;

import javax.annotation.Nullable;
import java.util.regex.Pattern;

import static cn.hellp.touch.unitem.app.sentence.SentenceFactory.getValue;
import static cn.hellp.touch.unitem.app.sentence.SentenceFactory.splitAndCreateSentences;

public class IfBuilder extends SentenceBuilder {
    private IfCallableActuator actuator;

    public IfBuilder() {
        pattern = Pattern.compile("if(\\s)*\\((?<predicate>(.|\\n)*?)\\)(\\s)*(?<body>[\\s\\S]*)");
    }

    @Override
    public Result callback(CallbackReason reason, String s,PlaceholderManager placeholderManager) {
        if(reason==CallbackReason.COMPLETE) {
            ActuatorList else_ = new ActuatorList();
            ElseBuilder builder = new ElseBuilder();
            builder.matches(s);
            builder.create(s,placeholderManager,else_,this);
            actuator.setElse_(else_.toArray(new CallableActuator[0]));
            return ResultType.DONE.withCallableActuator(actuator);
        } else if (reason == CallbackReason.UNMATCHED) {
            return ResultType.DONE.withCallableActuator(actuator);
        }
        return ResultType.REMATCH.empty();
    }

    private static String getPredicate(String raw) {
        StringBuilder builder = new StringBuilder();
        int i = -1;
        boolean inString = false;
        for (char c : raw.toCharArray()) {
            if(!inString) {
                if('('==c) {
                    i++;
                    if(i>0) {
                        builder.append(c);
                    }
                }else
                if(')'==c) {
                    i--;
                    if(i==-1) {
                        return builder.toString();
                    }
                    builder.append(c);
                }else
                if('\"'==c && i>=0) {
                    builder.append(c);
                    inString=true;
                }else
                if(i>=0) {
                    builder.append(c);
                }
            } else {
                if('\"'==c) {
                    inString=false;
                }
                builder.append(c);
            }
        }
        return builder.toString();
    }

    private static String getBody(String raw) {
        StringBuilder builder = new StringBuilder();
        int i = -1;
        int j = -1;
        boolean inString = false;
        for (char c : raw.toCharArray()) {
            if(!inString) {
                if('('==c && j==-1) {
                    i++;
                }else
                if('{'==c && i==-1) {
                    j++;
                    builder.append(c);
                }else
                if('}'==c && i==-1) {
                    j--;
                    builder.append(c);
                }else
                if(')'==c && j==-1) {
                    i--;
                }else
                if('\"'==c && j>=0) {
                    builder.append(c);
                    inString=true;
                }else if(j>=0){
                    builder.append(c);
                }

            } else {
                if('\"'==c) {
                    inString=false;
                }
                builder.append(c);
            }
        }
        return builder.toString();
    }

    @Override
    public Result create(String raw, PlaceholderManager placeholderManager, ActuatorList actuatorList, @Nullable SentenceBuilder lastSentence) {
        String predicateString = getPredicate(raw);
        ISelector<?> predicate = getValue(predicateString, placeholderManager);
        ActuatorList list1 = splitAndCreateSentences(placeholderManager, getBody(raw));
        actuator = new IfCallableActuator(new CallableActuator[0],predicate,list1.toArray(new CallableActuator[0]));
        return ResultType.UNDONE.withNeedNext(ElseBuilder.pattern0);
    }
}
