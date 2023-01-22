package cn.hellp.touch.unitem.app.parser;

import cn.hellp.touch.unitem.app.PlaceholderManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class UEnv {
    private PlaceholderManager manager = new PlaceholderManager();
    private Player caller;
    private boolean skipped = false;
    private boolean stopped = false;
    private @Nullable Event event;

    public UEnv(PlaceholderManager manager) {
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

    public PlaceholderManager getManager() {
        return manager;
    }

    public void setManager(PlaceholderManager manager) {
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
