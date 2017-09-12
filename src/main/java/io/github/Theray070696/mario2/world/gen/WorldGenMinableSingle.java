package io.github.Theray070696.mario2.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Created by Theray070696 on 9/15/2015.
 */
public class WorldGenMinableSingle extends WorldGenerator
{
    private Block block;
    private Block target;
    private boolean rare;

    public WorldGenMinableSingle(Block block, Block target, boolean rare)
    {
        this.block = block;
        this.target = target;
        this.rare = rare;
    }

    public WorldGenMinableSingle(Block block, Block target)
    {
        this(block, target, false);
    }

    public WorldGenMinableSingle(Block block)
    {
        this(block, Blocks.STONE);
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos)
    {
        if(world.getBlockState(pos).getBlock().isReplaceableOreGen(world.getBlockState(pos), world, pos, BlockMatcher.forBlock(this.target)))
        {
            if(!this.rare)
            {
                world.setBlockState(pos, this.block.getDefaultState(), 2);
            } else
            {
                if(random.nextInt(50) + 1 == 1)
                {
                    world.setBlockState(pos, this.block.getDefaultState(), 2);
                }
            }
        }
        return true;
    }
}