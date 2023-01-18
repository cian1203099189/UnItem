package cn.hellp.touch.unitem.app;

import cn.hellp.touch.unitem.app.parser.CodeNode;
import cn.hellp.touch.unitem.app.parser.UEnv;
import org.bukkit.entity.Player;

public class Skill {
    private final CodeNode runnable;
    private final String name;

    public Skill(CodeNode c, String name) {
        this.runnable = c;
        this.name = name;
    }

    public void call(Player caller) {
        UEnv env = new UEnv(caller);
        runnable.eval(env);
    }
}
