package cn.hellp.touch.unitem.selector.tools.player;

import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.auxiliary.Number;
import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.support.MoneySupport;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerMoneyGetterSetter extends PlayerGetterSetter{
    @Override
    protected Object get(Player invoker) {
        if(MoneySupport.INSTANCE.isLoaded()) {
            throw new ERROR("You didn't loaded the [Vault] plugin , can't use the getPlayerMoney()");
        }
        return MoneySupport.INSTANCE.getMoney(invoker);
    }

    public PlayerMoneyGetterSetter(ISelector<?> target) {
        super(target);
    }

    public PlayerMoneyGetterSetter() {
        super(null);
    }

    @Override
    protected Object set(Player invoker, Object... value) {
        if(MoneySupport.INSTANCE.isLoaded()) {
            throw new ERROR("You didn't loaded the [Vault] plugin , can't use the #setPlayerMoney()");
        }
        return MoneySupport.INSTANCE.takeMoney(invoker, ((Number) value[0]).toDouble());
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "takePlayerMoney";
    }

    @Override
    public String selectorID() {
        return "getPlayerMoney";
    }
}
