package cn.hellp.touch.unitem.app.parser;

import cn.hellp.touch.unitem.app.PlaceholderManager;
import org.bukkit.entity.Player;

public class UEnv {
    private PlaceholderManager manager = new PlaceholderManager();
    private Player caller;
    private boolean skipped = false;
    private boolean stopped = false;

    public UEnv(PlaceholderManager manager) {
        this.manager = manager;
    }

    public UEnv(Player caller) {
        this.caller = caller;
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
