package cn.hellp.touch.unitem.support;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public enum MoneySupport {
    INSTANCE;
    private boolean loaded = false;
    private static Economy econ = null;

    private boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public double getMoney(OfflinePlayer player) {
        return econ.getBalance(player);
    }

    public double takeMoney(OfflinePlayer player,double value) {
        value = Math.min(getMoney(player), value);
        EconomyResponse r = econ.depositPlayer(player, value);
        return value;
    }

    public boolean init() {
        loaded=setupEconomy();
        return loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }
}
