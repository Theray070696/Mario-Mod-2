package io.github.Theray070696.mario2.block;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 8/23/2017
 */
public class BlockMarioLog extends BlockMario
{
    public static final PropertyEnum<BlockLog.EnumAxis> LOG_AXIS = PropertyEnum.create("axis", BlockLog.EnumAxis.class);

    public BlockMarioLog()
    {
        super(Material.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
        this.setUnlocalizedName("marioBlockLog");
        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
    }

    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        if(world.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5)))
        {
            for(BlockPos blockPos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4)))
            {
                IBlockState iblockstate = world.getBlockState(blockPos);

                if(iblockstate.getBlock().isLeaves(iblockstate, world, blockPos))
                {
                    iblockstate.getBlock().beginLeavesDecay(iblockstate, world, blockPos);
                }
            }
        }
    }

    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
            EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch(rot)
        {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch(state.getValue(LOG_AXIS))
                {
                    case X:
                        return state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                    case Z:
                        return state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                    default:
                        return state;
                }

            default:
                return state;
        }
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis)
    {
        IBlockState state = world.getBlockState(pos);
        for(IProperty<?> prop : state.getProperties().keySet())
        {
            if(prop.getName().equals("axis"))
            {
                world.setBlockState(pos, state.cycleProperty(prop));
                return true;
            }
        }
        return false;
    }

    public IBlockState getStateFromMeta(int meta)
    {
        BlockLog.EnumAxis axis = BlockLog.EnumAxis.Y;
        int i = meta & 12;

        if(i == 4)
        {
            axis = BlockLog.EnumAxis.X;
        } else if(i == 8)
        {
            axis = BlockLog.EnumAxis.Z;
        }

        return this.getDefaultState().withProperty(LOG_AXIS, axis);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        BlockLog.EnumAxis axis = state.getValue(LOG_AXIS);

        if(axis == BlockLog.EnumAxis.X)
        {
            i |= 4;
        } else if(axis == BlockLog.EnumAxis.Z)
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, LOG_AXIS);
    }
}
