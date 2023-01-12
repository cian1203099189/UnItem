package cn.hellp.touch.unitem.app;

public abstract class Sentence {
    protected PlaceholderManager manager;
    protected ActuatorList actuatorList;

    public Sentence(PlaceholderManager manager,ActuatorList actuatorList) {
        this.manager=manager;
        this.actuatorList=actuatorList;
    }

    public abstract void init(Object... args);

    enum OPERATOR {
        ADD("+"), SUB("-"), MUL("*"), DIVIDED("/"), ASSIGNMENT("=");
        public final String str;

        OPERATOR(String s) {
            str = s;
        }
    }
}
