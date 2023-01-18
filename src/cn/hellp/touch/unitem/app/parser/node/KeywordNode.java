package cn.hellp.touch.unitem.app.parser.node;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;

public class KeywordNode implements CodeNode {
    private final ISelector<?> value;

    public KeywordNode(ISelector<?> value) {
        this.value = value;
    }

    @Override
    public ISelector<?> eval(UEnv env) {
        return value;
    }
}
