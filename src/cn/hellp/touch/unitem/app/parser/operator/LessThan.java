package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.predicate.Less;

public class LessThan extends DoubleOperator {
    @Override
    public String getToken() {
        return "<";
    }

    @Override
    public ISelector<?> eval(UEnv env) {
        return new Less(left.eval(env),right.eval(env));
    }

    @Override
    public int getPriority() {
        return 10;
    }
}
