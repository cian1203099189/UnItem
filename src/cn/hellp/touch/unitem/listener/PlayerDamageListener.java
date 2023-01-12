package cn.hellp.touch.unitem.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.*;

import static org.bukkit.event.entity.EntityDamageEvent.DamageCause;
public class PlayerDamageListener implements Listener {

    public static class Damage {
        public DamageCause reason = DamageCause.VOID;
        public double damage = 0;
        public Object source = null;
        public Damage(double damage, Object source, DamageCause reason) {
            this.damage=damage;
            this.reason=reason;
            this.source=source;
        }
        public Damage(double damage,Object source) {
            this(damage,source,DamageCause.VOID);
        }
    }

    private static void checkAlreadyCreateLogList(Player player) {
        damageLog.putIfAbsent(player.getUniqueId(),new ArrayList<>());
    }

    private static final Map<UUID, List<Damage>> damageLog = new HashMap<>();

    @EventHandler
    public static void onDamageByEntity(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player) {
            Player player = ((Player) e.getEntity());
            checkAlreadyCreateLogList(player);
            damageLog.get(player.getUniqueId()).add(new Damage(e.getDamage(),e.getDamager(),e.getCause()));
        }
    }

    public static Map<UUID, List<Damage>> getDamageLog() {
        return damageLog;
    }

    @EventHandler
    public static void onDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player player = ((Player) e.getEntity());
            checkAlreadyCreateLogList(player);
            if(e.getCause()==DamageCause.CUSTOM) {
                damageLog.get(player.getUniqueId()).add(new Damage(e.getDamage(),null,e.getCause()));
            }
        }
    }

    @EventHandler
    public static void onDamageByBlock(EntityDamageByBlockEvent e) {
        if(e.getEntity() instanceof Player) {
            Player player = ((Player) e.getEntity());
            checkAlreadyCreateLogList(player);
            damageLog.get(player.getUniqueId()).add(new Damage(e.getDamage(),e.getDamager(),e.getCause()));
        }
    }
}
