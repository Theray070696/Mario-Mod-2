package io.github.Theray070696.mariodeath.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;

/**
 * Created by Theray070696 on 4/18/2017.
 */
public class BlockMarioConnectedTexture extends BlockMario
{
    public static final PropertyBool CONNECTED_DOWN = PropertyBool.create("connected_down");
    public static final PropertyBool CONNECTED_UP = PropertyBool.create("connected_up");
    public static final PropertyBool CONNECTED_NORTH = PropertyBool.create("connected_north");
    public static final PropertyBool CONNECTED_SOUTH = PropertyBool.create("connected_south");
    public static final PropertyBool CONNECTED_WEST = PropertyBool.create("connected_west");
    public static final PropertyBool CONNECTED_EAST = PropertyBool.create("connected_east");

    public BlockMarioConnectedTexture(Material material)
    {
        this(material, true);
    }

    public BlockMarioConnectedTexture(Material material, boolean addToCreativeTab)
    {
        super(material, addToCreativeTab);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(CONNECTED_DOWN, Boolean.FALSE)
                .withProperty(CONNECTED_EAST, Boolean.FALSE)
                .withProperty(CONNECTED_NORTH, Boolean.FALSE)
                .withProperty(CONNECTED_SOUTH, Boolean.FALSE)
                .withProperty(CONNECTED_UP, Boolean.FALSE)
                .withProperty(CONNECTED_WEST, Boolean.FALSE));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos position)
    {
        return state.withProperty(CONNECTED_DOWN, this.isSideConnectable(world, position, EnumFacing.DOWN))
                .withProperty(CONNECTED_EAST, this.isSideConnectable(world, position, EnumFacing.EAST))
                .withProperty(CONNECTED_NORTH, this.isSideConnectable(world, position, EnumFacing.NORTH))
                .withProperty(CONNECTED_SOUTH, this.isSideConnectable(world, position, EnumFacing.SOUTH))
                .withProperty(CONNECTED_UP, this.isSideConnectable(world, position, EnumFacing.UP))
                .withProperty(CONNECTED_WEST, this.isSideConnectable(world, position, EnumFacing.WEST));
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{CONNECTED_DOWN, CONNECTED_UP, CONNECTED_NORTH, CONNECTED_SOUTH, CONNECTED_WEST,
                CONNECTED_EAST});
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    private boolean isSideConnectable(IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        final IBlockState original = world.getBlockState(pos);
        final IBlockState connected = world.getBlockState(pos.offset(side));

        return original != null && connected != null && canConnect(original, connected);
    }

    protected boolean canConnect(@Nonnull IBlockState original, @Nonnull IBlockState connected)
    {
        return original.getBlock() == connected.getBlock();
    }
}