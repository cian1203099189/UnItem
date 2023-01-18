package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.tools.number.MulNumber;

public class Multiply extends DoubleOperator {
    @Override
    public ISelector<?> eval(UEnv env) {
        return new MulNumber(left.eval(env),right.eval(env));
    }

    @Override
    public int getPriority() {
        return 13;
    }

    @Override
    public String getToken() {
        return "*";
    }
}
