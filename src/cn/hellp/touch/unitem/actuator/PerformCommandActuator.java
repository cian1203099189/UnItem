package cn.hellp.touch.unitem.actuator;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PerformCommandActuator implements IActuator{
    @Nullable
    @Override
    public Object actuate(Object... target) {
        CommandSender sender = (CommandSender) target[0];
        Bukkit.dispatchCommand(sender, ((String) target[1]));
        return sender;
    }

    @NotNull
    @Override
    public String actuatorID() {
        return "performCommand";
    }
}
