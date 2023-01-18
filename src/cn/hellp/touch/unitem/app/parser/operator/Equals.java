package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;

public class Equals extends DoubleOperator{
    @Override
    public ISelector<?> eval(UEnv env) {
        return new cn.hellp.touch.unitem.selector.predicate.Equals(left.eval(env),right.eval(env));
    }

    @Override
    public int getPriority() {
        return 9;
    }

    @Override
    public String getToken() {
        return "==";
    }
}
