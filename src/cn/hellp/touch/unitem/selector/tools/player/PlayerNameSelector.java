package cn.hellp.touch.unitem.selector.tools.player;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PlayerNameSelector implements ISelector<String> {
    private final @Nullable ISelector<?> players;

    public PlayerNameSelector(@Nullable ISelector<?> players) {
        this.players = players;
    }

    public PlayerNameSelector() {
        this(null);
    }

    @Override
    public String[] select(Player invoker) {
        if(players==null) {
            return new String[]{invoker.getName()};
        } else {
            List<String> result = new ArrayList<>();
            for (Object player : players.select(invoker)) {
                result.add(((HumanEntity) player).getName());
            }
            return result.toArray(new String[0]);
        }
    }

    @Override
    public String selectorID() {
        return "playerName";
    }
}
