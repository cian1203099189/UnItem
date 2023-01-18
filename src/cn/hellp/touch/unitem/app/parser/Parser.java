package cn.hellp.touch.unitem.app.parser;

public interface Parser {
    /**
     *
     * @param reader the read context.
     * @return the codeNode from parser.
     */

    CodeNode parser(ParseReader reader);
}
