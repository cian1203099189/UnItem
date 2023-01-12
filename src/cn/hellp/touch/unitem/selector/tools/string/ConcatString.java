package cn.hellp.touch.unitem.selector.tools.string;

import cn.hellp.touch.unitem.selector.ISelector;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ConcatString implements ISelector<String> {
    private final ISelector<?> one;
    private final ISelector<?> two;

    public ConcatString(ISelector<?> o,ISelector<?> t) {
        this.one=o;
        this.two=t;
    }

    @Override
    public String[] select(Player invoker) {
        List<String> result = new ArrayList<>();
        for (Object one : one.select(invoker)) {
            for (Object two : two.select(invoker)) {
                result.add(((String) one).concat(((String) two)));
            }
        }
        return result.toArray(new String[0]);
    }

    @Override
    public String selectorID() {
        return "concatString";
    }
}
