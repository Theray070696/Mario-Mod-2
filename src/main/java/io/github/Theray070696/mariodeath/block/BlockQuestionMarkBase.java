package io.github.Theray070696.mariodeath.block;

import io.github.Theray070696.mariodeath.audio.SoundHandler;
import io.github.Theray070696.mariodeath.block.tile.TileQuestionMark;
import io.github.Theray070696.mariodeath.item.*;
import io.github.Theray070696.mariodeath.lib.ItemsInQuestionMarks;
import io.github.Theray070696.mariodeath.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by Theray070696 on 8/29/2015.
 */
public class BlockQuestionMarkBase extends BlockMario implements ITileEntityProvider
{
    private static boolean keepInventory;

    public BlockQuestionMarkBase()
    {
        this(true);
    }

    public BlockQuestionMarkBase(boolean addToCreativeTab)
    {
        super(addToCreativeTab);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState blockState, Entity entity)
    {
        if(entity instanceof EntityPlayer)
        {
            if(this instanceof IFilledQBlock)
            {
                takeItemOutOfQBlock(world, blockPos, null);
            } else
            {
                if(this instanceof SMBQBlock)
                {
                    world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundHandler.smbEmptyBlockHit, SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else if(this instanceof SMB3QBlock)
                {
                    world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundHandler.smbEmptyBlockHit, SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else if(this instanceof SMWQBlock)
                {
                    world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundHandler.smwEmptyBlockHit, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World world, BlockPos pos)
    {
        return new AxisAlignedBB(0.0D, 0.25D, 0.0D, 1.0D, 1.0D, 1.0D);
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
    {
        return FULL_BLOCK_AABB.offset(pos);
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileQuestionMark();
    }

    public void updateQuestionMarkState(int itemInBlock, World world, BlockPos pos, TileQuestionMark tileQuestionMark)
    {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();

        keepInventory = true;

        if(itemInBlock != ItemsInQuestionMarks.ITEM_NOTHING)
        {
            tileQuestionMark.setItemInBlock(itemInBlock);

            if(block instanceof BlockInvisibleBlockEmpty)
            {
                world.setBlockState(pos, ModBlocks.blockInvisibleBlock.getDefaultState(), 3);
            } else if(block instanceof BlockQuestionMarkEmpty)
            {
                world.setBlockState(pos, ModBlocks.blockQuestionMark.getDefaultState(), 3);
            } else if(block instanceof BlockInvisibleBlockEmptySMB)
            {
                world.setBlockState(pos, ModBlocks.blockInvisibleBlockSMB.getDefaultState(), 3);
            } else if(block instanceof BlockInvisibleBlockEmptySMB3)
            {
                world.setBlockState(pos, ModBlocks.blockInvisibleBlockSMB3.getDefaultState(), 3);
            } else if(block instanceof BlockQuestionMarkEmptyUndergroundSMB)
            {
                world.setBlockState(pos, ModBlocks.blockQuestionMarkUndergroundSMB.getDefaultState(), 3);
            } else if(block instanceof BlockQuestionMarkEmptySMB)
            {
                world.setBlockState(pos, ModBlocks.blockQuestionMarkSMB.getDefaultState(), 3);
            } else if(block instanceof BlockQuestionMarkEmptySMB3)
            {
                world.setBlockState(pos, ModBlocks.blockQuestionMarkSMB3.getDefaultState(), 3);
            }

            keepInventory = false;

            if(tileQuestionMark != null)
            {
                tileQuestionMark.validate();
                world.setTileEntity(pos, tileQuestionMark);
            }
        } else
        {
            tileQuestionMark.setItemInBlock(ItemsInQuestionMarks.ITEM_NOTHING);

            if(block instanceof BlockInvisibleBlock)
            {
                world.setBlockState(pos, ModBlocks.blockEmptyInvisibleBlock.getDefaultState(), 3);
            } else if(block instanceof BlockQuestionMark)
            {
                world.setBlockState(pos, ModBlocks.blockEmptyQuestionMark.getDefaultState(), 3);
            } else if(block instanceof BlockInvisibleBlockSMB)
            {
                world.setBlockState(pos, ModBlocks.blockEmptyInvisibleBlockSMB.getDefaultState(), 3);
            } else if(block instanceof BlockInvisibleBlockSMB3)
            {
                world.setBlockState(pos, ModBlocks.blockEmptyInvisibleBlockSMB3.getDefaultState(), 3);
            } else if(block instanceof BlockQuestionMarkUndergroundSMB)
            {
                world.setBlockState(pos, ModBlocks.blockEmptyQuestionMarkUndergroundSMB.getDefaultState(), 3);
            } else if(block instanceof BlockQuestionMarkSMB)
            {
                world.setBlockState(pos, ModBlocks.blockEmptyQuestionMarkSMB.getDefaultState(), 3);
            } else if(block instanceof BlockQuestionMarkSMB3)
            {
                world.setBlockState(pos, ModBlocks.blockEmptyQuestionMarkSMB3.getDefaultState(), 3);
            }

            keepInventory = false;

            if(tileQuestionMark != null)
            {
                tileQuestionMark.validate();
                world.setTileEntity(pos, tileQuestionMark);
            }
        }

        keepInventory = false;
    }

    private void takeItemOutOfQBlock(World world, BlockPos pos, EntityPlayer entityPlayer)
    {
        if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileQuestionMark)
        {
            TileQuestionMark tileQuestionMark = (TileQuestionMark) world.getTileEntity(pos);

            int itemInBlock = tileQuestionMark.getItemInBlock();
            ItemStack newItemStack = null;

            if(itemInBlock == ItemsInQuestionMarks.ITEM_COIN)
            {
                if(this instanceof SMBQBlock)
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbCoin, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    newItemStack = new ItemStack(ModItems.itemMarioCoin);
                } else if(this instanceof SMWQBlock)
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smwCoin, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    newItemStack = new ItemStack(ModItems.itemMarioCoin, 1, 1);
                } else if(this instanceof SMB3QBlock)
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbCoin, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    newItemStack = new ItemStack(ModItems.itemMarioCoin, 1, 2);
                }
            } else if(itemInBlock == ItemsInQuestionMarks.ITEM_MUSHROOM)
            {
                if(this instanceof SMBQBlock)
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    newItemStack = new ItemStack(ModItems.itemMarioMushroom);
                } else if(this instanceof SMWQBlock)
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smwBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    newItemStack = new ItemStack(ModItems.itemMarioMushroom, 1, 1);
                } else if(this instanceof SMB3QBlock)
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    newItemStack = new ItemStack(ModItems.itemMarioMushroom, 1, 2);
                }
            } else if(itemInBlock == ItemsInQuestionMarks.ITEM_1UP)
            {
                if(this instanceof SMBQBlock)
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    newItemStack = new ItemStack(ModItems.item1Up);
                } else if(this instanceof SMWQBlock)
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smwBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    newItemStack = new ItemStack(ModItems.item1Up, 1, 1);
                } else if(this instanceof SMB3QBlock)
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    newItemStack = new ItemStack(ModItems.item1Up, 1, 2);
                }
            } else if(itemInBlock == ItemsInQuestionMarks.ITEM_CAPE)
            {
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smwBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                newItemStack = new ItemStack(ModItems.itemCape);
            } else if(itemInBlock == ItemsInQuestionMarks.ITEM_FIRE_FLOWER)
            {
                if(this instanceof SMBQBlock)
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else if(this instanceof SMWQBlock)
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smwBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else if(this instanceof SMB3QBlock)
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                newItemStack = new ItemStack(ModItems.itemFireFlower);
            } else if(itemInBlock == ItemsInQuestionMarks.ITEM_STAR_MAN)
            {
                newItemStack = new ItemStack(ModItems.itemStarMan);

                if(this instanceof SMBQBlock)
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else if(this instanceof SMWQBlock)
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smwBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else if(this instanceof SMB3QBlock)
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }

            if(newItemStack != null)
            {
                if(entityPlayer != null)
                {
                    EntityItem entityItem = entityPlayer.entityDropItem(newItemStack, 0.0f);

                    entityItem.setNoPickupDelay();
                } else if(entityPlayer == null)
                {
                    Random rand = new Random();

                    float dY = rand.nextFloat() * 0.8F + 0.1F;

                    EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY() + dY, pos.getZ(), newItemStack.copy());

                    float factor = 0.05F;
                    entityItem.motionX = rand.nextGaussian() * factor;
                    entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                    entityItem.motionZ = rand.nextGaussian() * factor;
                    world.spawnEntityInWorld(entityItem);
                }

                this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_NOTHING, world, pos, tileQuestionMark);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking())
        {
            return false;
        } else
        {
            if(!world.isRemote)
            {
                if(world.getTileEntity(pos) instanceof TileQuestionMark)
                {
                    TileQuestionMark tileQuestionMark = (TileQuestionMark) world.getTileEntity(pos);

                    if(heldItem == null && tileQuestionMark.getItemInBlock() != ItemsInQuestionMarks.ITEM_NOTHING)
                    {
                        takeItemOutOfQBlock(world, pos, player);
                    } else if(heldItem != null && tileQuestionMark.getItemInBlock() == ItemsInQuestionMarks.ITEM_NOTHING)
                    {
                        Item item = heldItem.getItem();
                        int blockType = 0;

                        if(this instanceof SMWQBlock)
                        {
                            blockType = 1;
                        } else if(this instanceof SMB3QBlock)
                        {
                            blockType = 2;
                        }

                        if(item instanceof ItemCoin)
                        {
                            if(heldItem.getItemDamage() == blockType)
                            {
                                heldItem.stackSize--;
                                this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_COIN, world, pos, tileQuestionMark);
                            }
                        } else if(item instanceof ItemMushroom)
                        {
                            if(heldItem.getItemDamage() == blockType)
                            {
                                heldItem.stackSize--;
                                this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_MUSHROOM, world, pos, tileQuestionMark);
                            }
                        } else if(item instanceof Item1Up)
                        {
                            if(heldItem.getItemDamage() == blockType)
                            {
                                heldItem.stackSize--;
                                this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_1UP, world, pos, tileQuestionMark);
                            }
                        } else if(item instanceof ItemCape)
                        {
                            if(blockType == 1)
                            {
                                heldItem.stackSize--;
                                this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_CAPE, world, pos, tileQuestionMark);
                            }
                        } else if(item instanceof ItemFireFlower)
                        {
                            heldItem.stackSize--;
                            this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_FIRE_FLOWER, world, pos, tileQuestionMark);
                        } else if(item instanceof ItemStarMan)
                        {
                            heldItem.stackSize--;
                            this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_STAR_MAN, world, pos, tileQuestionMark);
                        }
                    }
                }
            }

            return true;
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if(this instanceof BlockInvisibleBlock)
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyInvisibleBlock);
        } else if(this instanceof BlockQuestionMark)
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyQuestionMark);
        } else if(this instanceof BlockInvisibleBlockSMB)
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyInvisibleBlockSMB);
        } else if(this instanceof BlockInvisibleBlockSMB3)
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyInvisibleBlockSMB3);
        } else if(this instanceof BlockQuestionMarkUndergroundSMB)
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyQuestionMarkUndergroundSMB);
        } else if(this instanceof BlockQuestionMarkSMB)
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyQuestionMarkSMB);
        } else if(this instanceof BlockQuestionMarkSMB3)
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyQuestionMarkSMB3);
        } else
        {
            return super.getItemDropped(state, rand, fortune);
        }
    }

    @Override
    public void breakBlock(World world, BlockPos blockPos, IBlockState blockState)
    {
        if(!keepInventory)
        {
            if(this instanceof IFilledQBlock)
            {
                dropInventory(world, blockPos);
            }

            super.breakBlock(world, blockPos, blockState);
        }
    }
    
    protected void dropInventory(World world, BlockPos pos)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if(!(tileEntity instanceof TileQuestionMark))
        {
            return;
        }
    
        TileQuestionMark questionMark = (TileQuestionMark) tileEntity;
        int itemInBlock = questionMark.getItemInBlock();
        ItemStack itemStack = null;
    
        if(itemInBlock == ItemsInQuestionMarks.ITEM_COIN)
        {
            if(this instanceof SMBQBlock)
            {
                itemStack = new ItemStack(ModItems.itemMarioCoin);
            } else if(this instanceof SMWQBlock)
            {
                itemStack = new ItemStack(ModItems.itemMarioCoin, 1, 1);
            } else if(this instanceof SMB3QBlock)
            {
                itemStack = new ItemStack(ModItems.itemMarioCoin, 1, 2);
            }
        } else if(itemInBlock == ItemsInQuestionMarks.ITEM_MUSHROOM)
        {
            if(this instanceof SMBQBlock)
            {
                itemStack = new ItemStack(ModItems.itemMarioMushroom);
            } else if(this instanceof SMWQBlock)
            {
                itemStack = new ItemStack(ModItems.itemMarioMushroom, 1, 1);
            } else if(this instanceof SMB3QBlock)
            {
                itemStack = new ItemStack(ModItems.itemMarioMushroom, 1, 2);
            }
        } else if(itemInBlock == ItemsInQuestionMarks.ITEM_1UP)
        {
            if(this instanceof SMBQBlock)
            {
                itemStack = new ItemStack(ModItems.item1Up);
            } else if(this instanceof SMWQBlock)
            {
                itemStack = new ItemStack(ModItems.item1Up, 1, 1);
            } else if(this instanceof SMB3QBlock)
            {
                itemStack = new ItemStack(ModItems.item1Up, 1, 2);
            }
        } else if(itemInBlock == ItemsInQuestionMarks.ITEM_CAPE)
        {
            itemStack = new ItemStack(ModItems.itemCape);
        } else if(itemInBlock == ItemsInQuestionMarks.ITEM_FIRE_FLOWER)
        {
            itemStack = new ItemStack(ModItems.itemFireFlower);
        } else if(itemInBlock == ItemsInQuestionMarks.ITEM_STAR_MAN)
        {
            itemStack = new ItemStack(ModItems.itemStarMan);
        }
        
        if(itemStack != null && itemStack.stackSize > 0)
        {
            Random rand = new Random();
            
            float dX = rand.nextFloat() * 0.8F + 0.1F;
            float dY = rand.nextFloat() * 0.8F + 0.1F;
            float dZ = rand.nextFloat() * 0.8F + 0.1F;
            
            EntityItem entityItem = new EntityItem(world, pos.getX() + dX, pos.getY() + dY, pos.getZ() + dZ, itemStack.copy());
            
            float factor = 0.05F;
            entityItem.motionX = rand.nextGaussian() * factor;
            entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
            entityItem.motionZ = rand.nextGaussian() * factor;
            world.spawnEntityInWorld(entityItem);
        }
    }
}