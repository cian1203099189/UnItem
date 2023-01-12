package cn.hellp.touch.unitem.app;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class SentenceReader extends Reader {
    private final BufferedReader reader;

    public SentenceReader(InputStream stream) {
        this.reader=new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }


    public String readSentence() throws IOException {
        int i = 0;
        StringBuilder builder = new StringBuilder();
        char[] cArray = new char[1];
        while (reader.read(cArray)>0) {
            char c=cArray[0];
            if('{'==c) {
                i++;
                builder.append(c);
            } else if ('}'==c) {
                i--;
                builder.append(c);
                if(i==0) {
                    return builder.toString();
                }
            } else if (';' == c && i == 0) {
                return builder.toString();
            } else {
                builder.append(c);
            }
        }
        return null;
    }

    public static String[] splitSentences(String s) {
        StringBuilder builder = new StringBuilder();
        char[] raw = s.toCharArray();
        int point=0;
        int i = 0;
        List<String> result = new ArrayList<>();
        while (point< raw.length) {
            char c=raw[point];
            if('{'==c) {
                i++;
                builder.append(c);
            } else if ('}'==c) {
                i--;
                if(i==0) {
                    result.add(builder.toString());
                }
                builder.append(c);
            } else if (';' == c && i == 0) {
                result.add(builder.toString());
            } else {
                builder.append(c);
            }
            point++;
        }
        return result.toArray(new String[0]);
    }

    @Override
    public int read(char @NotNull [] cbuf, int off, int len) throws IOException {
        return reader.read(cbuf,off,len);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
