package cn.hellp.touch.unitem.item.style.dynamic.network;

import cn.hellp.touch.unitem.auxiliary.NbtTool;
import cn.hellp.touch.unitem.item.style.dynamic.network.versions.v1_19;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import org.bukkit.entity.Player;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ChannelInjector {
    public static final Class<?> networkManagerClass;
    public static final Class<?> craftPlayerClass;
    public static final Class<?> entityPlayerClass;
    public static final Class<?> playerConnectionClass;
    public static final Class<?> craftInventoryView;
    public static final Class<?> container;
    public static final Unsafe unsafe;

    public static final Class<?> packetSetSlot;
    public static final Class<?> packetClass;
    public static final Class<?> packetWindowItems;

    public static final VersionSupport versionSupport;

    static {
        try {
            if (NbtTool.later1_17) {
                networkManagerClass = Class.forName("net.minecraft.network.NetworkManager");
                playerConnectionClass = Class.forName("net.minecraft.server.network.PlayerConnection");
                entityPlayerClass = Class.forName("net.minecraft.server.level.EntityPlayer");
                packetSetSlot = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutSetSlot");
                packetWindowItems = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutWindowItems");
                container = Class.forName("net.minecraft.world.inventory.Container");
                packetClass = Class.forName("net.minecraft.network.protocol.Packet");
            } else {
                networkManagerClass = Class.forName("net.minecraft.server."+NbtTool.nmsVersion+".NetworkManager");
                playerConnectionClass = Class.forName("net.minecraft.server."+NbtTool.nmsVersion+".PlayerConnection");
                entityPlayerClass = Class.forName("net.minecraft.server."+NbtTool.nmsVersion+".EntityPlayer");
                packetSetSlot = Class.forName("net.minecraft.server."+NbtTool.nmsVersion+".PacketPlayOutSetSlot");
                packetWindowItems = Class.forName("net.minecraft.server."+NbtTool.nmsVersion+".PacketPlayOutWindowItems");
                container = Class.forName("net.minecraft.server."+NbtTool.nmsVersion+".Container");
                packetClass = Class.forName("net.minecraft.server."+NbtTool.nmsVersion+".Packet");
            }
            {
                Field field = Unsafe.class.getDeclaredField("theUnsafe");
                field.setAccessible(true);
                unsafe= (Unsafe) field.get(null);
            }
            versionSupport= (VersionSupport) Class.forName("cn.hellp.touch.unitem.item.style.dynamic.network.versions.v1_"+NbtTool.server_version).getDeclaredConstructor().newInstance();
            craftPlayerClass = Class.forName("org.bukkit.craftbukkit."+NbtTool.nmsVersion+".entity.CraftPlayer");
            craftInventoryView = Class.forName("org.bukkit.craftbukkit."+NbtTool.nmsVersion+".inventory.CraftInventoryView");
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException | InvocationTargetException |
                 InstantiationException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getField(Class<?> find,Object source,String fieldName) {
        try {
            Field field = find.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(source);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object callMethod(Class<?> find,String methodName, Object source, Class<?>[] pragmaType, Object... pragma) {
        try {
            Method method = find.getDeclaredMethod(methodName,pragmaType);
            method.setAccessible(true);
            return method.invoke(source,pragma);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getPlayerConnection(Player player) {
        Object entityPlayer = callMethod(craftPlayerClass,"getHandle",player,new Class[0]);
        return versionSupport.getPlayerConnection(entityPlayer);
    }

    public static void injectorChannelHandler(Player target, ChannelHandler handler) {
        Object playerConnection = getPlayerConnection(target);
        Object networkManager = versionSupport.getNetworkManager(playerConnection);
        Channel channel = versionSupport.getChannel(networkManager);
        channel.pipeline().addLast("unItemDynamic",handler);
    }
}
