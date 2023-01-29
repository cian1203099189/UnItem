package cn.hellp.touch.unitem.app.parser.block;

import cn.hellp.touch.unitem.app.VarManager;
import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.ParseReader;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.app.parser.parser.StatementParser;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.selector.ISelector;

import java.util.ArrayList;
import java.util.List;

public class CodeBlock implements Block{
    protected final List<CodeNode> nodes = new ArrayList<>();
    protected StatementParser parser = new StatementParser();

    public void put(CodeNode node) {
        nodes.add(node);
    }

    @Override
    public CodeNode parser(ParseReader reader) {
        while (true) {
            if (!reader.has()) {
                break;
            }
            char c = reader.eatAllSpace();
            if (!reader.has()) {
                break;
            }

            if (c != '}') {
                put(parser.parseUntilOrBlock(reader, ';'));
                parser.clear();
            } else {
                reader.eat();
                break;
            }
        }
        return this;
    }

    @Override
    public ISelector<?> eval(UEnv env) {
        int line = 0 ;
        final int length = nodes.size();
        try {
            UEnv env1 = env.createChildEnv();
            for (; line < length; line++) {
                nodes.get(line).eval(env1);
                if(env1.isSkipped() || env1.isStopped()) {
                    env.setSkipped(env1.isSkipped());
                    env.setStopped(env1.isStopped());
                    break;
                }
                if(env1.isContinued()) {
                    break;
                }
            }
        } catch (ERROR error) {
            throw new ERROR("Wrong config of line "+(line+1),error);
        }
        return null;
    }
}
