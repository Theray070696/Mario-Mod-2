package io.github.Theray070696.mariodeath.block;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Created by Theray070696 on 8/16/2017
 */
public class BlockBeanstalk extends BlockMario
{
    public static final PropertyBool ISTOP = PropertyBool.create("istop");

    public BlockBeanstalk()
    {
        super();

        this.setDefaultState(this.blockState.getBaseState().withProperty(ISTOP, false));

        this.setHardness(6.0F);
        this.setUnlocalizedName("marioBlockBeanstalk");
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World world, BlockPos blockPos)
    {
        return new AxisAlignedBB(0.44F, 0.0F, 0.44F, 0.56F, 1.0F, 0.56F);
    }

    public void breakBlock(World world, BlockPos pos, IBlockState blockState)
    {
        if(world.getBlockState(pos.up()).getBlock() instanceof BlockBeanstalk)
        {
            world.destroyBlock(pos.up(), false);
        }
    }

    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            ++stack.stackSize;
            world.destroyBlock(pos, false);
        }
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if(side.equals(EnumFacing.DOWN))
        {
            return false;
        }

        if(side.equals(EnumFacing.UP) && !blockState.getValue(ISTOP))
        {
            return false;
        }

        return true;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity)
    {
        return true;
    }

    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {ISTOP});
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        Block block = world.getBlockState(pos.up()).getBlock();

        if(block instanceof BlockBeanstalk)
        {
            return state.withProperty(ISTOP, false);
        } else
        {
            return state.withProperty(ISTOP, true);
        }
    }
}
