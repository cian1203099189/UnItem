package cn.hellp.touch.unitem.item.style.dynamic.network.versions;

import cn.hellp.touch.unitem.item.style.dynamic.network.ChannelInjector;
import cn.hellp.touch.unitem.item.style.dynamic.network.VersionSupport;
import io.netty.channel.Channel;

public class v1_14 implements VersionSupport {
    @Override
    public Object getPlayerConnection(Object entityPlayer) {
        return ChannelInjector.getField(ChannelInjector.entityPlayerClass,entityPlayer,"playerConnection");
    }

    @Override
    public Object getNetworkManager(Object playerConnection) {
        return ChannelInjector.getField(ChannelInjector.playerConnectionClass,playerConnection,"networkManager");
    }

    @Override
    public Channel getChannel(Object networkManager) {
        return (Channel) ChannelInjector.getField(ChannelInjector.networkManagerClass,networkManager,"channel");
    }

    @Override
    public String getWindowIdFiledName() {
        return "windowId";
    }

    @Override
    public String getSetSlotPacketItemStackFiledName() {
        return "c";
    }
}
