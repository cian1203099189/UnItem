package cn.hellp.touch.unitem.item.style.dynamic.network;

import cn.hellp.touch.unitem.auxiliary.NbtTool;
import cn.hellp.touch.unitem.item.ItemBuilder;
import cn.hellp.touch.unitem.item.UnItem;
import cn.hellp.touch.unitem.item.UnItemManager;
import cn.hellp.touch.unitem.plugin.Main;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;

public class ItemStackUpdateChannelHandler extends ChannelOutboundHandlerAdapter {
    private Player player;

    public ItemStackUpdateChannelHandler(Player player) {
        this.player = player;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(ChannelInjector.packetSetSlot.isInstance(msg)) {
            Field f = ChannelInjector.packetSetSlot.getDeclaredField(ChannelInjector.versionSupport.getSetSlotPacketItemStackFiledName());
            f.setAccessible(true);
            f.set(msg,updateItem(f.get(msg)));
        }
        super.write(ctx, msg, promise);
    }

    private Object updateItem(Object nmsStack) {
        Object craftItemStack = ChannelInjector.callMethod(NbtTool.craftItemStack,"asCraftMirror",null,new Class[]{NbtTool.itemStackClass},nmsStack);
        String unName = NbtTool.getString(((ItemStack) craftItemStack), Main.nbtPrefix);
        if(UnItemManager.contains(unName)) {
            UnItem unItem = UnItemManager.getItem(unName);
            if(unItem.getItemBuilder() instanceof ItemBuilder) {
                ChannelInjector.callMethod(ItemStack.class, "setItemMeta", craftItemStack, new Class[]{ItemMeta.class}, ((ItemBuilder) unItem.getItemBuilder()).now(player, (ItemStack) craftItemStack));
                return NbtTool.asNmsCopy(((ItemStack) craftItemStack));
            }
        }
        return nmsStack;
    }
}