package io.github.Theray070696.mario2.block;

import io.github.Theray070696.mario2.audio.SoundHandler;
import io.github.Theray070696.mario2.block.tile.TilePipe;
import io.github.Theray070696.mario2.item.ItemPipeLink;
import io.github.Theray070696.mario2.util.MarioTeleporter;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

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
    private int inPipeTime;

    public BlockPipe()
    {
        super(Material.IRON);

        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ISMULTIBLOCK, false).withProperty
                (CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, false));

        this.setTranslationKey("marioBlockPipe");
        this.setHardness(1.5f);
    }

    public static boolean isMultiBlock(World world, BlockPos basePos, BlockPos modPos)
    {
        if(world.getBlockState(basePos).getBlock() instanceof BlockPipe && world.getBlockState(basePos.add(0, modPos.getY(), 0)).getBlock()
                instanceof BlockPipe && world.getBlockState(basePos.add(modPos.getX(), 0, 0)).getBlock() instanceof BlockPipe && world
                .getBlockState(basePos.add(modPos.getX(), modPos.getY(), 0)).getBlock() instanceof BlockPipe && world.getBlockState(basePos.add(0,
                0, modPos.getZ())).getBlock() instanceof BlockPipe && world.getBlockState(basePos.add(modPos.getX(), 0, modPos.getZ())).getBlock()
                instanceof BlockPipe && world.getBlockState(basePos.add(0, modPos.getY(), modPos.getZ())).getBlock() instanceof BlockPipe && world
                .getBlockState(basePos.add(modPos)).getBlock() instanceof BlockPipe)
        {
            return true;
        }

        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos blockPos)
    {
        EnumFacing side = blockState.getValue(FACING);
        boolean rearBlock = getActualState(blockState, world, blockPos).getValue(REARBLOCK);
        boolean isMultiblock = blockState.getValue(ISMULTIBLOCK);

        if(isMultiblock)
        {
            if(side == EnumFacing.UP && !rearBlock)
            {
                return new AxisAlignedBB(0.0d, 0.0d, 0.0d, 1.0d, 0.625d, 1.0d);
            } else if(side == EnumFacing.DOWN && !rearBlock)
            {
                return new AxisAlignedBB(0.0d, 0.375d, 0.0d, 1.0d, 1.0d, 1.0d);
            } else if(side == EnumFacing.EAST && !rearBlock)
            {
                return new AxisAlignedBB(0.0d, 0.0d, 0.0d, 0.625d, 1.0d, 1.0d);
            } else if(side == EnumFacing.WEST && !rearBlock)
            {
                return new AxisAlignedBB(0.325d, 0.0d, 0.0d, 1.0d, 1.0d, 1.0d);
            } else if(side == EnumFacing.NORTH && !rearBlock)
            {
                return new AxisAlignedBB(0.0d, 0.0d, 0.375d, 1.0d, 1.0d, 1.0d);
            } else if(side == EnumFacing.SOUTH && !rearBlock)
            {
                return new AxisAlignedBB(0.0d, 0.0d, 0.0d, 1.0d, 1.0d, 0.625d);
            }
        }

        return new AxisAlignedBB(0.0d, 0.0d, 0.0d, 1.0d, 1.0d, 1.0d);
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
    public void onEntityCollision(World world, BlockPos blockPos, IBlockState blockState, Entity entity)
    {
        if(!world.isRemote)
        {
            inPipeTime++;

            if(inPipeTime >= 20)
            {
                if(world.getTileEntity(blockPos) instanceof TilePipe)
                {
                    if((blockState.getValue(FACING) == EnumFacing.UP && ((entity instanceof EntityPlayer && entity.isSneaking()) || !(entity
                            instanceof EntityPlayer))) || blockState.getValue(FACING) != EnumFacing.UP)
                    {
                        TilePipe tilePipe = (TilePipe) world.getTileEntity(blockPos);

                        if(tilePipe.hasMaster() && !tilePipe.getOtherPipePos().equals(BlockPos.ORIGIN))
                        {
                            BlockPos teleportDestinationPos = tilePipe.getOtherPipePos();
                            int teleportDestinationDimension = tilePipe.getOtherPipeDimension();

                            if(world.getMinecraftServer() != null)
                            {
                                World otherWorld = world.getMinecraftServer().getWorld(teleportDestinationDimension);

                                if(otherWorld.getBlockState(teleportDestinationPos).getBlock() instanceof BlockPipe && otherWorld.getTileEntity
                                        (teleportDestinationPos) instanceof TilePipe)
                                {
                                    // Offset teleport to prevent infinite loop
                                    teleportDestinationPos = teleportDestinationPos.offset(otherWorld.getBlockState(teleportDestinationPos)
                                            .getValue(FACING), 2);

                                    // Are the Pipes in different Dimensions?
                                    if(teleportDestinationDimension != world.provider.getDimension())
                                    {
                                        // Change dimension
                                        if(entity instanceof EntityPlayerMP)
                                        {
                                            // Players
                                            world.getMinecraftServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP) entity,
                                                    teleportDestinationDimension, new MarioTeleporter((WorldServer) otherWorld));
                                            entity.setSneaking(false);
                                        } else if(entity instanceof EntityItem)
                                        {
                                            // Items
                                            ItemStack stack = ((EntityItem) entity).getItem().copy();

                                            EntityItem entityItem = new EntityItem(otherWorld, teleportDestinationPos.getX(),
                                                    teleportDestinationPos.getY(), teleportDestinationPos.getZ(), stack);

                                            otherWorld.spawnEntity(entityItem);
                                            entity.setDead();
                                        } else
                                        {
                                            // Other Entities
                                            world.getMinecraftServer().getPlayerList().transferEntityToWorld(entity, world.provider.getDimension(),
                                                    (WorldServer) world, (WorldServer) otherWorld, new MarioTeleporter((WorldServer) otherWorld));
                                        }
                                    }

                                    if((!(entity instanceof EntityItem) && teleportDestinationDimension != world.provider.getDimension()) ||
                                            teleportDestinationDimension == world.provider.getDimension())
                                    {
                                        // Move the Entity to the correct coordinates
                                        entity.setPositionAndUpdate(teleportDestinationPos.getX(), teleportDestinationPos.getY(),
                                                teleportDestinationPos.getZ());
                                    }

                                    // Play sound
                                    world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundHandler.pipe, SoundCategory
                                            .BLOCKS, 1.0f, 1.0f);
                                    otherWorld.playSound(null, teleportDestinationPos.getX(), teleportDestinationPos.getY(), teleportDestinationPos
                                            .getZ(), SoundHandler.pipe, SoundCategory.BLOCKS, 1.0f, 1.0f);
                                }
                            }
                        }
                    }
                }

                inPipeTime = 0;
            }
        }
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

            world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                    (CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, false), 2);

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setIsMaster(isMultiBlock);
            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(new BlockPos(x, y, z));

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
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                        (CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, false), 2);
            } else if(side == EnumFacing.UP || side == EnumFacing.DOWN)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                        (CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, true), 2);
            }

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(new BlockPos(x, y, z));

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
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                        (CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, false), 2);
            } else if(side == EnumFacing.EAST || side == EnumFacing.WEST)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                        (CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, true), 2);
            }

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(new BlockPos(x, y, z));

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
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                        (CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, true), 2);
            } else if(side == EnumFacing.DOWN || side == EnumFacing.UP)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                        (CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, true), 2);
            } else if(side == EnumFacing.SOUTH || side == EnumFacing.NORTH)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                        (CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, false), 2);
            }

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(new BlockPos(x, y, z));

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
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                        (CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, false), 2);
            } else if(side == EnumFacing.WEST || side == EnumFacing.EAST)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                        (CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, false), 2);
            } else if(side == EnumFacing.NORTH || side == EnumFacing.SOUTH)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                        (CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, true), 2);
            }

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(new BlockPos(x, y, z));

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
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                        (CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, false), 2);
            } else if(side == EnumFacing.EAST || side == EnumFacing.WEST || side == EnumFacing.NORTH || side == EnumFacing.SOUTH)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                        (CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, true).withProperty(REARBLOCK, true), 2);
            }

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(new BlockPos(x, y, z));

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
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                        (CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, true), 2);
            } else if(side == EnumFacing.UP || side == EnumFacing.DOWN)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                        (CONNECTEDRIGHT, true).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, true), 2);
            } else if(side == EnumFacing.WEST || side == EnumFacing.EAST)
            {
                world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                        (CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, false), 2);
            }

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(new BlockPos(x, y, z));

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

            world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, side).withProperty(ISMULTIBLOCK, isMultiBlock).withProperty
                    (CONNECTEDRIGHT, false).withProperty(CONNECTEDDOWN, false).withProperty(REARBLOCK, true), 2);

            tilePipe = (TilePipe) world.getTileEntity(pos);

            tilePipe.reset();

            tilePipe.setHasMaster(isMultiBlock);
            if(isMultiBlock)
            {
                tilePipe.setMasterCoords(new BlockPos(x, y, z));

                tilePipe.setConnectedRight(false);
                tilePipe.setConnectedDown(false);
                tilePipe.setRearBlock(true);
            }

            ((BlockPipe) world.getBlockState(pos).getBlock()).shouldBreakBlock = true;

            tilePipe.validate();
            world.setTileEntity(pos, tilePipe);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX,
            float hitY, float hitZ)
    {
        if(player.isSneaking() || (!state.getValue(ISMULTIBLOCK) && (!player.getHeldItemMainhand().isEmpty() || !player.getHeldItemOffhand()
                .isEmpty())) || (state.getValue(ISMULTIBLOCK) && (player.getHeldItemMainhand().getItem() instanceof ItemPipeLink || player
                .getHeldItemOffhand().getItem() instanceof ItemPipeLink)))
        {
            return false;
        } else
        {
            if(!world.isRemote)
            {
                if(side == state.getValue(FACING) && state.getValue(ISMULTIBLOCK))
                {
                    return false;
                }

                if(isMultiBlock(world, pos, getPositionModifiers(side)))
                {
                    setMultiBlockFacing(world, pos.getX(), pos.getY(), pos.getZ(), side, true);
                }
            }
        }

        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState blockState)
    {
        if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TilePipe)
        {
            EnumFacing side = blockState.getValue(FACING);

            TilePipe pipe = (TilePipe) world.getTileEntity(pos);
            if(pipe.isMaster())
            {
                BlockPos otherPipePos = pipe.getOtherPipePos();
                int otherPipeDim = pipe.getOtherPipeDimension();

                if(otherPipeDim != world.provider.getDimension())
                {
                    if(world.getMinecraftServer() != null)
                    {
                        World otherPipeWorld = world.getMinecraftServer().getWorld(otherPipeDim);
                        if(otherPipeWorld.getTileEntity(otherPipePos) != null && otherPipeWorld.getTileEntity(otherPipePos) instanceof TilePipe)
                        {
                            TilePipe otherPipe = (TilePipe) otherPipeWorld.getTileEntity(otherPipePos);

                            otherPipe.setOtherPipePos(BlockPos.ORIGIN, 0);
                        }
                    }
                } else
                {
                    if(world.getTileEntity(otherPipePos) != null && world.getTileEntity(otherPipePos) instanceof TilePipe)
                    {
                        TilePipe otherPipe = (TilePipe) world.getTileEntity(otherPipePos);

                        otherPipe.setOtherPipePos(BlockPos.ORIGIN, 0);
                    }
                }

                setMultiBlockFacing(world, pos.getX(), pos.getY(), pos.getZ(), side, false);
            } else if(pipe.hasMaster())
            {
                pos = pipe.getMasterPos();
                if(world.getTileEntity(pos) instanceof TilePipe)
                {
                    pipe = (TilePipe) world.getTileEntity(pos);
                    if(pipe.isMaster())
                    {
                        if(!isMultiBlock(world, pos, getPositionModifiers(side)))
                        {
                            BlockPos otherPipePos = pipe.getOtherPipePos();
                            int otherPipeDim = pipe.getOtherPipeDimension();

                            if(otherPipeDim != world.provider.getDimension())
                            {
                                if(world.getMinecraftServer() != null)
                                {
                                    World otherPipeWorld = world.getMinecraftServer().getWorld(otherPipeDim);
                                    if(otherPipeWorld.getTileEntity(otherPipePos) != null && otherPipeWorld.getTileEntity(otherPipePos) instanceof
                                            TilePipe)
                                    {
                                        TilePipe otherPipe = (TilePipe) otherPipeWorld.getTileEntity(otherPipePos);

                                        otherPipe.setOtherPipePos(BlockPos.ORIGIN, 0);
                                    }
                                }
                            } else
                            {
                                if(world.getTileEntity(otherPipePos) != null && world.getTileEntity(otherPipePos) instanceof TilePipe)
                                {
                                    TilePipe otherPipe = (TilePipe) world.getTileEntity(otherPipePos);

                                    otherPipe.setOtherPipePos(BlockPos.ORIGIN, 0);
                                }
                            }

                            setMultiBlockFacing(world, pos.getX(), pos.getY(), pos.getZ(), side, false);
                        }
                    }
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
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta & 7)).withProperty(ISMULTIBLOCK, (meta & 8) > 0);
    }

    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(FACING).getIndex();

        if(state.getValue(ISMULTIBLOCK))
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, ISMULTIBLOCK, CONNECTEDRIGHT, CONNECTEDDOWN, REARBLOCK);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        TileEntity tileentity = world instanceof ChunkCache ? ((ChunkCache) world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world
                .getTileEntity(pos);

        if(tileentity instanceof TilePipe)
        {
            TilePipe tilePipe = (TilePipe) tileentity;

            return state.withProperty(CONNECTEDRIGHT, tilePipe.isConnectedRight()).withProperty(CONNECTEDDOWN, tilePipe.isConnectedDown())
                    .withProperty(REARBLOCK, tilePipe.isRearBlock());
        }

        return state;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add("Place in a 2x2x2 structure, then right click on the top-left block to form a multiblock.");
    }
}
