package cn.hellp.touch.unitem.item;

import cn.hellp.touch.unitem.app.SkillManager;
import cn.hellp.touch.unitem.auxiliary.NbtTool;
import cn.hellp.touch.unitem.item.style.dynamic.network.ChannelInjector;
import cn.hellp.touch.unitem.plugin.Main;
import cn.hellp.touch.unitem.trigger.Trigger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnItem {
    private final Map<Trigger.Timing, List<String>> triggerMap = new HashMap<>();
    private final String name;
    private BaseBuilder itemBuilder;

    public UnItem(String name) {
        this.name = name;
    }

    private static int toProtocolIndex(int bukkitIndex) {
        if (bukkitIndex >= 9 && bukkitIndex <= 35) {
            return bukkitIndex;
        }
        if (bukkitIndex >= 36 && bukkitIndex <= 39) {
            switch (bukkitIndex) {
                case 36:
                    return 8;
                case 37:
                    return 7;
                case 38:
                    return 6;
                case 39:
                    return 5;
            }
        }
        if (bukkitIndex == 40) {
            return 45;
        }
        if (bukkitIndex >= 0 && bukkitIndex <= 8) {
            return bukkitIndex + 36;
        }
        return bukkitIndex;
    }

    public static void update() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getOpenInventory()!=null && !(onlinePlayer.getOpenInventory().getTopInventory() instanceof PlayerInventory)) {
                for (int i = 0; i < onlinePlayer.getOpenInventory().getTopInventory().getSize() ; i++) {
                    ItemStack itemStack1 = onlinePlayer.getOpenInventory().getItem(i);
                    updateIfIsUnItem(itemStack1, onlinePlayer, i);
                }
            }
            for (int i = 0; i < onlinePlayer.getInventory().getSize(); i++) {
                ItemStack itemStack1 = onlinePlayer.getInventory().getItem(i);
                updateIfIsUnItem(itemStack1, onlinePlayer, toProtocolIndex(i));
            }
        }
    }

    private static void updateIfIsUnItem(ItemStack itemStack, Player player, int slot) {
        String name;
        if (UnItemManager.contains(name = NbtTool.getString(itemStack, Main.nbtPrefix))) {
            UnItem unItem = UnItemManager.getItem(name);
            if (unItem.getItemBuilder() instanceof ItemBuilder) {
                Object container = ChannelInjector.getField(ChannelInjector.craftInventoryView, player.getOpenInventory(), "container");
                int windowId = (int) ChannelInjector.getField(ChannelInjector.container, container, ChannelInjector.versionSupport.getWindowIdFiledName());
                ItemStack itemStack1 = new ItemStack(itemStack.getType());
                itemStack1.setItemMeta(((ItemBuilder) unItem.getItemBuilder()).update(player, itemStack));
                Object packet = createUpdateSlotPacket(windowId, slot, itemStack1);
                ChannelInjector.callMethod(ChannelInjector.playerConnectionClass, ChannelInjector.versionSupport.getSendPacketMethodName(), ChannelInjector.getPlayerConnection(player), new Class[]{ChannelInjector.packetClass}, packet);
            }
        }
    }

    public static Object createUpdateSlotPacket(int windowId, int slot, ItemStack itemStack) {
        try {
            if (!NbtTool.later1_17) {
                Constructor<?> constructor = ChannelInjector.packetSetSlot.getDeclaredConstructor(int.class, int.class, NbtTool.itemStackClass);
                constructor.setAccessible(true);
                return constructor.newInstance(windowId, slot, NbtTool.asNmsCopy(itemStack));
            } else {
                Constructor<?> constructor = ChannelInjector.packetSetSlot.getDeclaredConstructor(int.class, int.class, int.class, NbtTool.itemStackClass);
                constructor.setAccessible(true);
                return constructor.newInstance(windowId, 0, slot, NbtTool.asNmsCopy(itemStack));
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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

    public void setItemBuilder(BaseBuilder itemBuilder) {
        this.itemBuilder = itemBuilder;
    }

    public void setSkill(Trigger.Timing timing, List<String> skill) {
        triggerMap.put(timing, skill);
    }

    public void call(Player caller, Trigger.Timing timing) {
        if (!triggerMap.containsKey(timing) || triggerMap.get(timing).isEmpty()) {
            return;
        }
        for (String s : triggerMap.get(timing)) {
            SkillManager.invokeSkill(caller, s);
        }
    }
}
