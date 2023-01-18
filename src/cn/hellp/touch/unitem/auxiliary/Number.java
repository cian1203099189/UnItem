package cn.hellp.touch.unitem.auxiliary;

import java.util.Objects;

public class Number {
    private final Object value;

    public Number(Object value) {
        if(!(value instanceof java.lang.Number) && !(value instanceof String)) {
            throw new ERROR("can't use "+value+" as a number!");
        }
        this.value = value;
        if(value instanceof String) {
            try {
                toDouble();
                return;
            } catch (Exception ignored) {}
            try {
                toInteger();
                return;
            } catch (Exception ignored) {}
            try {
                toFloat();
                return;
            } catch (Exception ignored) {}
            try {
                toLong();
                return;
            } catch (Exception ignored) {}
            throw new ERROR("can't use "+value+" as a number!");
        }
    }

    public Number add(Number n) {
        return new Number(toDouble()+n.toDouble());
    }

    public Number sub(Number n) {
        return new Number(toDouble()-n.toDouble());
    }

    public Number mul(Number n) {
        return new Number(toDouble()*n.toDouble());
    }

    public Number divide(Number n) {
        return new Number(toDouble()/n.toDouble());
    }

    public Number remainder(Number n) {
        return new Number(toDouble()%n.toDouble());
    }

    public Double toDouble() {
        if(value instanceof Integer) {
            return Double.valueOf((Integer)value);
        }
        if(value instanceof Double) {
            return ((Double) value);
        }
        if(value instanceof Float) {
            return Double.valueOf(((Float) value));
        }
        if(value instanceof String) {
            return Double.valueOf(((String) value));
        }
        if(value instanceof Long) {
            return ((Long) value).doubleValue();
        }
        throw new ERROR("can't phrase "+value+" to double");
    }

    public Integer toInteger() {
        if(value instanceof Integer) {
            return (Integer)value;
        }
        if(value instanceof Double) {
            return ((Double) value).intValue();
        }
        if(value instanceof Float) {
            return ((Float) value).intValue();
        }
        if(value instanceof String) {
            return Integer.valueOf(((String) value));
        }
        if(value instanceof Long) {
            return ((Long) value).intValue();
        }
        throw new ERROR("can't phrase "+value+" to int");
    }

    public Float toFloat() {
        if(value instanceof Integer) {
            return ((Integer)value).floatValue();
        }
        if(value instanceof Double) {
            return ((Double) value).floatValue();
        }
        if(value instanceof Float) {
            return ((Float) value);
        }
        if(value instanceof String) {
            return Float.valueOf(((String) value));
        }
        if(value instanceof Long) {
            return ((Long) value).floatValue();
        }
        throw new ERROR("can't phrase "+value+" to float");
    }

    public Long toLong() {
        if(value instanceof Integer) {
            return ((Integer)value).longValue();
        }
        if(value instanceof Double) {
            return ((Double) value).longValue();
        }
        if(value instanceof Float) {
            return ((Float) value).longValue();
        }
        if(value instanceof String) {
            return Long.valueOf(((String) value));
        }
        if(value instanceof Long) {
            return ((Long) value);
        }
        throw new ERROR("can't phrase "+value+" to float");
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(getClass()!=obj.getClass()) {
            return false;
        }
        Number obj1 = (Number) obj;
        return Objects.equals(obj1.value,this.value);
    }
}
