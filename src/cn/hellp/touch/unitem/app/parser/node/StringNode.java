package cn.hellp.touch.unitem.app.parser.node;

import cn.hellp.touch.unitem.app.parser.*;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.ValueSelector;

public class StringNode implements CodeNode, Parser {
    private String variant;

    public StringNode() {
    }

    public StringNode(String variant) {
        this.variant = variant;
    }

    public String getVariant() {
        return variant;
    }

    @Override
    public ISelector<?> eval(UEnv env) {
        return new ValueSelector<>(variant);
    }


    @Override
    public CodeNode parser(ParseReader reader) {
        StringBuilder sb = new StringBuilder();
        char start = reader.read();
        char c = reader.eat();
        while (c != start) {
            if (c == '\\') {
                c = reader.eat();
                switch (c) {
                    case '\\':
                        sb.append('\\');
                        break;
                    case '\'':
                        sb.append('\'');
                        break;
                    case '"':
                        sb.append('"');
                        break;
                    case 'a':
                        sb.append('\u0007');
                        break;
                    case 'b':
                        sb.append('\u0008');
                        break;
                    case 'f':
                        sb.append('\u000C');
                        break;
                    case 'n':
                        sb.append('\n');
                        break;
                    case 'r':
                        sb.append('\r');
                        break;
                    case 't':
                        sb.append('\t');
                        break;
                    case 'v':
                        sb.append('\u000B');
                        break;
                    case 'X':// \x0008
                    case 'x':
                        StringBuilder x = new StringBuilder(8);
                        char c1 = reader.eat();
                        for (int i = 0; i < 8; i++) {
                            if (c1 >= '0' && c1 <= '9' || c1 >= 'a' && c1 <= 'f' || c1 >= 'A' && c1 <= 'F') {
                                x.append(c1);
                                c1 = reader.eat();
                            } else {
                                c = c1;
                                break;
                            }
                        }
                        sb.append((char) Integer.parseInt(x.toString(), 16));
                        break;
                }
                c = reader.eat();
                continue;
            }
            sb.append(c);
            c = reader.eat();
        }
        reader.eat();
        variant = sb.toString();
        return this;
    }
}
