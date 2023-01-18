package cn.hellp.touch.unitem.app.parser.statement;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.ParseReader;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.app.parser.block.Block;
import cn.hellp.touch.unitem.app.parser.block.CodeBlock;
import cn.hellp.touch.unitem.app.parser.parser.StatementParser;
import cn.hellp.touch.unitem.selector.ISelector;

public class IfStatement implements Block {
    private CodeNode Condition;
    private CodeNode If;
    private CodeNode Else;

    public IfStatement() {
    }

    @Override
    public CodeNode parser(ParseReader reader) {
        StatementParser parser = new StatementParser();
        char c = reader.eatAllSpace();
        if (c == '(') {
            parser.clear();
            c = reader.eat();
            Condition = parser.parseUntil(reader, ')');
            c = reader.eat();
        }
        c = reader.eatAllSpace();
        if (c == '{') {
            c = reader.eat();
            If = new CodeBlock().parser(reader);
        } else {
            If = parser.parseUntil(reader, ';');
        }
        if(!reader.has()) {
            return this;
        }
        c = reader.eatAllSpace();
        if (c == 'e' && reader.reads(0, 4).equals("else")) {
            reader.eat();
            reader.eat();
            reader.eat();
            reader.eat();
            c = reader.eatAllSpace();
            if (c == '{') {
                c = reader.eat();
                Else = new CodeBlock().parser(reader);
            } else {
                Else = parser.parseUntil(reader, ';');
            }
            reader.eat();
        }
        return this;
    }

    @Override
    public ISelector<?> eval(UEnv env) {
        for (Object condition : Condition.eval(env).select(env.getCaller())) {
            if(((Boolean) condition)) {
                If.eval(env);
            } else if (Else != null) {
                Else.eval(env);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "if(" + Condition.toString() + ")\n" + If.toString()
                + (Else != null ? "\nelse\n" + Else : "");
    }
}
