package cn.hellp.touch.unitem.app;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.Var;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class PlaceholderManager {
    private final Map<String, Var> placeholderMap = new HashMap<>();

    public void put(@Nonnull String name,@Nonnull ISelector<?> o) {
        if(placeholderMap.containsKey(name)) {
            replace(name,o);
            return;
        }
        placeholderMap.put(name,new Var(o));
    }

    public boolean contains(String s) {
        return placeholderMap.containsKey(s);
    }

    public void putVar(@Nonnull String name,@Nonnull Var o) {
        if(placeholderMap.containsKey(name)) {
            replaceVar(name,o);
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

    public Var getVar(String name) {
        if(!placeholderMap.containsKey(name)) {
            throw new RuntimeException("can't find placeholder \""+name+"\"");
        }
        return placeholderMap.get(name);
    }


    @Override
    public PlaceholderManager clone() {
        PlaceholderManager placeholderManager = new PlaceholderManager();
        for (String key : placeholderMap.keySet()) {
            placeholderManager.putVar(new String(key.toCharArray()), getVar(key));
        }
        return placeholderManager;
    }

    public void replace(String name,ISelector<?> o) {
        placeholderMap.get(name).setValue(o);
    }

    public void replaceVar(String name,Var o) {
        placeholderMap.replace(name,o);
    }

    public ISelector<?> delete(String name) {
        if(placeholderMap.containsKey(name)) {
            return placeholderMap.remove(name);
        }
        return null;
    }
}
