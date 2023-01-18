package cn.hellp.touch.unitem.selector.block;

import cn.hellp.touch.unitem.selector.ISelector;
import net.minecraft.server.v1_12_R1.Vec3D;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.*;

public class LookingBlockSelector implements IBlockSelector<Block>{
    private final ISelector<?> players;

    public LookingBlockSelector(ISelector<?> players) {
        this.players = players;
    }

    public LookingBlockSelector() {
        this(null);
    }

    @Override
    public String selectorID() {
        return "lookingBlock";
    }

    private static class Pos {
        int x,y,z;

        public Pos(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Block getBlock(World world) {
            return world.getBlockAt(x,y,z);
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + z;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj==null) {
                return false;
            }else if(obj==this) {
                return true;
            }else if(obj.getClass() != this.getClass()) {
                return false;
            }
            Pos pos = ((Pos) obj);
            return x== pos.x && y== pos.y && z == pos.z;
        }
    }

    /*public static Block[] raytraceBlocks(Location location, Vector direction ,int length) {
        Set<Pos> result = new HashSet<>();
        int tracedLength = 0;
        Vector step = direction.normalize().multiply(0.2).clone();
        while (tracedLength<length) {
            tracedLength+=0.2;
            location.add(direction);
            Block b = location.getBlock();
            if(b.getType()!=Material.AIR) {
                result.add(new Pos(b.getX(),b.getY(),b.getZ()));
            }
            location.subtract(direction);
            direction = direction.add(step);
        }
        Block[] blocks = new Block[result.size()];
        World world = location.getWorld();
        int i = 0;
        for (Pos pos : result) {
            blocks[i++]= pos.getBlock(world);
        }
        return blocks;
    }*/

    public static Block[] raytraceBlocks(Location start ,Vector direction,int maxDistance) {
        BlockIterator iterator = new BlockIterator(start.getWorld(),start.toVector(),direction,0,maxDistance);
        List<Block> result = new ArrayList<>();
        while (iterator.hasNext()) {
            Block block = iterator.next();
            if(block.getType()!=Material.AIR) {
                result.add(block);
            }
        }
        return result.toArray(new Block[0]);
    }


    @Override
    public Block[] select(Player invoker) {
        if(players==null) {
            Block[] blocks = raytraceBlocks(invoker.getEyeLocation(), invoker.getEyeLocation().getDirection(), 10);
            if (blocks.length > 1) {
                return new Block[]{blocks[0]};
            }
            return blocks;
        } else {
            List<Block> result = new ArrayList<>();
            for (Object o : players.select(invoker)) {
                Player player = ((Player) o);
                Block[] blocks = raytraceBlocks(player.getEyeLocation(), player.getEyeLocation().getDirection(), 10);
                if(blocks.length>=1) {
                    result.add(blocks[0]);
                }
            }
            return result.toArray(new Block[0]);
        }
    }
}
