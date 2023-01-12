package cn.hellp.touch.unitem.plugin;

import cn.hellp.touch.unitem.app.SkillManager;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.item.UnItemFactory;
import cn.hellp.touch.unitem.item.UnItemManager;
import cn.hellp.touch.unitem.listener.PlayerDamageListener;
import cn.hellp.touch.unitem.trigger.Trigger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    private static Main INSTANCE;
    public static String nbtPrefix = "UnItem";

    @Override
    public void onLoad() {
        INSTANCE=this;
    }

    public static Main getInstance() {
        return INSTANCE;
    }

    public static Logger getMainLogger() {
        return INSTANCE.getLogger();
    }

    private static void initCommands() {
        try {
            Constructor<PluginCommand> commandConstructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            commandConstructor.setAccessible(true);
            String cmdName = "unItem";
            PluginCommand command = commandConstructor.newInstance(cmdName,INSTANCE);
            command.setPermission("unItem.admin");
            command.setExecutor(new CommandHandler());
            command.setTabCompleter(new TabCompleter());
            command.setAliases(Collections.emptyList());
            command.setName(cmdName);

            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);
            ((CommandMap) field.get(Bukkit.getPluginManager())).register(cmdName,command);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static @Nonnull File getResourceFolder(String name) {
        File file = new File(getInstance().getDataFolder(),name);
        if(!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static ERROR loadSkills() {
        SkillManager.clear();
        for (File file : Objects.requireNonNull(Main.getResourceFolder("skills").listFiles(pathname -> pathname.getName().endsWith(".ui")))) {
            try {
                SkillManager.loadSkill(file);
            }catch (ERROR e) {
                getMainLogger().warning("can't load skill "+file.getName());
                e.printStackTrace();
                return e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static ERROR loadItems() {
        UnItemManager.clear();
        for (File file : Objects.requireNonNull(Main.getResourceFolder("items").listFiles(pathname -> pathname.getName().endsWith("ui")))) {
            try {
                UnItemManager.addItem(UnItemFactory.loadFile(file));
            }catch (ERROR error) {
                getMainLogger().warning("can't load item "+file.getName());
                error.printStackTrace();
                return error;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private static void initListeners() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new PlayerDamageListener(),INSTANCE);
        manager.registerEvents(new Trigger(),INSTANCE);
    }


    @Override
    public void onEnable() {
        CommandSender sender = Bukkit.getConsoleSender();
        sender.sendMessage("§a----------------------------------------------------");
        sender.sendMessage("§a ██╗   ██╗███╗   ██╗██╗████████╗███████╗███╗   ███╗ ");
        sender.sendMessage("§a ██║   ██║████╗  ██║██║╚══██╔══╝██╔════╝████╗ ████║ ");
        sender.sendMessage("§a ██║   ██║██╔██╗ ██║██║   ██║   █████╗  ██╔████╔██║ ");
        sender.sendMessage("§a ██║   ██║██║╚██╗██║██║   ██║   ██╔══╝  ██║╚██╔╝██║ ");
        sender.sendMessage("§a ╚██████╔╝██║ ╚████║██║   ██║   ███████╗██║ ╚═╝ ██║ ");
        sender.sendMessage("§a  ╚═════╝ ╚═╝  ╚═══╝╚═╝   ╚═╝   ╚══════╝╚═╝     ╚═╝ ");
        sender.sendMessage("                                                    ");
        sender.sendMessage("§a      作者：zZZZ                       版本1.0        ");
        sender.sendMessage("§a----------------------------------------------------  ");
        initCommands();
        loadSkills();
        initListeners();
        loadItems();
    }
}
