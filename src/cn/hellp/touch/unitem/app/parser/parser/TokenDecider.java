package cn.hellp.touch.unitem.app.parser.parser;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.ParseReader;
import cn.hellp.touch.unitem.app.parser.Parser;
import cn.hellp.touch.unitem.app.parser.block.CodeBlock;
import cn.hellp.touch.unitem.app.parser.node.*;
import cn.hellp.touch.unitem.app.parser.operator.*;
import cn.hellp.touch.unitem.app.parser.statement.*;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.selector.ConsoleSelector;
import cn.hellp.touch.unitem.selector.ValueSelector;
import cn.hellp.touch.unitem.selector.entity.living.player.CallingPlayerSelector;

import java.util.HashMap;
import java.util.Map;

public class TokenDecider implements Parser {
    private static final Map<String, LiteralFactory> identifiers = new HashMap<>();
    private CodeNode last = null;

    @Override
    public CodeNode parser(ParseReader reader) {
        // TODO Auto-generated method stub
        char c = reader.eatAllSpace();
        if (c < '!')
            throw new ERROR("Invalid char "+c);
        else if (c < '0') {
            if (c == '"' || c == '\'')
                return last = new StringNode().parser(reader);
            return last = parseOperator(reader);
        } else if (c <= '9')
            return last = new NumberNode().parser(reader);
        else if (c < '@')
            return last = parseOperator(reader);
        else if (c <= 'Z')
            return last = parseLiteral(reader,c);
        else if (c <= '^')
            return last = parseOperator(reader);
        else if (c < '{')
            return last = parseLiteral(reader,c);
        else if (c <= '~')
            return last = parseOperator(reader);
        else
            return last = parseLiteral(reader,c);
    }

    public CodeNode parseOperator(ParseReader reader) {
        // TODO Auto-generated method stub
        Associative infer = Associative.RIGHT;
        char first = reader.read();
        char next = reader.eat();
        if (last == null || last instanceof Operator) {
            infer = Associative.LEFT;
        }
        switch (first) {
            case '!':
                if (infer == Associative.LEFT)
                    return new Not();
                else if (next == '=') {
                    reader.eat();
                    return new NotEquals();
                }
            case '#':
                return new FuncCall().parser(reader);
            case '$':
                return parseLiteral(reader,first);
            case '%':
                return new Mod();
            case '&':
                if (next == '&') {
                    return new LogicalAnd();
                }
            case '(':
                if(last instanceof StringNode)
                    return new SelectorNode(((StringNode) last).getVariant(), (ArgumentNode) new ArgumentNode('(').parser(reader));
                return new Parentness().parser(reader);
            case '*':
                return new Multiply();
            case '+':
                return new Add();
            case ',':
                return new ArgumentNode(';').parser(reader);
            case '-':
                return new Sub();
            case '/':
                if(next=='/') {
                    reader.eatLine();
                    return null;
                }
                return new Divided();
            case '<':
                if (next == '=') {
                    reader.eat();
                    return new LessOrEqualThan();
                } else
                    return new LessThan();
            case '=':
                if (next == '=') {
                    reader.eat();
                    return new Equals();
                } else if (next == '>') {
                    reader.eat();
                    return new Equal();
                } else
                    return new Equal();
            case '>':
                if (next == '=') {
                    reader.eat();
                    return new GreaterOrEqualThan();
                } else
                    return new GreaterThan();
            case '{':
                return new CodeBlock().parser(reader);
            case '|':
                if (next == '|') {
                    return new LogicalOr();
                }
            default:
                throw new ERROR("unexpected" + first);
        }
    }

    public CodeNode parseLiteral(ParseReader reader,char first) {
        StringBuilder sb = new StringBuilder();
        char ch = reader.read();
        boolean isVar = first=='$';
        do {
            sb.append(ch);
        } while (Character.isJavaIdentifierPart(ch = reader.eat()) && ch != '$' && ch != 0);
        String lite = sb.toString();
        LiteralFactory cn = TokenDecider.identifiers.get(lite);
        if (cn != null)
            return cn.create(reader, last);
        if(ch=='(') {
            reader.eat();
            return new SelectorNode(lite, (ArgumentNode) new ArgumentNode('(').parser(reader));
        }
        return isVar ? new LiteralNode(lite) : new StringNode(lite);
    }

    public CodeNode parseString(ParseReader reader) {
        StringBuilder sb = new StringBuilder();
        char ch = reader.read();
        do {
            sb.append(ch);
        } while ((ch= reader.read())!=',' && ch!=';' && ch!='(' && ch!=')' && ch!='{' && ch!='}' && ch!=0);
        return new StringNode(sb.toString());
    }

    public void reset() {
        last=null;
    }

    @FunctionalInterface
    interface LiteralFactory {
        CodeNode create(ParseReader reader, CodeNode last);
    }

    static {
        TokenDecider.identifiers.put("if", (reader, last) -> new IfStatement().parser(reader));
        TokenDecider.identifiers.put("break", (reader, last) -> new Break());
        TokenDecider.identifiers.put("return", (reader, last) -> new Return());
        TokenDecider.identifiers.put("for", (reader, last) -> new ForStatement().parser(reader));
        TokenDecider.identifiers.put("console", (reader, last) -> new KeywordNode(new ConsoleSelector()));
        TokenDecider.identifiers.put("owner_player", (reader, last) -> new KeywordNode(new CallingPlayerSelector()));
        TokenDecider.identifiers.put("true", (reader, last) -> new KeywordNode(new ValueSelector<>(true)));
        TokenDecider.identifiers.put("false", (reader, last) -> new KeywordNode(new ValueSelector<>(false)));
        TokenDecider.identifiers.put("cancel",(reader, last) -> new CancelEvent());
        TokenDecider.identifiers.put("continue",(reader, last1) -> new Continue());
        TokenDecider.identifiers.put("while",(reader, last1) -> new WhileStatement().parser(reader));
    }
}
