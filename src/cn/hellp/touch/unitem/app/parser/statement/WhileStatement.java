package cn.hellp.touch.unitem.app.parser.statement;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.ParseReader;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.app.parser.block.Block;
import cn.hellp.touch.unitem.app.parser.block.CodeBlock;
import cn.hellp.touch.unitem.app.parser.parser.StatementParser;
import cn.hellp.touch.unitem.selector.EmptySelector;
import cn.hellp.touch.unitem.selector.ISelector;

public class WhileStatement implements Block {
    private CodeNode condition;
    private CodeNode body;

    @Override
    public ISelector<?> eval(UEnv env) {
        UEnv env1 = env.createChildEnv();
        while (true) {
            Object[] conditions = condition.eval(env).select(env.getCaller());
            boolean allFalse = true;
            for (Object o : conditions) {
                if (((Boolean) o)) {
                    allFalse = false;
                    body.eval(env1);
                    if(env1.isSkipped() || env1.isStopped()) {
                        env.setStopped(env1.isStopped());
                        return new EmptySelector();
                    }
                    break;
                }
            }
            if(allFalse) {
                break;
            }
        }
        return new EmptySelector();
    }

    @Override
    public CodeNode parser(ParseReader reader) {
        StatementParser sp = new StatementParser();
        char c = reader.eatAllSpace();
        if(c=='(') {
            reader.eat();
            condition = sp.parseUntil(reader,')');
            reader.eat();
        }
        c = reader.eatAllSpace();
        if (c == '{') {
            reader.eat();
            body = new CodeBlock().parser(reader);
        } else {
            body = sp.parseUntil(reader, ';');
        }
        return this;
    }
}
