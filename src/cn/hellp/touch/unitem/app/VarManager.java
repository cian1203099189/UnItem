package cn.hellp.touch.unitem.app;

import cn.hellp.touch.unitem.selector.ISelector;
import cn.hellp.touch.unitem.selector.Var;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class VarManager {
    private final Map<String, Var> placeholderMap = new HashMap<>();
    private VarManager parent;

    public VarManager getParent() {
        return parent;
    }

    public void setParent(VarManager parent) {
        this.parent = parent;
    }

    public void put(@Nonnull String name, @Nonnull ISelector<?> o) {
        if(placeholderMap.containsKey(name) || (parent!=null && parent.contains(name))) {
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
        if(placeholderMap.containsKey(name)|| (parent!=null && parent.contains(name))) {
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
    public VarManager clone() {
        VarManager varManager = new VarManager();
        for (String key : placeholderMap.keySet()) {
            varManager.putVar(new String(key.toCharArray()), getVar(key));
        }
        return varManager;
    }

    public void replace(String name,ISelector<?> o) {
        if(placeholderMap.containsKey(name))
            placeholderMap.get(name).setValue(o);
        if(parent!=null && parent.contains(name)) {
            parent.replace(name,o);
        }
    }

    public void replaceVar(String name,Var o) {
        if (placeholderMap.containsKey(name))
            placeholderMap.replace(name,o);
        if(parent!=null && parent.contains(name)) {
            parent.replaceVar(name,o);
        }
    }
}
