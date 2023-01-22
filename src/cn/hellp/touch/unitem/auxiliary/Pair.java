package cn.hellp.touch.unitem.auxiliary;

public class Pair<K,V> {
    private K first;
    private V second;

    public K getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    public Pair(K first,V second) {
        this.first=first;
        this.second=second;
    }

    @Override
    public int hashCode() {
        int result = first!=null ? first.hashCode() : 0;
        result = 31*result + (second!=null ? second.hashCode() : 0);
        return result;
    }
}
