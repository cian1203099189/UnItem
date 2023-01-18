package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.predicate.Equals;
import cn.hellp.touch.unitem.selector.predicate.Less;
import cn.hellp.touch.unitem.selector.predicate.logical.Or;

public class LessOrEqualThan extends DoubleOperator {
    @Override
    public String getToken() {
        return "<=";
    }

    @Override
    public ISelector<?> eval(UEnv env) {
        ISelector<?> l = left.eval(env);
        ISelector<?> r = right.eval(env);
        return new Or(new Less(l,r),new Equals(l,r));
    }

    @Override
    public int getPriority() {
        return 10;
    }
}
