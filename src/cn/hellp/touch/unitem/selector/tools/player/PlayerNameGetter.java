package cn.hellp.touch.unitem.selector.tools.player;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

public class PlayerNameGetter extends PlayerGetter {

    public PlayerNameGetter(ISelector<?> target) {
        super(target);
    }

    public PlayerNameGetter() {
        super(null);
    }

    @Override
    public String selectorID() {
        return "getPlayerName";
    }

    @Override
    protected Object get(Player invoker) {
        return invoker.getName();
    }
}
