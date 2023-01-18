package cn.hellp.touch.unitem.app.parser;

import cn.hellp.touch.unitem.selector.ISelector;

public interface CodeNode {
    /**
     *
     * @param env Runtime environment.
     * @return the value of result.
     */

    ISelector<?> eval(UEnv env);
}
