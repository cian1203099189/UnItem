package cn.hellp.touch.unitem.app.parser.node;

import cn.hellp.touch.unitem.app.Registry;
import cn.hellp.touch.unitem.app.SelectorFactory;
import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.app.parser.statement.ArgumentNode;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.selector.ISelector;

public class SelectorNode implements CodeNode {
    protected SelectorFactory value;
    protected CodeNode[] args;

    public SelectorNode(String name , ArgumentNode args) {
        this.value = Registry.SELECTOR.get(name);
        if(value==null) {
            throw new ERROR("Unknown get \""+name+"\"");
        }
        this.args=args.getAll().toArray(new CodeNode[0]);
    }

    @Override
    public ISelector<?> eval(UEnv env) {
        return value.create(args,env);
    }
}
