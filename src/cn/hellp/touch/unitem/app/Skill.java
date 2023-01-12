package cn.hellp.touch.unitem.app;

import org.bukkit.entity.Player;

public class Skill {
    private final ActuatorList actuatorList;
    private final String name;

    public Skill(ActuatorList actuatorList, String name) {
        this.actuatorList = actuatorList;
        this.name = name;
    }

    public void call(Player caller) {
        actuatorList.foreach(callableActuator -> {
            callableActuator.call(caller);
        });
    }
}
