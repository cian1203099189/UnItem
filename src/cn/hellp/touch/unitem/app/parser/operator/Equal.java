package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.Assignable;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;

public class Equal extends DoubleOperator{
    @Override
    public ISelector<?> eval(UEnv env) {
        return ((Assignable) left).assign(env,right.eval(env));
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public String getToken() {
        return "=";
    }
}
