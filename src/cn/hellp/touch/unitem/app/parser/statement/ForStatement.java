package cn.hellp.touch.unitem.app.parser.statement;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.ParseReader;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.app.parser.block.Block;
import cn.hellp.touch.unitem.app.parser.node.LiteralNode;
import cn.hellp.touch.unitem.app.parser.parser.StatementParser;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.auxiliary.Number;
import cn.hellp.touch.unitem.plugin.Main;
import cn.hellp.touch.unitem.selector.EmptySelector;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.ValueSelector;

import java.util.List;

public class ForStatement implements Block {
    protected CodeNode range1;
    protected CodeNode range2;
    protected CodeNode body;
    private LiteralNode var;


    private static int min(Object... ints) {
        int result = Integer.MAX_VALUE;
        for (Object anInt : ints) {
            result=Math.min(result, ((Number) anInt).toInteger());
        }
        return result;
    }

    private static int max(Object... ints) {
        int result = Integer.MIN_VALUE;
        for (Object anInt : ints) {
            result=Math.max(result,((Number) anInt).toInteger());
        }
        return result;
    }

    @Override
    public ISelector<?> eval(UEnv env) {
        int min,max;
        if(Main.getSetting("for-MaxRange").asBoolean()) {
            min = min((Object[]) range1.eval(env).select(env.getCaller()));
            max = max(((Object[]) range2.eval(env).select(env.getCaller())));
        } else {
            min = max((Object[]) range1.eval(env).select(env.getCaller()));
            max = min(((Object[]) range2.eval(env).select(env.getCaller())));
        }
        final int step = max>min ? 1 : -1;
        for ( ; min!=max ; min+=step) {
            var.assign(env,new ValueSelector<>(new Number(min)));
            body.eval(env);
        }
        return new EmptySelector();
    }

    @Override
    public CodeNode parser(ParseReader reader) {
        StatementParser sp = new StatementParser();
        var = (LiteralNode) new LiteralNode().parser(reader);
        reader.eatAllSpace();
        //System.out.println(reader.read());
        if(!reader.has()) {
            throw new ERROR("Unexpected ending of the text");
        }
        reader.eat(2); //in
        //System.out.println(reader.read());
        reader.eatAllSpace();
        //System.out.println(reader.read());
        reader.eat(5); //range
        //System.out.println(reader.read());
        reader.eatAllSpace();
        //System.out.println(reader.read());
        reader.eat();
        //System.out.println(reader.read());
        List<CodeNode> range = ((ArgumentNode) new ArgumentNode('(').parser(reader)).getAll();
        if(range.size()<2) {
            throw new ERROR("Invalid range for for in range().");
        }
        range1=range.get(0);
        range2=range.get(1);
        reader.eatAllSpace();
        body = sp.parseUntilOrBlock(reader,';');
        return this;
    }
}
