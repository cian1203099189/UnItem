package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.EmptySelector;
import cn.hellp.touch.unitem.selector.ISelector;

public class Break extends SingleOperator {
    @Override
    public ISelector<?> eval(UEnv env) {
        env.setSkipped(true);
        return new EmptySelector();
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public Associative getAssociative() {
        return Associative.LEFT;
    }
}
