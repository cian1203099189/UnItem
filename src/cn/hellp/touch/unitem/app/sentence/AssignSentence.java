package cn.hellp.touch.unitem.app.sentence;

import cn.hellp.touch.unitem.app.ActuatorList;
import cn.hellp.touch.unitem.app.PlaceholderManager;
import cn.hellp.touch.unitem.app.Sentence;
import cn.hellp.touch.unitem.selector.ISelector;

public class AssignSentence extends Sentence {


    public AssignSentence(PlaceholderManager manager, ActuatorList actuatorList) {
        super(manager, actuatorList);
    }

    @Override
    public void init(Object... args) {
        String placeholder = (String) args[0];
        ISelector<?> selector = (ISelector<?>) args[1];
        manager.put(placeholder,selector);
    }
}
