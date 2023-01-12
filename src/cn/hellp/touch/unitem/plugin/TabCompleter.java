package cn.hellp.touch.unitem.plugin;

import cn.hellp.touch.unitem.app.SkillManager;
import cn.hellp.touch.unitem.item.UnItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    private static final Map<String,SubCompleter> subCompleterMap = new HashMap<>();

    @SafeVarargs
    public static <T> List<T> listOf(T... eme) {
        List<T> result = new ArrayList<>();
        Collections.addAll(result,eme);
        return result;
    }

    public static class SubCompleter {
        Map<Integer, BiFunction<CommandSender,String[],List<String>>> result;
        public SubCompleter(Map<Integer, BiFunction<CommandSender,String[],List<String>>> result) {
            this.result=result;
        }
        List<String> complete(CommandSender sender,String[] args) {
            return result.get(args.length).apply(sender,args);
        }
    }

    public TabCompleter() {
        Map<Integer, BiFunction<CommandSender,String[],List<String>>> skills = new HashMap<>();
        skills.put(1,((sender, strings) -> listOf("reload","invoke","list")));
        skills.put(2,(sender, strings) -> {
            if (strings[0].equalsIgnoreCase("reload") || strings[0].equalsIgnoreCase("list")) {
                return listOf();
            } else if (strings[0].equalsIgnoreCase("invoke")) {
                return SkillManager.skillsName();
            }
            return listOf();
        });
        skills.put(3,(sender, strings) -> {
            if(strings[0].equalsIgnoreCase("invoke")) {
                return null;
            }
            return listOf();
        });

        Map<Integer, BiFunction<CommandSender,String[],List<String>>> items = new HashMap<>();
        items.put(1,(sender, strings) -> listOf("reload","send","list"));
        items.put(2,(sender, strings) -> {
            if(strings[0].equalsIgnoreCase("send"))
                return UnItemManager.itemNames();
            return listOf();
        });
        items.put(3,(sender, strings) -> {
            if(strings[0].equalsIgnoreCase("send")) {
                return null;
            }
            return listOf();
        });
        subCompleterMap.put("skills", new SubCompleter(skills));
        subCompleterMap.put("items", new SubCompleter(items));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if(args.length>=2) {
            for (String key : subCompleterMap.keySet()) {
                if(key.equalsIgnoreCase(args[0])) {
                    return subCompleterMap.get(key).complete(commandSender, Arrays.copyOfRange(args,1,args.length));
                }
            }
        } else {
            return new ArrayList<>(subCompleterMap.keySet());
        }
        return listOf();
    }
}
