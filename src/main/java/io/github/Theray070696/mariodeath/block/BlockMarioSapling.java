package io.github.Theray070696.mariodeath.block;

import io.github.Theray070696.mariodeath.world.gen.WorldGenMarioTree;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Created by Theray070696 on 8/22/2017
 */
public class BlockMarioSapling extends BlockMario implements IGrowable
{
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D,
            0.800000011920929D, 0.8999999761581421D);

    public BlockMarioSapling()
    {
        super(Material.PLANTS);

        this.setUnlocalizedName("marioBlockSapling");
        this.setHardness(0.0F);
    }

    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(!world.isRemote)
        {
            super.updateTick(world, pos, state, rand);

            if(world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
            {
                this.grow(world, rand, pos, state);
            }
        }
    }

    public void generateTree(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, pos))
        {
            return;
        }

        WorldGenerator worldgenerator = new WorldGenMarioTree();
        int i = 0;
        int j = 0;
        boolean flag = false;

        IBlockState air = Blocks.AIR.getDefaultState();

        if(flag)
        {
            world.setBlockState(pos.add(i, 0, j), air, 4);
            world.setBlockState(pos.add(i + 1, 0, j), air, 4);
            world.setBlockState(pos.add(i, 0, j + 1), air, 4);
            world.setBlockState(pos.add(i + 1, 0, j + 1), air, 4);
        } else
        {
            world.setBlockState(pos, air, 4);
        }

        if(!worldgenerator.generate(world, rand, pos.add(i, 0, j)))
        {
            if(flag)
            {
                world.setBlockState(pos.add(i, 0, j), state, 4);
                world.setBlockState(pos.add(i + 1, 0, j), state, 4);
                world.setBlockState(pos.add(i, 0, j + 1), state, 4);
                world.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
            } else
            {
                world.setBlockState(pos, state, 4);
            }
        }
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state)
    {
        if(((Integer) state.getValue(STAGE)).intValue() == 0)
        {
            world.setBlockState(pos, state.cycleProperty(STAGE), 4);
        } else
        {
            this.generateTree(world, pos, state, rand);
        }
    }
}
