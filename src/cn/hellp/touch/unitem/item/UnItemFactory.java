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
            BaseBuilder builder = configuration.contains("attribute") ? BuilderAttribute.getFromName(configuration.getString("attribute")).createBuilder() : new ItemBuilder();
            builder.load(configuration);
            UnItem item = new UnItem(builder.getName());
            if(configuration.contains("skills")) {
                ConfigurationSection skillsSection = configuration.getConfigurationSection("skills");
                if(skillsSection!=null) {
                    for (String sectionKey : skillsSection.getKeys(false)) {
                        List<String> skillsString = skillsSection.getStringList(sectionKey);
                        item.setSkill(Trigger.Timing.valueOf(sectionKey.toUpperCase()),skillsString);
                    }
                }
            }
            item.setItemBuilder(builder);
            return item;
        } catch (IOException | InvalidConfigurationException e) {
            throw new ERROR(e);
        }
    }
}
