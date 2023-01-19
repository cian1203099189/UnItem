package cn.hellp.touch.unitem.item;

import cn.hellp.touch.unitem.app.SkillManager;
import cn.hellp.touch.unitem.trigger.Trigger;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnItem {
    private BaseBuilder itemBuilder;
    private final Map<Trigger.Timing, List<String>> triggerMap = new HashMap<>();
    private final String name;

    public UnItem(String name) {
        this.name = name;
    }

    public List<String> getSkills(Trigger.Timing timing) {
        return triggerMap.get(timing);
    }

    public String getName() {
        return name;
    }

    public BaseBuilder getItemBuilder() {
        return itemBuilder;
    }

    public void setSkill(Trigger.Timing timing, List<String> skill) {
        triggerMap.put(timing,skill);
    }

    public void setItemBuilder(BaseBuilder itemBuilder) {
        this.itemBuilder = itemBuilder;
    }

    public void call(Player caller, Trigger.Timing timing) {
        if(!triggerMap.containsKey(timing) || triggerMap.get(timing).isEmpty()) {
            return;
        }
        for (String s : triggerMap.get(timing)) {
            SkillManager.invokeSkill(caller,s);
        }
    }
}
