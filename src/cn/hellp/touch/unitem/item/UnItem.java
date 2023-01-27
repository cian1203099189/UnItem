package cn.hellp.touch.unitem.item;

import cn.hellp.touch.unitem.app.SkillManager;
import cn.hellp.touch.unitem.auxiliary.NbtTool;
import cn.hellp.touch.unitem.item.style.dynamic.network.ChannelInjector;
import cn.hellp.touch.unitem.plugin.Main;
import cn.hellp.touch.unitem.trigger.Trigger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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

    private static final Set<String> updatedUnItemSet = new HashSet<>();

    public static void update() {
        updatedUnItemSet.clear();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            int offset = 0;
            if (onlinePlayer.getOpenInventory()!=null) {
                Inventory top = onlinePlayer.getOpenInventory().getTopInventory();
                offset = top.getSize() - 1;
                for (int i = 0; i < top.getSize(); i++) {
                    ItemStack itemStack = top.getItem(i);
                    updateIfIsUnItem(itemStack,onlinePlayer,i);
                }
            }
            Inventory playerInventory = onlinePlayer.getInventory();
            for (int i = offset; i < playerInventory.getSize() + offset; i++) {
                ItemStack itemStack1 = onlinePlayer.getInventory().getItem(onlinePlayer.getOpenInventory().convertSlot(i));
                updateIfIsUnItem(itemStack1, onlinePlayer, i);
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
                if (!updatedUnItemSet.contains(name)) {
                    updatedUnItemSet.add(name);
                    itemStack1.setItemMeta(((ItemBuilder) unItem.getItemBuilder()).update(player, itemStack));
                } else {
                    itemStack1.setItemMeta(((ItemBuilder) unItem.getItemBuilder()).now(player, itemStack));
                }
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

    public void call(Player caller, Trigger.Timing timing,@Nullable Event event) {
        if (!triggerMap.containsKey(timing) || triggerMap.get(timing).isEmpty()) {
            return;
        }
        for (String s : triggerMap.get(timing)) {
            SkillManager.invokeSkill(caller, s , event);
        }
    }
}
