package io.github.Theray070696.mario2.block;

import io.github.Theray070696.mario2.world.gen.WorldGenMarioTree;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
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

    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        IBlockState ground = world.getBlockState(pos.down());
        return super.canPlaceBlockAt(world, pos) && ground.getBlock() instanceof BlockMario && ((BlockMario) ground.getBlock()).isGround();
    }

    protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() instanceof BlockMario && ((BlockMario) state.getBlock()).isGround();
    }

    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block)
    {
        super.neighborChanged(state, world, pos, block);
        this.checkAndDropBlock(world, pos, state);
    }

    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if(!this.canBlockStay(worldIn, pos, state))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
    {
        if(state.getBlock() == this)
        {
            IBlockState ground = world.getBlockState(pos.down());
            return ground.getBlock() instanceof BlockMario && ((BlockMario) ground.getBlock()).isGround();
        }
        return this.canSustainBush(world.getBlockState(pos.down()));
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SAPLING_AABB;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World world, BlockPos pos)
    {
        return NULL_AABB;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(!world.isRemote)
        {
            this.checkAndDropBlock(world, pos, state);

            if(world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
            {
                this.grow(world, rand, pos, state);
            }
        }
    }

    private void generateTree(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(!TerrainGen.saplingGrowTree(world, rand, pos))
        {
            return;
        }

        WorldGenMarioTree treeGenerator = new WorldGenMarioTree();

        IBlockState air = Blocks.AIR.getDefaultState();

        world.setBlockState(pos, air, 4);

        if(!treeGenerator.generate(world, rand, pos))
        {
            world.setBlockState(pos, state, 4);
        }
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state)
    {
        return (double) world.rand.nextFloat() < 0.45D;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state)
    {
        if(state.getValue(STAGE) == 0)
        {
            world.setBlockState(pos, state.cycleProperty(STAGE), 4);
        } else
        {
            this.generateTree(world, pos, state, rand);
        }
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(STAGE, (meta & 8) >> 3);
    }

    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(STAGE) << 3;
        return i;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, STAGE);
    }
}
