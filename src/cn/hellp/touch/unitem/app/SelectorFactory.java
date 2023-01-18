package cn.hellp.touch.unitem.app;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.selector.ISelector;

import java.util.function.Function;

public class SelectorFactory {
    protected Function<ISelector<?>[],ISelector<?>> createFunc;

    public SelectorFactory(Function<ISelector<?>[], ISelector<?>> createFunc) {
        this.createFunc = createFunc;
    }

    public ISelector<?> create(CodeNode[] args , UEnv env) {
        ISelector<?>[] pragma = new ISelector[args.length];
        for (int i = 0; i < args.length; i++) {
            pragma[i]=args[i].eval(env);
        }
        return createFunc.apply(pragma);
    }
}
