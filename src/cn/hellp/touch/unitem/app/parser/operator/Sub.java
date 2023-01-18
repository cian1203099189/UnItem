package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.tools.number.SubNumber;

public class Sub extends DoubleOperator{
    @Override
    public ISelector<?> eval(UEnv env) {
        return new SubNumber(left.eval(env),right.eval(env));
    }

    @Override
    public int getPriority() {
        return 12;
    }

    @Override
    public String getToken() {
        return "-";
    }
}
