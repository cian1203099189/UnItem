package cn.hellp.touch.unitem.app;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.Var;
import org.bukkit.entity.Player;

public class AssignCallableActuator extends CallableActuator{
    private Var var;
    private ISelector<?> value;

    public AssignCallableActuator(Var var, ISelector<?> value) {
        super(null);
        this.var = var;
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
    public Object[] call(Player caller) {
        var.setValue(value);
        return value.select(caller);
    }
}
