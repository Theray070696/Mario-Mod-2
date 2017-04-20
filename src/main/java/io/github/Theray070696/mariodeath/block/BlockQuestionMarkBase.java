package io.github.Theray070696.mariodeath.block;

import io.github.Theray070696.mariodeath.block.tile.TileQuestionMark;
import io.github.Theray070696.mariodeath.item.*;
import io.github.Theray070696.mariodeath.lib.ItemsInQuestionMarks;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Theray on 8/29/2015.
 */
public class BlockQuestionMarkBase extends BlockMario implements ITileEntityProvider
{
    public BlockQuestionMarkBase()
    {
        super();
    }

    public BlockQuestionMarkBase(boolean addToCreativeTab)
    {
        super(addToCreativeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileQuestionMark();
    }

    public void updateQuestionMarkState(int itemInBlock, World world, int x, int y, int z, TileQuestionMark tileQuestionMark)
    {
        Block block = world.getBlock(x, y, z);

        if(itemInBlock != ItemsInQuestionMarks.ITEM_NOTHING)
        {
            tileQuestionMark.setItemInBlock(itemInBlock);

            if(block instanceof BlockInvisibleBlockEmpty)
            {
                world.setBlock(x, y, z, ModBlocks.blockInvisibleBlock);
            } else if(block instanceof BlockQuestionMarkEmpty)
            {
                world.setBlock(x, y, z, ModBlocks.blockQuestionMark);
            } else if(block instanceof BlockInvisibleBlockEmptySMB)
            {
                world.setBlock(x, y, z, ModBlocks.blockInvisibleBlockSMB);
            } else  if(block instanceof BlockQuestionMarkEmptyUndergroundSMB)
            {
                world.setBlock(x, y, z, ModBlocks.blockQuestionMarkUndergroundSMB);
            } else if(block instanceof BlockQuestionMarkEmptySMB)
            {
                world.setBlock(x, y, z, ModBlocks.blockQuestionMarkSMB);
            }

            tileQuestionMark.validate();
            world.setTileEntity(x, y, z, tileQuestionMark);
        } else
        {
            tileQuestionMark.setItemInBlock(ItemsInQuestionMarks.ITEM_NOTHING);

            if(block instanceof BlockInvisibleBlock)
            {
                world.setBlock(x, y, z, ModBlocks.blockEmptyInvisibleBlock);
            } else if(block instanceof BlockQuestionMark)
            {
                world.setBlock(x, y, z, ModBlocks.blockEmptyQuestionMark);
            } else if(block instanceof BlockInvisibleBlockSMB)
            {
                world.setBlock(x, y, z, ModBlocks.blockEmptyInvisibleBlockSMB);
            } else  if(block instanceof BlockQuestionMarkUndergroundSMB)
            {
                world.setBlock(x, y, z, ModBlocks.blockEmptyQuestionMarkUndergroundSMB);
            } else if(block instanceof BlockQuestionMarkSMB)
            {
                world.setBlock(x, y, z, ModBlocks.blockEmptyQuestionMarkSMB);
            }

            tileQuestionMark.validate();
            world.setTileEntity(x, y, z, tileQuestionMark);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if(player.isSneaking())
        {
            return false;
        } else
        {
            if(!world.isRemote)
            {
                if(world.getTileEntity(x, y, z) instanceof TileQuestionMark)
                {
                    TileQuestionMark tileQuestionMark = (TileQuestionMark) world.getTileEntity(x, y, z);
                    ItemStack currentEquippedItem = player.getCurrentEquippedItem();

                    if(currentEquippedItem == null && tileQuestionMark.getItemInBlock() != ItemsInQuestionMarks.ITEM_NOTHING)
                    {
                        int itemInBlock = tileQuestionMark.getItemInBlock();
                        ItemStack newItemStack = null;

                        if(itemInBlock == ItemsInQuestionMarks.ITEM_COIN)
                        {
                            if(this instanceof SMBQBlock)
                            {
                                newItemStack = new ItemStack(ModItems.itemMarioCoin);
                            } else
                            {
                                newItemStack = new ItemStack(ModItems.itemMarioCoin, 1, 1);
                            }
                        } else if(itemInBlock == ItemsInQuestionMarks.ITEM_MUSHROOM)
                        {
                            if(this instanceof SMBQBlock)
                            {
                                newItemStack = new ItemStack(ModItems.itemMarioMushroom);
                            } else
                            {
                                newItemStack = new ItemStack(ModItems.itemMarioMushroom, 1, 1);
                            }
                        } else if(itemInBlock == ItemsInQuestionMarks.ITEM_1UP)
                        {
                            if(this instanceof SMBQBlock)
                            {
                                newItemStack = new ItemStack(ModItems.item1Up);
                            } else
                            {
                                newItemStack = new ItemStack(ModItems.item1Up, 1, 1);
                            }
                        } else if(itemInBlock == ItemsInQuestionMarks.ITEM_CAPE)
                        {
                            newItemStack = new ItemStack(ModItems.itemCape);
                        } else if(itemInBlock == ItemsInQuestionMarks.ITEM_FIRE_FLOWER)
                        {
                            newItemStack = new ItemStack(ModItems.itemFireFlower);
                        } else if(itemInBlock == ItemsInQuestionMarks.ITEM_STAR_MAN)
                        {
                            newItemStack = new ItemStack(ModItems.itemStarMan);
                        }

                        if(newItemStack != null)
                        {
                            EntityItem entityItem = player.entityDropItem(newItemStack, 0.0f);

                            if(entityItem != null)
                            {
                                entityItem.delayBeforeCanPickup = 0;

                                this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_NOTHING, world, x, y, z, tileQuestionMark);
                            }
                        }
                    } else if(currentEquippedItem != null && tileQuestionMark.getItemInBlock() == ItemsInQuestionMarks.ITEM_NOTHING)
                    {
                        Item item = currentEquippedItem.getItem();
                        int blockType = 0;

                        if(this instanceof SMWQBlock)
                        {
                            blockType = 1;
                        }

                        if(item instanceof ItemCoin)
                        {
                            if(currentEquippedItem.getItemDamage() == blockType)
                            {
                                currentEquippedItem.stackSize--;
                                this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_COIN, world, x, y, z, tileQuestionMark);
                            }
                        } else if(item instanceof ItemMushroom)
                        {
                            if(currentEquippedItem.getItemDamage() == blockType)
                            {
                                currentEquippedItem.stackSize--;
                                this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_MUSHROOM, world, x, y, z, tileQuestionMark);
                            }
                        } else if(item instanceof Item1Up)
                        {
                            if(currentEquippedItem.getItemDamage() == blockType)
                            {
                                currentEquippedItem.stackSize--;
                                this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_1UP, world, x, y, z, tileQuestionMark);
                            }
                        } else if(item instanceof ItemCape)
                        {
                            if(blockType == 1)
                            {
                                currentEquippedItem.stackSize--;
                                this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_CAPE, world, x, y, z, tileQuestionMark);
                            }
                        } else if(item instanceof ItemFireFlower)
                        {
                            currentEquippedItem.stackSize--;
                            this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_FIRE_FLOWER, world, x, y, z, tileQuestionMark);
                        } else if(item instanceof ItemStarMan)
                        {
                            currentEquippedItem.stackSize--;
                            this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_STAR_MAN, world, x, y, z, tileQuestionMark);
                        }
                    }
                }
            }

            return true;
        }
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random rand, int p_149650_3_)
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
        } else if(this instanceof BlockQuestionMarkUndergroundSMB)
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyQuestionMarkUndergroundSMB);
        } else if(this instanceof BlockQuestionMarkSMB)
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyQuestionMarkSMB);
        } else
        {
            return super.getItemDropped(p_149650_1_, rand, p_149650_3_);
        }
    }
    
    protected void dropInventory(World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        
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
            } else
            {
                itemStack = new ItemStack(ModItems.itemMarioCoin, 1, 1);
            }
        } else if(itemInBlock == ItemsInQuestionMarks.ITEM_MUSHROOM)
        {
            if(this instanceof SMBQBlock)
            {
                itemStack = new ItemStack(ModItems.itemMarioMushroom);
            } else
            {
                itemStack = new ItemStack(ModItems.itemMarioMushroom, 1, 1);
            }
        } else if(itemInBlock == ItemsInQuestionMarks.ITEM_1UP)
        {
            if(this instanceof SMBQBlock)
            {
                itemStack = new ItemStack(ModItems.item1Up);
            } else
            {
                itemStack = new ItemStack(ModItems.item1Up, 1, 1);
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
            
            EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, itemStack.copy());
            
            float factor = 0.05F;
            entityItem.motionX = rand.nextGaussian() * factor;
            entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
            entityItem.motionZ = rand.nextGaussian() * factor;
            world.spawnEntityInWorld(entityItem);
        }
    }
}