package io.github.Theray070696.mario2.block;

import io.github.Theray070696.mario2.audio.SoundHandler;
import io.github.Theray070696.mario2.block.tile.TileQuestionMark;
import io.github.Theray070696.mario2.core.EventHandler;
import io.github.Theray070696.mario2.item.*;
import io.github.Theray070696.mario2.lib.ItemsInQuestionMarks;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
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
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by Theray070696 on 8/29/2015.
 */
public abstract class BlockQuestionMarkBase extends BlockMario implements ITileEntityProvider
{
    private static boolean keepInventory; // Whether or not we should dropInventory when this block is broken.
    private EnumBlockType blockType;

    public BlockQuestionMarkBase(EnumBlockType blockType)
    {
        this(true, blockType);
    }

    public BlockQuestionMarkBase(boolean addToCreativeTab, EnumBlockType blockType)
    {
        this(Material.ROCK, addToCreativeTab, blockType);
    }

    public BlockQuestionMarkBase(Material material, boolean addToCreativeTab, EnumBlockType blockType)
    {
        super(material, addToCreativeTab);

        this.setHardness(1.5F);
        this.blockType = blockType;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState blockState, Entity entity)
    {
        entity.motionY = -0.25f; // Send the entity back down.

        if(entity instanceof EntityPlayer) // If the entity that hit the bottom was a player...
        {
            if(this instanceof BlockQuestionMark) // If the block has an item in it...
            {
                takeItemOutOfQBlock(world, blockPos, null); // Take the item out.
            } else // Otherwise...
            {
                EntityPlayer player = (EntityPlayer) entity; // Get player for use in cooldown stuff.

                if(EventHandler.getSoundCooldown(player) == 0) // Make sure it's been enough time to play sounds again.
                {
                    if(blockType.equals(EnumBlockType.SMB) || blockType.equals(EnumBlockType.SMB_INVISIBLE) || blockType.equals(EnumBlockType
                            .SMB_UNDERGROUND) || blockType.equals(EnumBlockType.SMB_CASTLE) || blockType.equals(EnumBlockType.SMB3) || blockType
                            .equals(EnumBlockType.SMB3_INVISIBLE)) // If it is from Mario 1 or Mario 3...
                    {
                        world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundHandler.smbEmptyBlockHit, SoundCategory
                                .BLOCKS, 1.0F, 1.0F); // Play this sound.
                        EventHandler.setSoundCooldown(player, 1); // Don't spam sounds.
                    } else if(blockType.equals(EnumBlockType.SMW) || blockType.equals(EnumBlockType.SMW_INVISIBLE)) // If it is from Mario
                    // World...
                    {
                        world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundHandler.smwEmptyBlockHit, SoundCategory
                                .BLOCKS, 1.0F, 1.0F); // Play this sound.
                        EventHandler.setSoundCooldown(player, 1); // Don't spam sounds.
                    }
                }
            }
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World world, BlockPos pos)
    {
        return new AxisAlignedBB(0.0D, 0.25D, 0.0D, 1.0D, 1.0D, 1.0D); // You can jump into the bottom of the block.
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
        keepInventory = true; // Don't dropInventory.

        if(itemInBlock != ItemsInQuestionMarks.ITEM_NOTHING) // If we're putting something into the block...
        {
            tileQuestionMark.setItemInBlock(itemInBlock); // Save the item in the TileEntity.

            if(blockType.equals(EnumBlockType.SMB))
            {
                world.setBlockState(pos, ModBlocks.blockQuestionMarkSMB.getDefaultState(), 3); // Change it to the filled block.
            } else if(blockType.equals(EnumBlockType.SMB_INVISIBLE))
            {
                world.setBlockState(pos, ModBlocks.blockInvisibleBlockSMB.getDefaultState(), 3); // Change it to the filled block.
            } else if(blockType.equals(EnumBlockType.SMB_UNDERGROUND))
            {
                world.setBlockState(pos, ModBlocks.blockQuestionMarkUndergroundSMB.getDefaultState(), 3); // Change it to the filled block.
            } else if(blockType.equals(EnumBlockType.SMB_CASTLE))
            {
                world.setBlockState(pos, ModBlocks.blockQuestionMarkCastleSMB.getDefaultState(), 3); // Change it to the filled block.
            } else if(blockType.equals(EnumBlockType.SMB3))
            {
                world.setBlockState(pos, ModBlocks.blockQuestionMarkSMB3.getDefaultState(), 3); // Change it to the filled block.
            } else if(blockType.equals(EnumBlockType.SMB3_INVISIBLE))
            {
                world.setBlockState(pos, ModBlocks.blockInvisibleBlockSMB3.getDefaultState(), 3); // Change it to the filled block.
            } else if(blockType.equals(EnumBlockType.SMW))
            {
                world.setBlockState(pos, ModBlocks.blockQuestionMark.getDefaultState(), 3); // Change it to the filled block.
            } else if(blockType.equals(EnumBlockType.SMW_INVISIBLE))
            {
                world.setBlockState(pos, ModBlocks.blockInvisibleBlock.getDefaultState(), 3); // Change it to the filled block.
            }

            keepInventory = false; // We can dropInventory again.

            if(tileQuestionMark != null) // If the tile is not null...
            {
                tileQuestionMark.validate(); // Update it.
                world.setTileEntity(pos, tileQuestionMark); // Re-add the tile.
            }
        } else // Otherwise...
        {
            tileQuestionMark.setItemInBlock(ItemsInQuestionMarks.ITEM_NOTHING); // There is no longer an item in the block.

            if(blockType.equals(EnumBlockType.SMB))
            {
                world.setBlockState(pos, ModBlocks.blockEmptyQuestionMarkSMB.getDefaultState(), 3); // Change it to the empty block.
            } else if(blockType.equals(EnumBlockType.SMB_INVISIBLE))
            {
                world.setBlockState(pos, ModBlocks.blockEmptyInvisibleBlockSMB.getDefaultState(), 3); // Change it to the empty block.
            } else if(blockType.equals(EnumBlockType.SMB_UNDERGROUND))
            {
                world.setBlockState(pos, ModBlocks.blockEmptyQuestionMarkUndergroundSMB.getDefaultState(), 3); // Change it to the empty block.
            } else if(blockType.equals(EnumBlockType.SMB_CASTLE))
            {
                world.setBlockState(pos, ModBlocks.blockEmptyQuestionMarkCastleSMB.getDefaultState(), 3); // Change it to the filled block.
            } else if(blockType.equals(EnumBlockType.SMB3))
            {
                world.setBlockState(pos, ModBlocks.blockEmptyQuestionMarkSMB3.getDefaultState(), 3); // Change it to the empty block.
            } else if(blockType.equals(EnumBlockType.SMB3_INVISIBLE))
            {
                world.setBlockState(pos, ModBlocks.blockEmptyInvisibleBlockSMB3.getDefaultState(), 3); // Change it to the empty block.
            } else if(blockType.equals(EnumBlockType.SMW))
            {
                world.setBlockState(pos, ModBlocks.blockEmptyQuestionMark.getDefaultState(), 3); // Change it to the empty block.
            } else if(blockType.equals(EnumBlockType.SMW_INVISIBLE))
            {
                world.setBlockState(pos, ModBlocks.blockEmptyInvisibleBlock.getDefaultState(), 3); // Change it to the empty block.
            }

            keepInventory = false; // We can dropInventory again.

            if(tileQuestionMark != null) // If the tile is not null...
            {
                tileQuestionMark.validate(); // Update it.
                world.setTileEntity(pos, tileQuestionMark); // Re-add the tile.
            }
        }

        keepInventory = false;
    }

    private void takeItemOutOfQBlock(World world, BlockPos pos, EntityPlayer entityPlayer)
    {
        if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileQuestionMark) // If the tile is not null and it's a question
        // mark...
        {
            TileQuestionMark tileQuestionMark = (TileQuestionMark) world.getTileEntity(pos); // Store tile.

            int itemInBlock = tileQuestionMark.getItemInBlock(); // Get item that is currently in the tile.
            ItemStack newItemStack = null; // Create this so we set what comes out.

            if(itemInBlock == ItemsInQuestionMarks.ITEM_COIN) // If the item was a Coin...
            {
                if(blockType.equals(EnumBlockType.SMB) || blockType.equals(EnumBlockType.SMB_INVISIBLE) || blockType.equals(EnumBlockType
                        .SMB_UNDERGROUND) || blockType.equals(EnumBlockType.SMB_CASTLE)) // If it was from Mario 1...
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbCoin, SoundCategory.BLOCKS, 1.0F, 1.0F); // Play this
                    // sound.
                    newItemStack = new ItemStack(ModItems.itemMarioCoin); // Set ItemStack to Mario 1 Coin.
                } else if(blockType.equals(EnumBlockType.SMW) || blockType.equals(EnumBlockType.SMW_INVISIBLE)) // If it was from Mario World...
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smwCoin, SoundCategory.BLOCKS, 1.0F, 1.0F); // Play this
                    // sound.
                    newItemStack = new ItemStack(ModItems.itemMarioCoin, 1, 1); // Set ItemStack to Mario World Coin.
                } else if(blockType.equals(EnumBlockType.SMB3) || blockType.equals(EnumBlockType.SMB3_INVISIBLE)) // If it was from Mario 3...
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbCoin, SoundCategory.BLOCKS, 1.0F, 1.0F); // Play this
                    // sound.
                    newItemStack = new ItemStack(ModItems.itemMarioCoin, 1, 2); // Set ItemStack to Mario 3 Coin.
                }
            } else if(itemInBlock == ItemsInQuestionMarks.ITEM_MUSHROOM) // If the item was a Mushroom...
            {
                if(blockType.equals(EnumBlockType.SMB) || blockType.equals(EnumBlockType.SMB_INVISIBLE) || blockType.equals(EnumBlockType
                        .SMB_UNDERGROUND) || blockType.equals(EnumBlockType.SMB_CASTLE)) // If it was from Mario 1...
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    // Play this sound.
                    newItemStack = new ItemStack(ModItems.itemMarioMushroom); // Set ItemStack to Mario 1 Mushroom.
                } else if(blockType.equals(EnumBlockType.SMW) || blockType.equals(EnumBlockType.SMW_INVISIBLE)) // If it was from Mario World...
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smwBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    // Play this sound.
                    newItemStack = new ItemStack(ModItems.itemMarioMushroom, 1, 1); // Set ItemStack to Mario World Mushroom.
                } else if(blockType.equals(EnumBlockType.SMB3) || blockType.equals(EnumBlockType.SMB3_INVISIBLE)) // If it was from Mario 3...
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    // Play this sound.
                    newItemStack = new ItemStack(ModItems.itemMarioMushroom, 1, 2); // Set ItemStack to Mario 3 Mushroom.
                }
            } else if(itemInBlock == ItemsInQuestionMarks.ITEM_1UP) // If the item was a 1Up...
            {
                if(blockType.equals(EnumBlockType.SMB) || blockType.equals(EnumBlockType.SMB_INVISIBLE) || blockType.equals(EnumBlockType
                        .SMB_UNDERGROUND) || blockType.equals(EnumBlockType.SMB_CASTLE)) // If it was from Mario 1...
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    // Play this sound.
                    newItemStack = new ItemStack(ModItems.item1Up); // Set ItemStack to Mario 1 1Up.
                } else if(blockType.equals(EnumBlockType.SMW) || blockType.equals(EnumBlockType.SMW_INVISIBLE)) // If it was from Mario World...
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smwBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    // Play this sound.
                    newItemStack = new ItemStack(ModItems.item1Up, 1, 1); // Set ItemStack to Mario World 1Up.
                } else if(blockType.equals(EnumBlockType.SMB3) || blockType.equals(EnumBlockType.SMB3_INVISIBLE)) // If it was from Mario 3...
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    // Play this sound.
                    newItemStack = new ItemStack(ModItems.item1Up, 1, 2); // Set ItemStack to Mario 3 1Up.
                }
            } else if(itemInBlock == ItemsInQuestionMarks.ITEM_CAPE) // If the item was a Cape...
            {
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smwBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F); //
                // Play this sound.
                newItemStack = new ItemStack(ModItems.itemCape); // Set ItemStack to Cape.
            } else if(itemInBlock == ItemsInQuestionMarks.ITEM_FIRE_FLOWER)
            {
                if(blockType.equals(EnumBlockType.SMB) || blockType.equals(EnumBlockType.SMB_INVISIBLE) || blockType.equals(EnumBlockType
                        .SMB_UNDERGROUND) || blockType.equals(EnumBlockType.SMB_CASTLE) || blockType.equals(EnumBlockType.SMB3) || blockType.equals
                        (EnumBlockType.SMB3_INVISIBLE)) // If it was from Mario 1 or Mario 3...
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    // Play this sound.
                } else if(blockType.equals(EnumBlockType.SMW) || blockType.equals(EnumBlockType.SMW_INVISIBLE)) // If it was from Mario World...
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smwBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    // Play this sound.
                }

                newItemStack = new ItemStack(ModItems.itemFireFlower); // Set ItemStack to Fire Flower.
            } else if(itemInBlock == ItemsInQuestionMarks.ITEM_STAR_MAN) // If the item was a Starman...
            {
                newItemStack = new ItemStack(ModItems.itemStarMan); // Set ItemStack to Starman.

                if(blockType.equals(EnumBlockType.SMB) || blockType.equals(EnumBlockType.SMB_INVISIBLE) || blockType.equals(EnumBlockType
                        .SMB_UNDERGROUND) || blockType.equals(EnumBlockType.SMB_CASTLE) || blockType.equals(EnumBlockType.SMB3) || blockType.equals
                        (EnumBlockType.SMB3_INVISIBLE)) // If it was from Mario 1 or Mario 3...
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smbBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    // Play this sound.
                } else if(blockType.equals(EnumBlockType.SMW) || blockType.equals(EnumBlockType.SMW_INVISIBLE)) // If it was from Mario World...
                {
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.smwBlockHitPowerup, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    // Play this sound.
                }
            } else if(itemInBlock == ItemsInQuestionMarks.ITEM_BEANSTALK) // If the item was a Beanstalk...
            {
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.beanstalk, SoundCategory.BLOCKS, 1.0F, 1.0F); // Play this
                // sound.

                for(int i = 1; i < 10; i++) // Go from 1 to 9.
                {
                    world.setBlockState(pos.up(i), ModBlocks.blockBeanstalk.getDefaultState()); // Set the block to a beanstalk
                }

                world.setBlockState(pos.up(10), ModBlocks.blockBeanstalk.getDefaultState().withProperty(BlockBeanstalk.ISTOP, true), 2); // Set the
                // top to the top of the beanstalk.
            }

            if(newItemStack != null) // If the ItemStack is not null...
            {
                if(entityPlayer != null) // If the player is not null...
                {
                    // This is unused currently... It's a leftover from when you could right-click a block to take the item out.
                    ItemHandlerHelper.giveItemToPlayer(entityPlayer, newItemStack); // Give the item to the player.
                } else // If the player is null...
                {
                    Random rand = new Random(); // Initialize random number generator.

                    float dY = rand.nextFloat() * 0.8F + 0.1F; // Do math to figure out how high the item will go.

                    EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY() + dY, pos.getZ(), newItemStack.copy()); // Create the item
                    // entity.

                    float factor = 0.05F; // We don't want it to go FLYING, so we slow it down a bit.
                    entityItem.motionX = rand.nextGaussian() * factor; // X motion.
                    entityItem.motionY = rand.nextGaussian() * factor + 0.2F; // Y motion.
                    entityItem.motionZ = rand.nextGaussian() * factor; // Z motion.
                    world.spawnEntity(entityItem); // Spawn the item entity.
                }
            }

            this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_NOTHING, world, pos, tileQuestionMark); // Update the state of the block.
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem,
                                    EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking()) // If the player is sneaking...
        {
            return false; // Exit out. If the player is holding a block, they will place it.
        } else // Otherwise...
        {
            if(!world.isRemote) // If we're on the server side...
            {
                if(world.getTileEntity(pos) instanceof TileQuestionMark) // If the TileEntity in this position is a question mario block...
                {
                    TileQuestionMark tileQuestionMark = (TileQuestionMark) world.getTileEntity(pos); // Store TileEntity for later use.

                    if(heldItem != null && tileQuestionMark.getItemInBlock() == ItemsInQuestionMarks.ITEM_NOTHING) // If the player is holding
                    // something, and the question mark block is empty...
                    {
                        Item item = heldItem.getItem(); // Get the held item.
                        int blockType = 0; // Used to compare against damage values.

                        if(this.blockType.equals(EnumBlockType.SMW) || this.blockType.equals(EnumBlockType.SMW_INVISIBLE)) // If the block is from
                        // Mario World...
                        {
                            blockType = 1; // Set blockType to the same as the damage values for Mario World items.
                        } else if(this.blockType.equals(EnumBlockType.SMB3) || this.blockType.equals(EnumBlockType.SMB3_INVISIBLE)) // If the block
                        // is from Mario World...
                        {
                            blockType = 2; // Set blockType to the same as the damage values for Mario 3 items.
                        }

                        if(item instanceof ItemCoin) // If the item is a Coin...
                        {
                            if(heldItem.getItemDamage() == blockType) // If it matches the block type...
                            {
                                heldItem.stackSize--; // Decrease amount of items in stack by one.
                                this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_COIN, world, pos, tileQuestionMark); // Update the state of
                                // the block.
                            }
                        } else if(item instanceof ItemMushroom) // If the item is a Mushroom...
                        {
                            if(heldItem.getItemDamage() == blockType) // If it matches the block type...
                            {
                                heldItem.stackSize--; // Decrease amount of items in stack by one.
                                this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_MUSHROOM, world, pos, tileQuestionMark); // Update the state
                                // of the block.
                            }
                        } else if(item instanceof Item1Up) // If the item is a 1Up...
                        {
                            if(heldItem.getItemDamage() == blockType) // If it matches the block type...
                            {
                                heldItem.stackSize--; // Decrease amount of items in stack by one.
                                this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_1UP, world, pos, tileQuestionMark); // Update the state of
                                // the block.
                            }
                        } else if(item instanceof ItemCape) // If the item is a Cape...
                        {
                            if(blockType == 1) // If the block is a Mario World block...
                            {
                                heldItem.stackSize--; // Decrease amount of items in stack by one.
                                this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_CAPE, world, pos, tileQuestionMark); // Update the state of
                                // the block.
                            }
                        } else if(item instanceof ItemFireFlower) // If the item is a Fire Flower...
                        {
                            heldItem.stackSize--; // Decrease amount of items in stack by one.
                            this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_FIRE_FLOWER, world, pos, tileQuestionMark); // Update the state
                            // of the block.
                        } else if(item instanceof ItemStarMan) // If the item is a Starman...
                        {
                            heldItem.stackSize--; // Decrease amount of items in stack by one.
                            this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_STAR_MAN, world, pos, tileQuestionMark); // Update the state of
                            // the block.
                        } else if(item == Item.getItemFromBlock(ModBlocks.blockBeanstalk)) // If the item is a beanstalk...
                        {
                            heldItem.stackSize--; // Decrease amount of items in stack by one.
                            this.updateQuestionMarkState(ItemsInQuestionMarks.ITEM_BEANSTALK, world, pos, tileQuestionMark); // Update the state of
                            // the block.
                        }
                    }
                }
            }

            return false; // If the player is holding a block, they will place it.
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if(blockType.equals(EnumBlockType.SMB))
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyQuestionMarkSMB);
        } else if(blockType.equals(EnumBlockType.SMB_INVISIBLE))
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyInvisibleBlockSMB);
        } else if(blockType.equals(EnumBlockType.SMB_UNDERGROUND))
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyQuestionMarkUndergroundSMB);
        } else if(blockType.equals(EnumBlockType.SMB_CASTLE))
        {
            return Item.getItemFromBlock(ModBlocks.blockQuestionMarkCastleSMB);
        } else if(blockType.equals(EnumBlockType.SMB3))
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyQuestionMarkSMB3);
        } else if(blockType.equals(EnumBlockType.SMB3_INVISIBLE))
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyInvisibleBlockSMB3);
        } else if(blockType.equals(EnumBlockType.SMW))
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyQuestionMark);
        } else if(blockType.equals(EnumBlockType.SMW_INVISIBLE))
        {
            return Item.getItemFromBlock(ModBlocks.blockEmptyInvisibleBlock);
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
            if(this instanceof BlockQuestionMark)
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
            if(blockType.equals(EnumBlockType.SMB) || blockType.equals(EnumBlockType.SMB_INVISIBLE) || blockType.equals(EnumBlockType
                    .SMB_UNDERGROUND) || blockType.equals(EnumBlockType.SMB_CASTLE))
            {
                itemStack = new ItemStack(ModItems.itemMarioCoin);
            } else if(blockType.equals(EnumBlockType.SMW) || blockType.equals(EnumBlockType.SMW_INVISIBLE))
            {
                itemStack = new ItemStack(ModItems.itemMarioCoin, 1, 1);
            } else if(blockType.equals(EnumBlockType.SMB3) || blockType.equals(EnumBlockType.SMB3_INVISIBLE))
            {
                itemStack = new ItemStack(ModItems.itemMarioCoin, 1, 2);
            }
        } else if(itemInBlock == ItemsInQuestionMarks.ITEM_MUSHROOM)
        {
            if(blockType.equals(EnumBlockType.SMB) || blockType.equals(EnumBlockType.SMB_INVISIBLE) || blockType.equals(EnumBlockType
                    .SMB_UNDERGROUND) || blockType.equals(EnumBlockType.SMB_CASTLE))
            {
                itemStack = new ItemStack(ModItems.itemMarioMushroom);
            } else if(blockType.equals(EnumBlockType.SMW) || blockType.equals(EnumBlockType.SMW_INVISIBLE))
            {
                itemStack = new ItemStack(ModItems.itemMarioMushroom, 1, 1);
            } else if(blockType.equals(EnumBlockType.SMB3) || blockType.equals(EnumBlockType.SMB3_INVISIBLE))
            {
                itemStack = new ItemStack(ModItems.itemMarioMushroom, 1, 2);
            }
        } else if(itemInBlock == ItemsInQuestionMarks.ITEM_1UP)
        {
            if(blockType.equals(EnumBlockType.SMB) || blockType.equals(EnumBlockType.SMB_INVISIBLE) || blockType.equals(EnumBlockType
                    .SMB_UNDERGROUND) || blockType.equals(EnumBlockType.SMB_CASTLE))
            {
                itemStack = new ItemStack(ModItems.item1Up);
            } else if(blockType.equals(EnumBlockType.SMW) || blockType.equals(EnumBlockType.SMW_INVISIBLE))
            {
                itemStack = new ItemStack(ModItems.item1Up, 1, 1);
            } else if(blockType.equals(EnumBlockType.SMB3) || blockType.equals(EnumBlockType.SMB3_INVISIBLE))
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
        } else if(itemInBlock == ItemsInQuestionMarks.ITEM_BEANSTALK)
        {
            itemStack = new ItemStack(ModBlocks.blockBeanstalk);
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
            world.spawnEntity(entityItem);
            questionMark.setItemInBlock(ItemsInQuestionMarks.ITEM_NOTHING);
        }
    }

    public EnumBlockType getBlockType()
    {
        return this.blockType;
    }
}