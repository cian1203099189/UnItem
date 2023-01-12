package cn.hellp.touch.unitem.selector.tools.player;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

public class PlayerNameSelector implements ISelector<String> {
    @Override
    public String[] select(Player invoker) {
        return new String[]{invoker.getName()};
    }

    @Override
    public String selectorID() {
        return "playerName";
    }
}
