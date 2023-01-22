package cn.hellp.touch.unitem.item.style.dynamic.network;

import io.netty.channel.Channel;

public interface VersionSupport {
    /**
     * 获取一个EntityPlayer的PlayerConnection字段
     * @param entityPlayer 拥有该字段的EntityPlayer
     * @return 该EntityPlayer的PlayerConnection
     */
    Object getPlayerConnection(Object entityPlayer);

    /**
     * 获取一个PlayerConnection的NetworkManager字段
     * @param playerConnection 拥有该字段的PlayerConnection
     * @return 该PlayerConnection的NetworkManager
     */
    Object getNetworkManager(Object playerConnection);

    /**
     * 获取一个NetworkManager的Channel字段
     * @param networkManager 拥有该字段的NetworkManager
     * @return 该NetworkManager的Channel字段
     */
    Channel getChannel(Object networkManager);

    String getWindowIdFiledName();

    default String getSendPacketMethodName() {
        return "sendPacket";
    }

    String getSetSlotPacketItemStackFiledName();
}
