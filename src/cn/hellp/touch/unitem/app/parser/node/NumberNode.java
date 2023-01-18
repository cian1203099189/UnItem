package cn.hellp.touch.unitem.app.parser.node;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.ParseReader;
import cn.hellp.touch.unitem.app.parser.Parser;
import cn.hellp.touch.unitem.app.parser.UEnv;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.auxiliary.Number;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.ValueSelector;

public class NumberNode implements CodeNode , Parser {
    private Number value;

    @Override
    public ISelector<?> eval(UEnv env) {
        return new ValueSelector<>(value);
    }

    @Override
    public CodeNode parser(ParseReader reader) {
        Double value = 0.0D;
        Double dot = 1D;
        boolean dotted = false;
        boolean hex = false;
        boolean oct = false;
        int degit = 10;
        char c = reader.read();
        char c2 = reader.read(1);
        if (c == '0') {
            if (c2 == 'x' || c2 == 'X') {
                hex = true;
                degit = 16;
            } else {
                oct = true;
                degit = 8;
            }
        }
        while (true) {
            if (c >= '0' && c <= '9') {
                if (oct && c >= '8')
                    throw new ERROR("错误的八进制数");
                value *= degit;
                value += c - '0';
                if (dotted) {
                    dot *= degit;
                }

            } else if (c == '.') {
                dotted = true;
            } else if (hex) {
                char lowerc = Character.toLowerCase(c);
                if (lowerc >= 'a' && lowerc <= 'f') {
                    value *= degit;
                    value += c - 'a' + 9;
                    if (dotted) {
                        dot *= degit;
                    }

                }
            } else {
                break;
            }
            c = reader.eat();
        }
        value /= dot;
        this.value=new Number(value);
        // TODO Auto-generated method stub
        return this;
    }
}
