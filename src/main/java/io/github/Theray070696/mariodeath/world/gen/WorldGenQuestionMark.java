package io.github.Theray070696.mariodeath.world.gen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Created by Theray070696 on 8/16/2017
 */
public class WorldGenQuestionMark extends WorldGenerator
{
    private Block block;
    private boolean rare;

    public WorldGenQuestionMark(Block block, boolean rare)
    {
        this.block = block;
        this.rare = rare;
    }

    public boolean generate(World world, Random random, BlockPos pos)
    {
        int y = pos.getY();

        for(int m = y; m <= 200; ++m)
        {
            if(!world.isAirBlock(new BlockPos(pos.getX(), m, pos.getZ())))
            {
                y = m;
            }
        }

        if(world.getBlockState(new BlockPos(pos.getX(), y, pos.getZ())).getBlock() != Blocks.WATER && world.getBlockState(new BlockPos(pos.getX(), y, pos.getZ())).getBlock() != Blocks.FLOWING_WATER)
        {
            if(!rare)
            {
                world.setBlockState(new BlockPos(pos.getX(), y + 3 + random.nextInt(3), pos.getZ()), block.getDefaultState(), 2);
                return true;
            } else if(random.nextInt(50) == 0)
            {
                world.setBlockState(new BlockPos(pos.getX(), y + 3 + random.nextInt(3), pos.getZ()), block.getDefaultState(), 2);
                return true;
            }
        }

        return false;
    }
}
