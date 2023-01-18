package cn.hellp.touch.unitem.selector.tools.player;

import cn.hellp.touch.unitem.auxiliary.Setter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public abstract class PlayerSetter extends Setter<Player> {

    @Nullable
    @Override
    public Object actuate(Object... target) {
        Player invoker = ((Player) target[0]);
        return set(invoker, Arrays.copyOfRange(target,1,target.length));
    }

    protected abstract Object set(Player invoker,Object... parameter);
}
