package cn.hellp.touch.unitem.item;

import cn.hellp.touch.unitem.auxiliary.ERROR;
import cn.hellp.touch.unitem.trigger.Trigger;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class UnItemFactory {
    public static UnItem loadFile(File file) {
        YamlConfiguration configuration = new YamlConfiguration();
        try {
            configuration.load(file);
            String name = configuration.getString("name");
            String material = configuration.getString("material");
            if(name==null || material==null) {
                throw new RuntimeException("can't load item from "+file.getName());
            }
            UnItem item = new UnItem(name);
            ItemBuilder builder = new ItemBuilder();
            builder.setNbtValue(name);
            builder.setMaterial(Material.getMaterial(material));
            if(configuration.contains("displayName")) {
                builder.setDisplayName(configuration.getString("displayName"));
            }
            if(configuration.contains("lore")) {
                builder.setLore(configuration.getStringList("lore"));
            }
            if(configuration.contains("enchants")) {
                ConfigurationSection enchantsConfig = configuration.getConfigurationSection("enchants");
                if(enchantsConfig!=null) {
                    for (String enchantName : enchantsConfig.getKeys(false)) {
                        Enchantment enchantment = Enchantment.getByName(enchantName);
                        if(enchantment!=null) {
                            short i = Short.parseShort(Objects.requireNonNull(enchantsConfig.getString(enchantName)));
                            builder.addEnchantments(enchantment,i);
                        }
                    }
                }
            }
            if(configuration.contains("item-flags")) {
                List<String> flags = configuration.getStringList("item-flags");
                for (String flag : flags) {
                    try {
                        ItemFlag itemFlag = ItemFlag.valueOf(flag);
                        builder.addItemFlags(itemFlag);
                    } catch (Exception ignored) {}
                }
            }
            if(configuration.contains("unbreakable")) {
                builder.setUnbreakable(configuration.getBoolean("unbreakable"));
            }
            item.setItemBuilder(builder);
            if(configuration.contains("skills")) {
                ConfigurationSection skillsSection = configuration.getConfigurationSection("skills");
                if(skillsSection!=null) {
                    for (String sectionKey : skillsSection.getKeys(false)) {
                        List<String> skillsString = skillsSection.getStringList(sectionKey);
                        item.setSkill(Trigger.Timing.valueOf(sectionKey.toUpperCase()),skillsString);
                    }
                }
            }
            return item;
        } catch (IOException | InvalidConfigurationException e) {
            throw new ERROR(e);
        }
    }
}
