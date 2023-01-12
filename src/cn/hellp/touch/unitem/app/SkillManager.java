package cn.hellp.touch.unitem.app;

import cn.hellp.touch.unitem.app.sentence.SentenceFactory;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.plugin.Main;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SkillManager {
    private static int loadedSkillCount = 0;

    private static final Map<String,Skill> skillMap = new HashMap<>();

    public static Skill loadSkill(File file) {
        try {
            FileInputStream is =new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            PlaceholderManager manager = new PlaceholderManager();
            ActuatorList actuatorList = new ActuatorList();
            SentenceFactory factory = new SentenceFactory(manager,actuatorList);
            while ((line= reader.readLine())!=null) {
                factory.create(line);
            }
            String name = file.getName().substring(0,file.getName().lastIndexOf("."));
            Skill skill = new Skill(actuatorList, name);
            skillMap.put(name,skill);
            ++loadedSkillCount;
            return skill;
        } catch (IOException e) {
            throw new ERROR(e);
        }
    }

    public static List<String> skillsName() {
        return  new ArrayList<>(skillMap.keySet());
    }

    public static String asString() {
        StringBuilder builder = new StringBuilder();
        for (String name : skillMap.keySet()) {
            builder.append(name);
            builder.append("\n");
        }
        return builder.toString();
    }

    public static void clear() {
        skillMap.clear();
    }

    public static Skill getSkill(String name) {
        return skillMap.get(name);
    }

    public static boolean contains(String name) {
        return skillMap.containsKey(name);
    }

    public static void invokeSkill(Player invoker,String name) {
        Skill skill = getSkill(name);
        if(skill!=null) {
            skill.call(invoker);
        } else {
            Main.getMainLogger().warning("can't call skill "+name+" on player "+invoker.getName()+"("+invoker.getUniqueId()+") , because it was not found.");
        }
    }
}
