package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.predicate.Equals;
import cn.hellp.touch.unitem.selector.predicate.logical.Un;

public class NotEquals extends DoubleOperator {
    @Override
    public ISelector<?> eval(UEnv env) {
        return new Un(new Equals(left.eval(env),right.eval(env)));
    }

    @Override
    public int getPriority() {
        return 9;
    }

    @Override
    public String getToken() {
        return "!=";
    }
}
