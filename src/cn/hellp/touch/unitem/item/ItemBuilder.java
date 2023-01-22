package cn.hellp.touch.unitem.item;

import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.auxiliary.NbtTool;
import cn.hellp.touch.unitem.auxiliary.Pair;
import cn.hellp.touch.unitem.item.style.Style;
import cn.hellp.touch.unitem.plugin.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemBuilder implements BaseBuilder {
    private final List<Pair<Enchantment, Short>> enchantments = new ArrayList<>();
    private final List<ItemFlag> itemFlags = new ArrayList<>();
    private List<Style> lore = new ArrayList<>();
    private Style displayName;
    private String nbtValue;
    private boolean unbreakable = false;
    private Material material;

    public ItemBuilder setDisplayName(String displayName) {
        this.displayName = Style.create(displayName);
        return this;
    }

    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    public ItemBuilder addEnchantments(Enchantment enchantment, short level) {
        this.enchantments.add(new Pair<>(enchantment, level));
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag itemFlag) {
        this.itemFlags.add(itemFlag);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        for (String s : lore) {
            this.lore.add(Style.create(s));
        }
        return this;
    }

    public ItemBuilder addLore(String lore) {
        this.lore.add(Style.create(lore));
        return this;
    }

    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder setNbtValue(String nbtValue) {
        this.nbtValue = nbtValue;
        return this;
    }

    @Override
    public ItemStack toBukkitItemStack() {
        if (material == null) {
            throw new ERROR("can't create item on a empty config");
        }
        ItemStack itemStack = new ItemStack(material);
        ItemMeta im = itemStack.getItemMeta();
        if (!enchantments.isEmpty()) {
            for (Pair<Enchantment, Short> enchantment : enchantments) {
                im.addEnchant(enchantment.getFirst(), enchantment.getSecond(), true);
            }
        }
        if (!itemFlags.isEmpty()) {
            for (ItemFlag itemFlag : itemFlags) {
                im.addItemFlags(itemFlag);
            }
        }
        if (NbtTool.server_version >= 12) {
            im.setUnbreakable(true);
        } else {
            im.spigot().setUnbreakable(true);
        }
        itemStack.setItemMeta(im);
        if (nbtValue != null && !nbtValue.isEmpty()) {
            itemStack = NbtTool.setString(itemStack, Main.nbtPrefix, nbtValue);
        }
        if (displayName != null) {
            im.setDisplayName(displayName.update(null, itemStack));
        }
        if (lore != null && !lore.isEmpty()) {
            final ItemStack finalItemStack = itemStack;
            List<String> temp = lore.stream().map(style -> style.update(null, finalItemStack)).collect(Collectors.toList());
            im.setLore(temp);
        }
        return itemStack;
    }

    public ItemMeta update(Player origin, ItemStack eventItem) {
        if (eventItem != null && eventItem.getAmount() != 0 && eventItem.getType() != Material.AIR) {
            ItemMeta im = eventItem.getItemMeta();
            im.setDisplayName(displayName.update(origin, eventItem));
            im.setLore(lore.stream().map(style -> style.update(origin, eventItem)).collect(Collectors.toList()));
            return im;
        }
        return eventItem != null && eventItem.hasItemMeta() ? eventItem.getItemMeta() : null;
    }



    @Override
    public void load(YamlConfiguration configuration) {
        String name = configuration.getString("name");
        String material = configuration.getString("material");
        if (name == null || material == null) {
            throw new RuntimeException("can't load item from " + configuration.getName());
        }
        setNbtValue(name);
        setMaterial(Material.getMaterial(material));
        if (configuration.contains("displayName")) {
            setDisplayName(configuration.getString("displayName"));
        }
        if (configuration.contains("lore")) {
            setLore(configuration.getStringList("lore"));
        }
        if (configuration.contains("enchants")) {
            ConfigurationSection enchantsConfig = configuration.getConfigurationSection("enchants");
            if (enchantsConfig != null) {
                for (String enchantName : enchantsConfig.getKeys(false)) {
                    Enchantment enchantment = Enchantment.getByName(enchantName);
                    if (enchantment != null) {
                        short i = Short.parseShort(Objects.requireNonNull(enchantsConfig.getString(enchantName)));
                        addEnchantments(enchantment, i);
                    }
                }
            }
        }
        if (configuration.contains("item-flags")) {
            List<String> flags = configuration.getStringList("item-flags");
            for (String flag : flags) {
                try {
                    ItemFlag itemFlag = ItemFlag.valueOf(flag);
                    addItemFlags(itemFlag);
                } catch (Exception ignored) {
                }
            }
        }
        if (configuration.contains("unbreakable")) {
            setUnbreakable(configuration.getBoolean("unbreakable"));
        }
    }

    @Override
    public String getName() {
        return nbtValue;
    }

    public ItemMeta now(Player origin, ItemStack eventItem) {
        if (eventItem != null && eventItem.getAmount() != 0 && eventItem.getType() != Material.AIR) {
            ItemMeta im = eventItem.getItemMeta();
            im.setDisplayName(displayName.now(origin));
            im.setLore(lore.stream().map(style -> style.now(origin)).collect(Collectors.toList()));
            return im;
        }
        return eventItem != null && eventItem.hasItemMeta() ? eventItem.getItemMeta() : null;
    }
}
