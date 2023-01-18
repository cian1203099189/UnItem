package cn.hellp.touch.unitem.app.parser;

import cn.hellp.touch.unitem.auxiliary.ERROR;

public class ParseReader {
    private final String text;
    private int pos = 0;

    public ParseReader(String text) {
        this.text = text;
    }

    public char read() {
        if(pos>text.length()) {
            throw new ERROR("Wrong ending of the text");
        } else if (pos == text.length()) {
            return 0;
        }
        return text.charAt(pos);
    }

    public char eat() {
        pos++;
        if(has()) {
            return read();
        } else if (pos > text.length()) {
            throw new ERROR("Wrong ending of the text");
        } else return 0;
    }

    public char read(int off) {
        return text.charAt(pos + off);
    }


    public void eat(int count) {
        pos += count;
    }

    public boolean has() {
        return pos < text.length();
    }

    public char eatAllSpace() {
        if(!has()) {
            return 0;
        }
        while (Character.isWhitespace(read())) {
            pos++;
            if(pos>=text.length()) {
                return 0;
            }
        }
        return read();
    }

    public void eatLine() {
        if(read()!='\n') {
            while(eat()!='\n');
        }
        eat();
    }

    public String reads(int off, int count) {
        return text.substring(pos + off, pos + off + count);
    }
}
