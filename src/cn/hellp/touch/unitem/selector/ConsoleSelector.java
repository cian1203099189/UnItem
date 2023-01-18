package cn.hellp.touch.unitem.selector;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConsoleSelector implements ISelector<CommandSender>{
    @Override
    public CommandSender[] select(Player invoker) {
        return new CommandSender[] {Bukkit.getConsoleSender()};
    }

    @Override
    public String selectorID() {
        return "console";
    }
}
