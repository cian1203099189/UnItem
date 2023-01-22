package cn.hellp.touch.unitem.plugin;

import cn.hellp.touch.unitem.app.SkillManager;
import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.item.UnItem;
import cn.hellp.touch.unitem.item.UnItemFactory;
import cn.hellp.touch.unitem.item.UnItemManager;
import cn.hellp.touch.unitem.item.style.StyleManager;
import cn.hellp.touch.unitem.item.style.dynamic.PlayerNetworkListener;
import cn.hellp.touch.unitem.listener.PlayerDamageListener;
import cn.hellp.touch.unitem.support.ItemsAdderSupport;
import cn.hellp.touch.unitem.support.MoneySupport;
import cn.hellp.touch.unitem.support.PlaceholderAPISupport;
import cn.hellp.touch.unitem.trigger.Trigger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    private static Main INSTANCE;
    public static String nbtPrefix = "UnItem";
    private static FileConfiguration config;

    public static class Setting {
        protected String value;

        public Setting(String value) {
            this.value = value;
        }

        public boolean asBoolean() {
            return Boolean.parseBoolean(value);
        }

        public int asInt() {
            return Integer.parseInt(value);
        }

        public double asDouble() {
            return Double.parseDouble(value);
        }

        public float asFloat() {
            return Float.parseFloat(value);
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static Setting getSetting(String key) {
        return new Setting(config.getString(key));
    }

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

    private static final Set<Integer> tasks = new HashSet<>();

    public static ERROR loadItems() {
        UnItemManager.clear();
        tasks.forEach(task -> Bukkit.getScheduler().cancelTask(task));
        tasks.clear();
        for (File file : Objects.requireNonNull(Main.getResourceFolder("items").listFiles(pathname -> pathname.getName().endsWith("ui")))) {
            try {
                UnItemManager.addItem(UnItemFactory.loadFile(file));
                tasks.add(Bukkit.getScheduler().runTaskTimer(INSTANCE, UnItem::update, 0, getSetting("update-time").asInt()).getTaskId());
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

    public static ERROR loadStyles() {
        StyleManager.clear();
        try {
            StyleManager.loadAllStyles();
        } catch (Exception e) {
            return new ERROR(e);
        }
        PlayerNetworkListener.init();
        return null;
    }

    private static void initSupports() {
        if(!MoneySupport.INSTANCE.init()) {
            Bukkit.getConsoleSender().sendMessage("§aUnItem >Vault soft-dependency not found. Will not use the money about.");
        }
        if(!ItemsAdderSupport.INSTANCE.init()) {
            Bukkit.getConsoleSender().sendMessage("§aUnItem >ItemsAdder soft-dependency not found. Will not use the itemsAdder about.");
        }
        if(!PlaceholderAPISupport.INSTANCE.init()) {
            Bukkit.getConsoleSender().sendMessage("§aUnItem >PlaceholderAPI soft-dependency not found. Will not use the placeholderAPI about.");
        }
    }

    private static void initListeners() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new PlayerDamageListener(),INSTANCE);
        manager.registerEvents(new Trigger(),INSTANCE);
    }

    private static void initConfig() {
        INSTANCE.saveDefaultConfig();
        config = INSTANCE.getConfig();
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
        sender.sendMessage("§a   作者：zZZZ(也称不是辞安或Touch)       版本1.0         ");
        sender.sendMessage("§a----------------------------------------------------  ");
        initConfig();
        initCommands();
        loadSkills();
        initListeners();
        loadStyles();
        loadItems();
        initSupports();
    }
}
