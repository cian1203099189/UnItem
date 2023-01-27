package cn.hellp.touch.unitem.trigger;

import cn.hellp.touch.unitem.auxiliary.NbtTool;
import cn.hellp.touch.unitem.item.UnItemManager;
import cn.hellp.touch.unitem.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class Trigger implements Listener {
    private static final Map<UUID, List<String>> wearingItems = new HashMap<>();

    static {
        if (NbtTool.server_version<=8) {
            Bukkit.getScheduler().runTaskTimer(Main.getInstance(),Trigger::tick,0,2);
        }
    }


    public static void tick() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            for(ItemStack armor : player.getInventory().getArmorContents()) {
                String name = getNbt(armor);
                if(UnItemManager.contains(name)) {
                    if(!wearingItems.containsKey(player.getUniqueId())) {
                        List<String> list = new ArrayList<>();
                        list.add(name);
                        wearingItems.put(player.getUniqueId(), list);
                        UnItemManager.getItem(name).call(player,Timing.WEARING,null);
                    } else if (!wearingItems.get(player.getUniqueId()).contains(name)) {
                        wearingItems.get(player.getUniqueId()).add(name);
                        UnItemManager.getItem(name).call(player,Timing.WEARING,null);
                    }
                }
            }
            List<String> removeList = new ArrayList<>();
            if(wearingItems.containsKey(player.getUniqueId()) && !wearingItems.get(player.getUniqueId()).isEmpty()) {
                List<ItemStack> armors = new ArrayList<>();
                Collections.addAll(armors,player.getInventory().getArmorContents());
                for (String item : wearingItems.get(player.getUniqueId())) {
                    boolean found = false;
                    int j = armors.size();
                    for(int i = 0;i< j;i++) {
                        ItemStack itemStack = armors.get(i);
                        if(itemStack!=null) {
                            if(getNbt(itemStack).equals(item)) {
                                found=true;
                                armors.remove(i);
                                --j;
                                break;
                            }
                        }
                    }
                    if(!found) {
                        UnItemManager.getItem(item).call(player,Timing.UNWEARING,null);
                        removeList.add(item);
                    }
                }
                wearingItems.get(player.getUniqueId()).removeAll(removeList);
            }
        }
    }

    public enum Timing {
        ATTACK,
        SHOT,
        DAMAGED,
        WEARING,
        UNWEARING,
        RIGHTCLICK,
        LEFTCLICK,
        SNEAK,
        UNSNEAK,
    }

    private static String holdingItem(PlayerInventory inventory) {
        return getNbt(inventory.getItemInHand());
    }

    private static String getNbt(ItemStack itemStack) {
        return NbtTool.getString(itemStack,Main.nbtPrefix);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player) {
            Player player = (Player)e.getDamager();
            if(UnItemManager.getItem(holdingItem(player.getInventory()))!=null) {
                UnItemManager.getItem(holdingItem(player.getInventory())).call(player,Timing.ATTACK,e);
            }
        }
    }

    @EventHandler
    public void onShooting(EntityShootBowEvent e) {
        if(e.getEntity() instanceof Player) {
            Player player = (Player)e.getEntity();
            if(UnItemManager.getItem(holdingItem(player.getInventory()))!=null)
                UnItemManager.getItem(holdingItem(player.getInventory())).call(player,Timing.SHOT,e);
        }
    }

    @EventHandler
    public void onDamaged(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player player = (Player)e.getEntity();
            if(UnItemManager.getItem(holdingItem(player.getInventory()))!=null) {
                UnItemManager.getItem(holdingItem(player.getInventory())).call(player,Timing.ATTACK,e);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.hasItem()) {
            if(UnItemManager.contains(getNbt(e.getItem()))) {
                if(e.getAction()== Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                    UnItemManager.getItem(getNbt(e.getItem())).call(e.getPlayer(),Timing.LEFTCLICK,e);
                } else {
                    UnItemManager.getItem(getNbt(e.getItem())).call(e.getPlayer(),Timing.RIGHTCLICK,e);
                }
            }
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        String name = holdingItem(e.getPlayer().getInventory());
        if(UnItemManager.contains(name)) {
            if(e.isSneaking()) {
                UnItemManager.getItem(name).call(e.getPlayer(),Timing.SNEAK,e);
            } else {
                UnItemManager.getItem(name).call(e.getPlayer(),Timing.UNSNEAK,e);
            }
        }
    }
}
