package cn.hellp.touch.unitem.selector.block;

import net.minecraft.server.v1_12_R1.Vec3D;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class LookingBlockSelector implements IBlockSelector<Block>{
    @Override
    public String selectorID() {
        return "lookingBlock";
    }

    public static Block[] raytraceBlocks(Location location, Vector direction ,int length) {
        Set<Block> result = new HashSet<>();
        int tracedLength = 0;
        Vector step = direction.normalize();
        while (tracedLength<length) {
            tracedLength+=1;
            direction = direction.add(step);
            location.add(direction);
            Block b = location.getBlock();
            if(b.getType()!=Material.AIR) {
                result.add(b);
            }
            location.subtract(direction);
        }
        return result.toArray(new Block[0]);
    }

    @Override
    public Block[] select(Player invoker) {
        Block[] blocks = raytraceBlocks(invoker.getEyeLocation(),invoker.getEyeLocation().getDirection(),10);
        if(blocks.length>1) {
            return new Block[] {blocks[0]};
        }
        return blocks;
    }
}
