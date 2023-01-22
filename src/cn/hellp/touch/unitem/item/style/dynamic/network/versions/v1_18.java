package cn.hellp.touch.unitem.item.style.dynamic.network.versions;

import cn.hellp.touch.unitem.item.style.dynamic.network.ChannelInjector;
import cn.hellp.touch.unitem.item.style.dynamic.network.VersionSupport;
import io.netty.channel.Channel;

public class v1_18 implements VersionSupport {
    @Override
    public Object getPlayerConnection(Object entityPlayer) {
        return ChannelInjector.getField(ChannelInjector.entityPlayerClass,entityPlayer,"b");
    }

    @Override
    public Object getNetworkManager(Object playerConnection) {
        return ChannelInjector.getField(ChannelInjector.playerConnectionClass,playerConnection,"a");
    }

    @Override
    public Channel getChannel(Object networkManager) {
        return (Channel) ChannelInjector.getField(ChannelInjector.networkManagerClass,networkManager,"k");
    }

    @Override
    public String getWindowIdFiledName() {
        //TODO add
        return "j";
    }

    @Override
    public String getSendPacketMethodName() {
        return "a";
    }

    @Override
    public String getSetSlotPacketItemStackFiledName() {
        return "f";
    }
}
