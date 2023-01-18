package cn.hellp.touch.unitem.app.parser;

import cn.hellp.touch.unitem.selector.ISelector;

public interface Assignable extends CodeNode {
    ISelector<?> assign(UEnv env,ISelector<?> val);
}
