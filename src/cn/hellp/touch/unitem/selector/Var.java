package cn.hellp.touch.unitem.selector;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class Var implements ISelector{
    private @Nullable ISelector value;

    public Var(@Nullable ISelector value) {
        this.value = value;
    }

    public void setValue(@Nullable ISelector value) {
        this.value = value;
    }

    public @Nullable ISelector getValue() {
        return value;
    }

    @Override
    public Object[] select(Player invoker) {
        return value.select(invoker);
    }

    @Override
    public String selectorID() {
        return value.selectorID();
    }
}
