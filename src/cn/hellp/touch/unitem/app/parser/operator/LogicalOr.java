package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.predicate.logical.Or;

public class LogicalOr extends DoubleOperator {
    @Override
    public ISelector<?> eval(UEnv env) {
        return new Or(left.eval(env),right.eval(env));
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public String getToken() {
        return "||";
    }
}
