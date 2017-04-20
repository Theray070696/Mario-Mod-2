package io.github.Theray070696.mariodeath.block;

import io.github.Theray070696.mariodeath.block.tile.TilePipe;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nullable;

/**
 * Created by Theray070696 on 2/9/2017.
 */
public class BlockPipe extends BlockMario implements ITileEntityProvider
{
    public static final PropertyDirection FACING = BlockDirectional.FACING;
    public static final PropertyBool ISMULTIBLOCK = PropertyBool.create("ismultiblock");
    public static final PropertyBool CONNECTEDRIGHT = PropertyBool.create("connectedright");
    public static final PropertyBool CONNECTEDDOWN = PropertyBool.create("connecteddown");
    public static final PropertyBool REARBLOCK = PropertyBool.create("rearblock");

    public boolean shouldBreakBlock = true;

    public BlockPipe()
    {
        super();

        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ISMULTIBLOCK, false).withProperty(CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, false));
        
        this.setUnlocalizedName("marioBlockPipe");
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World world, BlockPos blockPos)
    {
        EnumFacing side = blockState.getValue(FACING);
        boolean rearBlock = getActualState(blockState, world, blockPos).getValue(REARBLOCK);

        if(side == EnumFacing.UP && !rearBlock)
        {
            return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D);
        } else if(side == EnumFacing.DOWN && !rearBlock)
        {
            return new AxisAlignedBB(0.0D, 0.375D, 0.0D, 1.0D, 1.0D, 1.0D);
        } else if(side == EnumFacing.EAST && !rearBlock)
        {
            return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
        } else if(side == EnumFacing.WEST && !rearBlock)
        {
            return new AxisAlignedBB(0.325D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        } else if(side == EnumFacing.NORTH && !rearBlock)
        {
            return new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D);
        } else if(side == EnumFacing.SOUTH && !rearBlock)
        {
            return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D);
        }

        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    }

    @Override
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState blockState, Entity entity)
    {
        if(world.getTileEntity(blockPos) instanceof TilePipe)
        {
            TilePipe tilePipe = (TilePipe) world.getTileEntity(blockPos);

            if(tilePipe.hasMaster())
            {
                if(tilePipe.isMaster())
                {
                    // Get warp id
                    int warpID = tilePipe.getWarpID();



                    // Make sure there is another pipe with this ID.
                    // Play sound
                    // Then teleport the player

                } else
                {
                    // Get master coord position
                    int x = tilePipe.getMasterX();
                    int y = tilePipe.getMasterY();
                    int z = tilePipe.getMasterZ();

                    BlockPos pos = new BlockPos(x, y, z);
                    if(world.getTileEntity(pos) instanceof TilePipe)
                    {
                        // Get master tile
                        tilePipe = (TilePipe) world.getTileEntity(pos);
                        if(tilePipe.hasMaster() && tilePipe.isMaster()) // This shouldn't not be the case, but better to be safe than sorry.
                        {
                            // Get warp id
                            int warpID = tilePipe.getWarpID();



                            // Make sure there is another pipe with this ID.
                            // Play sound
                            // Then teleport the player

                        }
                    }
                }
            }
        }

        //world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundHandler.noteBlock, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    private void setMultiBlockFacing(World world, int x, int y, int z, EnumFacing side, boolean isMultiBlock)
    {
        BlockPos modPos = getPositionModifiers(side);
        int xMod = modPos.getX(), yMod = modPos.getY(), zMod = modPos.getZ();

        TilePipe tilePipe;
        BlockPos pos = new BlockPos(x, y, z);

        if(world.getBlockState(pos).getBlock() instanceof BlockPipe)
        {
            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = false;

            world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, false), 2);

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setIsMaster(isMultiBlock);
            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(x, y, z);

                tilePipe.setConnectedRight(true);
                tilePipe.setConnectedDown(true);
                tilePipe.setRearBlock(false);
            }

            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = true;

            tilePipe.validate();
            world.setTileEntity(pos, tilePipe);
        }

        pos = new BlockPos(x, y + yMod, z);

        if(world.getBlockState(pos).getBlock() instanceof BlockPipe)
        {
            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = false;

            if(side == EnumFacing.NORTH || side == EnumFacing.WEST || side == EnumFacing.SOUTH || side == EnumFacing.EAST)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, false), 2);
            } else if(side == EnumFacing.UP || side == EnumFacing.DOWN)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, true), 2);
            }

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(x, y, z);

                if(side == EnumFacing.NORTH || side == EnumFacing.WEST || side == EnumFacing.SOUTH || side == EnumFacing.EAST)
                {
                    tilePipe.setConnectedRight(true);
                    tilePipe.setConnectedDown(false);
                    tilePipe.setRearBlock(false);
                } else if(side == EnumFacing.UP || side == EnumFacing.DOWN)
                {
                    tilePipe.setConnectedRight(true);
                    tilePipe.setConnectedDown(true);
                    tilePipe.setRearBlock(true);
                }
            }

            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = true;

            tilePipe.validate();
            world.setTileEntity(pos, tilePipe);
        }

        pos = new BlockPos(x + xMod, y, z);

        if(world.getBlockState(pos).getBlock() instanceof BlockPipe)
        {
            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = false;

            if(side == EnumFacing.UP || side == EnumFacing.NORTH || side == EnumFacing.SOUTH || side == EnumFacing.DOWN)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, false), 2);
            } else if(side == EnumFacing.EAST || side == EnumFacing.WEST)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, true), 2);
            }

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(x, y, z);

                if(side == EnumFacing.UP || side == EnumFacing.NORTH || side == EnumFacing.SOUTH || side == EnumFacing.DOWN)
                {
                    tilePipe.setConnectedRight(false);
                    tilePipe.setConnectedDown(true);
                    tilePipe.setRearBlock(false);
                } else if(side == EnumFacing.EAST || side == EnumFacing.WEST)
                {
                    tilePipe.setConnectedRight(true);
                    tilePipe.setConnectedDown(true);
                    tilePipe.setRearBlock(true);
                }
            }

            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = true;

            tilePipe.validate();
            world.setTileEntity(pos, tilePipe);
        }

        pos = new BlockPos(x + xMod, y + yMod, z);

        if(world.getBlockState(pos).getBlock() instanceof BlockPipe)
        {
            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = false;

            if(side == EnumFacing.EAST || side == EnumFacing.WEST)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, true), 2);
            } else if(side == EnumFacing.DOWN || side == EnumFacing.UP)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, true), 2);
            } else if(side == EnumFacing.SOUTH || side == EnumFacing.NORTH)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, false), 2);
            }

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(x, y, z);

                if(side == EnumFacing.EAST || side == EnumFacing.WEST)
                {
                    tilePipe.setConnectedRight(true);
                    tilePipe.setConnectedDown(false);
                    tilePipe.setRearBlock(true);
                } else if(side == EnumFacing.DOWN || side == EnumFacing.UP)
                {
                    tilePipe.setConnectedRight(false);
                    tilePipe.setConnectedDown(true);
                    tilePipe.setRearBlock(true);
                } else if(side == EnumFacing.SOUTH || side == EnumFacing.NORTH)
                {
                    tilePipe.setConnectedRight(false);
                    tilePipe.setConnectedDown(false);
                    tilePipe.setRearBlock(false);
                }
            }

            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = true;

            tilePipe.validate();
            world.setTileEntity(pos, tilePipe);
        }

        pos = new BlockPos(x, y, z + zMod);

        if(world.getBlockState(pos).getBlock() instanceof BlockPipe)
        {
            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = false;

            if(side == EnumFacing.UP || side == EnumFacing.DOWN)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, false), 2);
            } else if(side == EnumFacing.WEST || side == EnumFacing.EAST)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, false), 2);
            } else if(side == EnumFacing.NORTH || side == EnumFacing.SOUTH)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, true), 2);
            }

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(x, y, z);

                if(side == EnumFacing.EAST || side == EnumFacing.WEST)
                {
                    tilePipe.setConnectedRight(false);
                    tilePipe.setConnectedDown(true);
                    tilePipe.setRearBlock(false);
                } else if(side == EnumFacing.DOWN || side == EnumFacing.UP)
                {
                    tilePipe.setConnectedRight(true);
                    tilePipe.setConnectedDown(false);
                    tilePipe.setRearBlock(false);
                } else if(side == EnumFacing.SOUTH || side == EnumFacing.NORTH)
                {
                    tilePipe.setConnectedRight(true);
                    tilePipe.setConnectedDown(true);
                    tilePipe.setRearBlock(true);
                }
            }

            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = true;

            tilePipe.validate();
            world.setTileEntity(pos, tilePipe);
        }

        pos = new BlockPos(x + xMod, y, z + zMod);

        if(world.getBlockState(pos).getBlock() instanceof BlockPipe)
        {
            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = false;

            if(side == EnumFacing.UP || side == EnumFacing.DOWN)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, false), 2);
            } else if(side == EnumFacing.EAST || side == EnumFacing.WEST || side == EnumFacing.NORTH || side == EnumFacing.SOUTH)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, true), 2);
            }

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(x, y, z);

                if(side == EnumFacing.UP || side == EnumFacing.DOWN)
                {
                    tilePipe.setConnectedRight(false);
                    tilePipe.setConnectedDown(false);
                    tilePipe.setRearBlock(false);
                } else if(side == EnumFacing.EAST || side == EnumFacing.WEST || side == EnumFacing.NORTH || side == EnumFacing.SOUTH)
                {
                    tilePipe.setConnectedRight(false);
                    tilePipe.setConnectedDown(true);
                    tilePipe.setRearBlock(true);
                }
            }

            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = true;

            tilePipe.validate();
            world.setTileEntity(pos, tilePipe);
        }

        pos = new BlockPos(x, y + yMod, z + zMod);

        if(world.getBlockState(pos).getBlock() instanceof BlockPipe)
        {
            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = false;

            if(side == EnumFacing.NORTH || side == EnumFacing.SOUTH)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, true), 2);
            } else if(side == EnumFacing.UP || side == EnumFacing.DOWN)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, true), 2);
            } else if(side == EnumFacing.WEST || side == EnumFacing.EAST)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, false), 2);
            }

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(x, y, z);

                if(side == EnumFacing.EAST || side == EnumFacing.WEST)
                {
                    tilePipe.setConnectedRight(false);
                    tilePipe.setConnectedDown(false);
                    tilePipe.setRearBlock(false);
                } else if(side == EnumFacing.DOWN || side == EnumFacing.UP)
                {
                    tilePipe.setConnectedRight(true);
                    tilePipe.setConnectedDown(false);
                    tilePipe.setRearBlock(true);
                } else if(side == EnumFacing.SOUTH || side == EnumFacing.NORTH)
                {
                    tilePipe.setConnectedRight(true);
                    tilePipe.setConnectedDown(false);
                    tilePipe.setRearBlock(true);
                }
            }

            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = true;

            tilePipe.validate();
            world.setTileEntity(pos, tilePipe);
        }

        pos = new BlockPos(x + xMod, y + yMod, z + zMod);

        if(world.getBlockState(pos).getBlock() instanceof BlockPipe)
        {
            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = false;

            world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty(CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, true), 2);

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(x, y, z);

                tilePipe.setConnectedRight(false);
                tilePipe.setConnectedDown(false);
                tilePipe.setRearBlock(true);
            }

            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = true;

            tilePipe.validate();
            world.setTileEntity(pos, tilePipe);
        }
    }
    
    public static boolean isMultiBlock(World world, int x, int y, int z, int xMod, int yMod, int zMod)
    {
        if(world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof BlockPipe && world.getBlockState(new BlockPos(x, y + yMod, z)).getBlock() instanceof BlockPipe && world.getBlockState(new BlockPos(x + xMod, y, z)).getBlock() instanceof BlockPipe && world.getBlockState(new BlockPos(x + xMod, y + yMod, z)).getBlock() instanceof BlockPipe
           && world.getBlockState(new BlockPos(x, y, z + zMod)).getBlock() instanceof BlockPipe && world.getBlockState(new BlockPos(x + xMod, y, z + zMod)).getBlock() instanceof BlockPipe && world.getBlockState(new BlockPos(x, y + yMod, z + zMod)).getBlock() instanceof BlockPipe && world.getBlockState(new BlockPos(x + xMod, y + yMod, z + zMod)).getBlock() instanceof BlockPipe)
        {
            return true;
        }

        return false;
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
        {
            if(side == state.getValue(FACING) && state.getValue(ISMULTIBLOCK))
            {
                return false;
            }

            BlockPos modPos = getPositionModifiers(side);
            int xMod = modPos.getX(), yMod = modPos.getY(), zMod = modPos.getZ();

            if(isMultiBlock(world, pos.getX(), pos.getY(), pos.getZ(), xMod, yMod, zMod))
            {
                setMultiBlockFacing(world, pos.getX(), pos.getY(), pos.getZ(), side, true);
                return true;
            }
        }
        
        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState blockState)
    {
        EnumFacing side = blockState.getValue(FACING);

        if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TilePipe)
        {
            BlockPos modPos = getPositionModifiers(side);
            int xMod = modPos.getX(), yMod = modPos.getY(), zMod = modPos.getZ();

            TilePipe pipe = (TilePipe) world.getTileEntity(pos);
            if(pipe.isMaster())
            {
                setMultiBlockFacing(world, pos.getX(), pos.getY(), pos.getZ(), side, false);
            } else
            {
                pos = new BlockPos(pipe.getMasterX(), pipe.getMasterY(), pipe.getMasterZ());
                if(!isMultiBlock(world, pos.getX(), pos.getY(), pos.getZ(), xMod, yMod, zMod))
                {
                    setMultiBlockFacing(world, pos.getX(), pos.getY(), pos.getZ(), side, false);
                }
            }
        }

        if(shouldBreakBlock)
        {
            super.breakBlock(world, pos, blockState);
        }
    }

    private BlockPos getPositionModifiers(EnumFacing side)
    {
        int xMod = 0, yMod = 0, zMod = 0;

        if(side.equals(EnumFacing.WEST) || side.equals(EnumFacing.UP))
        {
            xMod = 1;
            yMod = -1;
            zMod = 1;
        } else if(side.equals(EnumFacing.EAST))
        {
            xMod = -1;
            yMod = -1;
            zMod = -1;
        } else if(side.equals(EnumFacing.NORTH))
        {
            xMod = -1;
            yMod = -1;
            zMod = 1;
        } else if(side.equals(EnumFacing.SOUTH))
        {
            xMod = 1;
            yMod = -1;
            zMod = -1;
        } else if(side.equals(EnumFacing.DOWN))
        {
            xMod = 1;
            yMod = 1;
            zMod = 1;
        }

        return new BlockPos(xMod, yMod, zMod);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TilePipe();
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7)).withProperty(ISMULTIBLOCK, Boolean.valueOf((meta & 8) > 0));
    }

    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(FACING).getIndex();

        if (state.getValue(ISMULTIBLOCK).booleanValue())
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{FACING, ISMULTIBLOCK, CONNECTEDRIGHT, CONNECTEDDOWN, REARBLOCK});
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        TileEntity tileentity = world instanceof ChunkCache ? ((ChunkCache)world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos);

        if(tileentity instanceof TilePipe)
        {
            TilePipe tilePipe = (TilePipe) tileentity;

            return state.withProperty(CONNECTEDRIGHT, tilePipe.isConnectedRight()).withProperty(CONNECTEDDOWN, tilePipe.isConnectedDown()).withProperty(REARBLOCK, tilePipe.isRearBlock());
        }

        return state;
    }
}
