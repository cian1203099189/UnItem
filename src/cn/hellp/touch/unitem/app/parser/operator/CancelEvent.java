package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.ValueSelector;

public class CancelEvent extends SingleOperator {
    @Override
    public ISelector<?> eval(UEnv env) {
        Object[] b = super.Child.eval(env).select(env.getCaller());
        if(b.length==0) {
            throw new RuntimeException("Invalid value of set cancel");
        }
        return new ValueSelector<>(env.setCancelEvent(((Boolean) b[b.length - 1])));
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
