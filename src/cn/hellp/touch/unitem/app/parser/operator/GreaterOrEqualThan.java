package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.predicate.Equals;
import cn.hellp.touch.unitem.selector.predicate.Greater;
import cn.hellp.touch.unitem.selector.predicate.logical.Or;

public class GreaterOrEqualThan extends DoubleOperator {
    @Override
    public ISelector<?> eval(UEnv env) {
        ISelector<?> l = left.eval(env);
        ISelector<?> r = right.eval(env);
        return new Or(new Greater(l,r),new Equals(l,r));
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public String getToken() {
        return ">=";
    }
}
