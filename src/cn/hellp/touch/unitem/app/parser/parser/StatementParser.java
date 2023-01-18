package cn.hellp.touch.unitem.app.parser.parser;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.ParseReader;
import cn.hellp.touch.unitem.app.parser.block.Block;
import cn.hellp.touch.unitem.app.parser.node.Nop;
import cn.hellp.touch.unitem.app.parser.operator.Associative;
import cn.hellp.touch.unitem.app.parser.operator.Operator;
import cn.hellp.touch.unitem.auxiliary.ERROR;

import java.util.ArrayList;
import java.util.Arrays;

public class StatementParser {
    private ArrayList<CodeNode> nodes = new ArrayList<>();
    private TokenDecider td = new TokenDecider();
    private CodeNode last;

    static boolean isOperator(CodeNode c) {
        return c instanceof Operator;
    }

    public CodeNode parseTree() {// 1+2*3+4*5
        Operator ret = null;// top level operator
        Operator pending = null;// deciding operator
        CodeNode last = null;// last node
        if (nodes.size() == 1)
            return nodes.get(0);
        for (CodeNode current : nodes) {
            if (StatementParser.isOperator(current)) {
                Operator op = (Operator) current;
                if (pending != null) {
                    if (pending.getPriority() >= op.getPriority()
                            + (op.getAssociative() == Associative.RIGHT ? 0 : 1)) {
                        // ret.setChildren(null,op);
                        pending.setChildren(null, last);
                        op.setChildren(ret, null);
                        ret = op;
                    } else {
                        pending.setChildren(null, op);
                        op.setChildren(last, null);
                    }
                } else {
                    op.setChildren(last, null);
                }
                last = null;
                pending = op;
                if (ret == null) {
                    ret = op;
                }
            } else if (last == null) {
                last = current;
            } else
                throw new ERROR("错误出现的 '" + current.toString() + "在" + last.toString() + "'之后,缺失运算符.");
        }
        if (last != null) {
            if (pending != null) {
                pending.setChildren(null, last);
            } else
                return last;
        }
        return ret;
    }

    public void clear() {
        nodes.clear();
    }

    public CodeNode parseUntilOrBlock(ParseReader reader, char until) {
        while (true) {
            char c = reader.eatAllSpace();
            if(c!=until && c!=0) {
                put(td.parser(reader));
                if (last instanceof Block) {
                    break;
                }
            } else {
                break;
            }
        }
        CodeNode ret = parseTree();
        if (ret == null) {
            ret = new Nop();
        }
        if (reader.read() == until) {
            reader.eat();
        }
        td.reset();
        clear();
        return ret;
    }

    public CodeNode parseUntil(ParseReader reader, char... untils) {
        Arrays.parallelSort(untils);
        while (true) {
            char c = reader.eatAllSpace();
            int srh = Arrays.binarySearch(untils, c);
            if (srh < 0) {
                put(td.parser(reader));
            } else {
                break;
            }
        }
        CodeNode ret = parseTree();
        if (ret == null) {
            ret = new Nop();
        }
        td.reset();
        clear();
        return ret;
    }


    public CodeNode parseUntilOrEnd(ParseReader reader, char until) {
        while (true) {
            if (!reader.has()) {
                break;
            }
            char c = reader.eatAllSpace();
            if (!reader.has()) {
                break;
            }
            if (c != until) {
                put(td.parser(reader));
                if (last instanceof Block) {
                    break;
                }
            } else {
                break;
            }
        }
        CodeNode ret = parseTree();
        if (ret == null) {
            ret = new Nop();
        }
        if (reader.has() && reader.read() == until) {
            reader.eat();
        }
        td.reset();
        clear();
        return ret;
    }

    public void put(CodeNode node) {
        if(node!=null)
            nodes.add(last = node);
    }
}
