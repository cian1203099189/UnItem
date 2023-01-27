package cn.hellp.touch.unitem.app;

import cn.hellp.touch.unitem.app.parser.ParseReader;
import cn.hellp.touch.unitem.app.parser.block.CodeBlock;
import cn.hellp.touch.unitem.plugin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillManager {
    private static int loadedSkillCount = 0;

    private static final Map<String,Skill> skillMap = new HashMap<>();

    public static Skill loadSkill(File file) {
        try (FileInputStream fs = new FileInputStream(file)) {
            String name = file.getName();
            name=name.substring(0,name.lastIndexOf("."));
            byte[] bytes = new byte[fs.available()];
            fs.read(bytes);
            String text = new String(bytes,StandardCharsets.UTF_8);
            ParseReader reader = new ParseReader(text);
            CodeBlock codeBlock = new CodeBlock();
            codeBlock.parser(reader);
            Skill skill = new Skill(codeBlock,name);
            skillMap.put(name, skill);
            ++loadedSkillCount;
            return skill;
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public static void invokeSkill(Player invoker, String name, Event event) {
        Skill skill = getSkill(name);
        if(skill!=null) {
            skill.call(invoker,event);
        } else {
            Main.getMainLogger().warning("can't call skill "+name+" on player "+invoker.getName()+"("+invoker.getUniqueId()+") , because it was not found.");
        }
    }
}
