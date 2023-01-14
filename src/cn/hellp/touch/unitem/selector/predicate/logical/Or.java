package cn.hellp.touch.unitem.selector.predicate.logical;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

public class Or implements ISelector<Boolean> {
    private final ISelector<?> left;
    private final ISelector<?> right;

    public Or(ISelector<?> left, ISelector<?> right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public Boolean[] select(Player invoker) {
        Object[] left = this.left.select(invoker);
        Object[] right = this.right.select(invoker);
        final int length = Math.min(left.length, right.length);
        Boolean[] result = new Boolean[length];
        for (int i = 0;i<length;i++) {
            result[i]=(((Boolean) left[i]))||(((Boolean) right[i]));
        }
        return result;
    }

    @Override
    public String selectorID() {
        return "or";
    }
}
