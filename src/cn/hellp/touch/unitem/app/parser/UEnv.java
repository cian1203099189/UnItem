package cn.hellp.touch.unitem.app.parser;

import cn.hellp.touch.unitem.app.VarManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class UEnv implements Cloneable {
    private VarManager manager = new VarManager();
    private Player caller;
    private boolean skipped = false;
    private boolean stopped = false;
    private boolean continued = false;

    public boolean isContinued() {
        return continued;
    }

    public void setContinued(boolean continued) {
        this.continued = continued;
    }

    public UEnv createChildEnv() {
        try {
            UEnv env = (UEnv) this.clone();
            VarManager manager1 = new VarManager();
            manager1.setParent(manager);
            env.setManager(manager1);
            return env;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private @Nullable Event event;

    public UEnv(VarManager manager) {
        this.manager = manager;
    }

    public UEnv(Player caller) {
        this.caller = caller;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean setCancelEvent(boolean cancel) {
        if(!(event instanceof Cancellable)) {
            return false;
        }
        Cancellable cancellableEvent = ((Cancellable) event);
        cancellableEvent.setCancelled(cancel);
        return cancel;
    }

    public void setCaller(Player caller) {
        this.caller = caller;
    }

    public Player getCaller() {
        return caller;
    }

    public VarManager getManager() {
        return manager;
    }

    public void setManager(VarManager manager) {
        this.manager = manager;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
}
