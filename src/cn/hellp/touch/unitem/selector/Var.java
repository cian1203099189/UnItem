package cn.hellp.touch.unitem.selector;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Var {
    private final String name;


    private @Nullable ISelector value;

    public Var(@Nullable ISelector value , @Nonnull String name) {
        this.value = value;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setValue(@Nullable ISelector value) {
        this.value = value;
    }

    public @Nullable ISelector getValue() {
        return value;
    }
}
