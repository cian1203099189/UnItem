package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.tools.number.RemainderNumber;

public class Mod extends DoubleOperator{
    @Override
    public ISelector<?> eval(UEnv env) {
        return new RemainderNumber(super.left.eval(env),super.right.eval(env));
    }

    @Override
    public int getPriority() {
        return 13;
    }

    @Override
    public String getToken() {
        return "%";
    }
}
