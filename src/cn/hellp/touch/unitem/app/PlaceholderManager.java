package cn.hellp.touch.unitem.app;

import cn.hellp.touch.unitem.selector.ISelector;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class PlaceholderManager {
    private final Map<String, ISelector<?>> placeholderMap = new HashMap<>();

    public void put(@Nonnull String name,@Nonnull ISelector<?> o) {
        if(placeholderMap.containsKey(name)) {
            replace(name,o);
            return;
        }
        placeholderMap.put(name,o);
    }

    public ISelector<?> get(String name) {
        if(!placeholderMap.containsKey(name)) {
            throw new RuntimeException("can't find placeholder \""+name+"\"");
        }
        return placeholderMap.get(name);
    }

    public void replace(String name,ISelector<?> o) {
        placeholderMap.replace(name,o);
    }

    public ISelector<?> delete(String name) {
        if(placeholderMap.containsKey(name)) {
            return placeholderMap.remove(name);
        }
        return null;
    }
}
