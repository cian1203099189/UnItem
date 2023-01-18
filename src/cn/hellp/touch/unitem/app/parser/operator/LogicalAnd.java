package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.predicate.logical.And;

public class LogicalAnd extends DoubleOperator {
    @Override
    public String getToken() {
        return "&&";
    }

    @Override
    public ISelector<?> eval(UEnv env) {
        return new And(left.eval(env),right.eval(env));
    }

    @Override
    public int getPriority() {
        return 5;
    }
}
