package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.predicate.Greater;

public class GreaterThan extends DoubleOperator {
    @Override
    public ISelector<?> eval(UEnv env) {
        return new Greater(left.eval(env),right.eval(env));
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public String getToken() {
        return ">";
    }
}
