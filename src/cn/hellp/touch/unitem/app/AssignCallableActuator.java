package cn.hellp.touch.unitem.app;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.PlaceholderSelector;
import cn.hellp.touch.unitem.selector.ValueSelector;
import cn.hellp.touch.unitem.selector.Var;
import cn.hellp.touch.unitem.selector.tools.ArraySelector;
import org.bukkit.entity.Player;

public class AssignCallableActuator extends CallableActuator{
    private Var var;
    private ISelector<?> value;
    private PlaceholderManager manager;

    public AssignCallableActuator(Var var, ISelector<?> value ,PlaceholderManager manager) {
        super(null);
        this.var = var;
        this.manager=manager;
        this.value = value;
    }

    public Var getVar() {
        return var;
    }

    public void setVar(Var var) {
        this.var = var;
    }

    public ISelector<?> getValue() {
        return value;
    }

    public void setValue(ISelector<?> value) {
        this.value = value;
    }

    @Override
    public void call(Player caller) {
        Object[] result = value.select(caller);
        manager.put(var.getName(),new ArraySelector((Object[]) result));
    }
}
