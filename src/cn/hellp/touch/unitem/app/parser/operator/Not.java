package cn.hellp.touch.unitem.app.parser.operator;

import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.predicate.logical.Un;

public class Not extends SingleOperator{
    @Override
    public ISelector<?> eval(UEnv env) {
        return new Un(super.Child.eval(env));
    }

    @Override
    public int getPriority() {
        return 14;
    }

    @Override
    public Associative getAssociative() {
        return Associative.LEFT;
    }
}
