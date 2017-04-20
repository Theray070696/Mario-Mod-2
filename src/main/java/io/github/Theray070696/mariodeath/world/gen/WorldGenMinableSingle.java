package io.github.Theray070696.mariodeath.world.gen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Created by Theray on 9/15/2015.
 */
public class WorldGenMinableSingle extends WorldGenerator
{
    private Block block;
    private int blockmeta;
    private Block target;
    private boolean rare;

    public WorldGenMinableSingle(Block block, int meta, Block target, boolean rare)
    {
        this.block = block;
        this.blockmeta = meta;
        this.target = target;
        this.rare = rare;
    }

    public WorldGenMinableSingle(Block block, int meta, Block target)
    {
        this(block, meta, target, false);
    }

    public WorldGenMinableSingle(Block block, Block target, boolean rare)
    {
        this(block, 0, target, rare);
    }

    public WorldGenMinableSingle(Block block, Block target)
    {
        this(block, 0, target);
    }

    public WorldGenMinableSingle(Block block)
    {
        this(block, Blocks.stone);
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        if(world.getBlock(x, y, z).isReplaceableOreGen(world, x, y, z, this.target))
        {
            if(!this.rare)
            {
                world.setBlock(x, y, z, this.block, this.blockmeta, 2);
            } else
            {
                if(random.nextInt(50) + 1 == 1)
                {
                    world.setBlock(x, y, z, this.block, this.blockmeta, 2);
                }
            }
        }
        return true;
    }
}