package cn.hellp.touch.unitem.app.parser.node;

import cn.hellp.touch.unitem.app.parser.*;
import cn.hellp.touch.unitem.selector.ISelector;

public class LiteralNode implements Assignable , Parser {
    private String name;
    public LiteralNode(String name) {
        this.name=name;
    }

    public LiteralNode() {
    }

    @Override
    public ISelector<?> eval(UEnv env) {
        return env.getManager().get(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public ISelector<?> assign(UEnv env, ISelector<?> val) {
        env.getManager().put(name,val);
        return val;
    }

    @Override
    public CodeNode parser(ParseReader reader) {
        StringBuilder builder = new StringBuilder();
        char c = reader.eatAllSpace();
        do {
            builder.append(c);
        }while (Character.isJavaIdentifierPart(c= reader.eat()) && c!= '$' && c!=0 && !Character.isWhitespace(c));
        name= builder.toString();
        return this;
    }
}
