package cn.hellp.touch.unitem.actuator.entity;

import cn.hellp.touch.unitem.actuator.IActuator;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SendMessageActuator implements IActuator {
    @Override
    public @Nullable Object actuate(Object... target) {
        CommandSender sender = ((CommandSender) target[0]);
        sender.sendMessage(((String) target[1]));
        return null;
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "sendMessage";
    }
}
