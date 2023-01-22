package cn.hellp.touch.unitem.item.style.dynamic;

import cn.hellp.touch.unitem.item.style.dynamic.network.ChannelInjector;
import cn.hellp.touch.unitem.item.style.dynamic.network.ItemStackUpdateChannelHandler;
import cn.hellp.touch.unitem.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerNetworkListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ChannelInjector.injectorChannelHandler(event.getPlayer(),new ItemStackUpdateChannelHandler(event.getPlayer()));
    }

    public static void init() {
        Bukkit.getPluginManager().registerEvents(new PlayerNetworkListener(),Main.getInstance());
    }
}
