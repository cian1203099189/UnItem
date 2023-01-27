package cn.hellp.touch.unitem.plugin;

import cn.hellp.touch.unitem.app.SkillManager;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.item.UnItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class CommandHandler implements CommandExecutor {
    private static final Map<String, SubCommand> subcommands = new HashMap<>();

    public CommandHandler() {
        subcommands.put("skills", new SubCommand(1, (CommandSender sender, String[] args) -> {
            switch (args[0]) {
                case "invoke" : {
                    if (args.length < 2) {
                        sendCommandHelp(sender);
                        return true;
                    }
                    if (args.length == 2 && (sender instanceof Player)) {
                        Player player = (((Player) sender));
                        if (!SkillManager.contains(args[1])) {
                            player.sendMessage("can't find skill " + args[1]);
                            return true;
                        }
                        try {
                            SkillManager.invokeSkill(player, args[1], null);
                        } catch (Exception e) {
                            sender.sendMessage("Couldn't invoke the skill "+args[1]+".");
                            sender.sendMessage("Please check the console and the config(s).");
                            e.printStackTrace();
                        }
                        return true;
                    } else if (args.length >= 3) {
                        Player player = Bukkit.getPlayer(args[2]);
                        if (player == null || !player.isOnline()) {
                            sender.sendMessage("can't find player " + args[2]);
                            return true;
                        }
                        if (!SkillManager.contains(args[1])) {
                            player.sendMessage("can't find skill " + args[1]);
                            return true;
                        }
                        try {
                            SkillManager.invokeSkill(player, args[1], null);
                        } catch (Exception e) {
                            sender.sendMessage("Couldn't invoke the skill "+args[1]+".");
                            sender.sendMessage("Please check the console and the config(s).");
                            e.printStackTrace();
                        }
                        return true;
                    }
                    break;
                }
                case "reload" : {
                    ERROR error = Main.loadSkills();
                    if(error == null) {
                        sender.sendMessage(ChatColor.AQUA + "Reloaded skills.");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Reloaded skills with " +ChatColor.DARK_RED + error.count()+ChatColor.RED+" error(s).");
                        sender.sendMessage(ChatColor.RED+"Please check the console.");
                    }
                    return true;
                }
                case "list" : {
                    sender.sendMessage(SkillManager.asString());
                    return true;
                }
            }
            sendCommandHelp(sender);
            return true;
        }));
        subcommands.put("items", new SubCommand(1, (CommandSender sender, String[] args) -> {
            switch (args[0]) {
                case "reload" : {
                    ERROR error1 = Main.loadStyles();
                    ERROR error =  Main.loadItems();
                    if(error == null && error1==null) {
                        sender.sendMessage(ChatColor.AQUA + "Reloaded items.");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Reloaded items with " +ChatColor.DARK_RED +(error!=null&&error1!=null ? (error.count()+error1.count()) : (error!=null) ? error.count() : error1.count())+ChatColor.RED+" error(s).");
                        sender.sendMessage(ChatColor.RED+"Please check the console.");
                    }
                    return true;
                }
                case "send" : {
                    if (args.length == 2 && (sender instanceof Player)) {
                        Player player = ((Player) sender);
                        if (!UnItemManager.contains(args[1])) {
                            player.sendMessage("can't find skill " + args[1]);
                            return true;
                        }
                        try {
                            sendItem(player, UnItemManager.getItem(args[1]).getItemBuilder().toBukkitItemStack());
                        }catch (Exception e) {
                            sender.sendMessage(ChatColor.RED+"Couldn't create the item(s) from unitem config "+args[1]+".");
                            sender.sendMessage(ChatColor.RED+"Please check the console and config(s).");
                            e.printStackTrace();
                        }
                        return true;
                    } else if (args.length >= 3) {
                        Player player = Bukkit.getPlayer(args[2]);
                        if (player == null || !player.isOnline()) {
                            sender.sendMessage("can't find player " + args[2]);
                            return true;
                        }
                        if (!UnItemManager.contains(args[1])) {
                            player.sendMessage("can't find skill " + args[1]);
                            return true;
                        }
                        try {
                            sendItem(player, UnItemManager.getItem(args[1]).getItemBuilder().toBukkitItemStack());
                        }catch (Exception e) {
                            sender.sendMessage(ChatColor.RED+"Couldn't create the item(s) from unitem config "+args[1]+".");
                            sender.sendMessage(ChatColor.RED+"Please check the console and config(s).");
                            e.printStackTrace();
                        }
                        return true;
                    }
                }
                case "list" : {
                    sender.sendMessage(UnItemManager.asString());
                    return true;
                }
            }
            sendCommandHelp(sender);
            return true;
        }));
    }

    private static void sendItem(Player player, ItemStack itemStack) {
        if(!player.getInventory().addItem(itemStack).isEmpty()) {
            player.getWorld().dropItem(player.getLocation(),itemStack);
        }
    }

    private static void sendCommandHelp(CommandSender sender) {
        sender.sendMessage("§a|    __  __     ______          ");
        sender.sendMessage("§a|   / / / /__  /  _/ /____ __ _ ");
        sender.sendMessage("§a|  / /_/ / _ \\_/ // __/ -_)  ' \\");
        sender.sendMessage("§a|  \\____/_//_/___/\\__/\\__/_/_/_/");
        sender.sendMessage("§a|                               ");
        sender.sendMessage("§a|  插件帮助                       ");
        sender.sendMessage("§a|  /unItem skills reload                       重载技能");
        sender.sendMessage("§a|  /unItem skills invoke <技能名称> [调用玩家名]  释放技能");
        sender.sendMessage("§a|  /unItem skills list                         列出技能");
        sender.sendMessage("§a|  /unItem items  reload                       重载物品");
        sender.sendMessage("§a|  /unItem items  send <物品名称> [给予玩家名]    获取物品");
        sender.sendMessage("§a|  /unItem items  list                         列出物品");
    }

    @Override
    public boolean onCommand(@org.jetbrains.annotations.NotNull CommandSender commandSender, @org.jetbrains.annotations.NotNull Command command, @org.jetbrains.annotations.NotNull String string, @org.jetbrains.annotations.NotNull String[] args) {
        if (args.length < 1) {
            sendCommandHelp(commandSender);
            return true;
        }
        for (String key : subcommands.keySet()) {
            if (key.equalsIgnoreCase(args[0])) {
                return subcommands.get(key).onCommand(commandSender, command, string, Arrays.copyOfRange(args, 1, args.length));
            }
        }
        sendCommandHelp(commandSender);
        return true;
    }

    public static class SubCommand implements CommandExecutor {

        int minArgsLength;
        BiFunction<CommandSender, String[], Boolean> handle;
        public SubCommand(int minArgsLength,
                          BiFunction<CommandSender, String[], Boolean> handle) {
            this.minArgsLength=minArgsLength;
            this.handle=handle;
        }
        @Override
        public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] strings) {
            if (strings.length < minArgsLength) {
                sendCommandHelp(commandSender);
                return true;
            }
            return handle.apply(commandSender, strings);
        }
    }
}
