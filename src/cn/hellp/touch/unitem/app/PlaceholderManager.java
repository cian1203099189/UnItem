package cn.hellp.touch.unitem.app;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.Var;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class PlaceholderManager {
    private final Map<String, Var> placeholderMap = new HashMap<>();
    private PlaceholderManager parent;

    public PlaceholderManager getParent() {
        return parent;
    }

    public void setParent(PlaceholderManager parent) {
        this.parent = parent;
    }

    public void put(@Nonnull String name, @Nonnull ISelector<?> o) {
        if(placeholderMap.containsKey(name)) {
            replace(name,o);
            return;
        }
        placeholderMap.put(name,new Var(o,name));
    }

    public boolean contains(String s) {
        boolean b =  placeholderMap.containsKey(s);
        return b || ((parent!=null) && parent.contains(s));
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
            if(parent!=null && parent.contains(name)) {
                return parent.get(name);
            }
            throw new RuntimeException("can't find placeholder \""+name+"\"");
        }
        return placeholderMap.get(name).getValue();
    }

    public Var getVar(String name) {
        if(!placeholderMap.containsKey(name)) {
            if(parent!=null && parent.contains(name)) {
                return parent.getVar(name);
            }
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
        if(parent!=null && parent.contains(name)) {
            parent.replace(name,o);
        }
    }

    public void replaceVar(String name,Var o) {
        placeholderMap.replace(name,o);
        if(parent!=null && parent.contains(name)) {
            parent.replaceVar(name,o);
        }
    }
}
