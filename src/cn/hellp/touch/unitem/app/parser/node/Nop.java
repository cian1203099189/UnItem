package cn.hellp.touch.unitem.app.parser.node;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.EmptySelector;
import cn.hellp.touch.unitem.selector.ISelector;

public class Nop implements CodeNode {
    @Override
    public ISelector<?> eval(UEnv env) {
        return null;
    }
}
