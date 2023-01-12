package cn.hellp.touch.unitem.auxiliary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ERROR extends RuntimeException {
    private final List<Throwable> exceptionList = new ArrayList<>();
    private int count = 0;

    public ERROR(Exception... exceptions) {
        Collections.addAll(exceptionList,exceptions);
        count+=exceptions.length;
    }

    public ERROR(Throwable cause) {
        super(cause);
        exceptionList.add(cause);
        count++;
    }

    public ERROR(String s) {
        super(s);
        count++;
    }

    public ERROR(String s, Exception e) {
        super(s,e);
        exceptionList.add(e);
        count++;
    }

    public void addException(Exception e) {
        exceptionList.add(e);
        ++count;
    }

    public List<Throwable> getExceptionList() {
        return exceptionList;
    }

    public int count() {
        return count;
    }
}
