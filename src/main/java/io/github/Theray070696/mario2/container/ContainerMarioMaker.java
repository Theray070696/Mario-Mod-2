package io.github.Theray070696.mario2.container;

import io.github.Theray070696.mario2.block.ModBlocks;
import io.github.Theray070696.mario2.crafting.MarioMakerCraftingManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 3/31/2016.
 */
public class ContainerMarioMaker extends Container
{
    /**
     * Position of the mario maker
     */
    private final BlockPos pos;
    /**
     * The crafting matrix inventory (3x3).
     */
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();
    private World world;
    private Slot outputSlot;

    public ContainerMarioMaker(InventoryPlayer inventoryPlayer, World world, BlockPos pos)
    {
        this.world = world;
        this.pos = pos;
        this.outputSlot = this.addSlotToContainer(new SlotCraftingMario(inventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        int l;
        int i1;

        for(l = 0; l < 3; ++l)
        {
            for(i1 = 0; i1 < 3; ++i1)
            {
                this.addSlotToContainer(new Slot(this.craftMatrix, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
            }
        }

        for(l = 0; l < 3; ++l)
        {
            for(i1 = 0; i1 < 9; ++i1)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
            }
        }

        for(l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, l, 8 + l * 18, 142));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory inventory)
    {
        this.craftResult.setInventorySlotContents(0, MarioMakerCraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.world));
        ((SlotCraftingMario) this.outputSlot).setRecipe(MarioMakerCraftingManager.getInstance().findRecipeForMatrix(this.craftMatrix, this.world));
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer entityPlayer)
    {
        super.onContainerClosed(entityPlayer);

        if(!this.world.isRemote)
        {
            for(int i = 0; i < 9; ++i)
            {
                ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);

                if(!itemstack.isEmpty())
                {
                    entityPlayer.dropItem(itemstack, false);
                }
            }
        }
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return this.world.getBlockState(this.pos).getBlock() == ModBlocks.marioBlockMarioMaker && entityPlayer.getDistanceSq((double) this.pos.getX()
                + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotID)
    {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(slotID);

        if(slot != null && slot.getHasStack())
        {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();

            if(slotID == 0)
            {
                itemStack1.getItem().onCreated(itemStack1, this.world, entityPlayer);

                if(!this.mergeItemStack(itemStack1, 10, 46, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemStack1, itemStack);
            } else if(slotID >= 10 && slotID < 37)
            {
                if(!this.mergeItemStack(itemStack1, 37, 46, false))
                {
                    return ItemStack.EMPTY;
                }
            } else if(slotID >= 37 && slotID < 46)
            {
                if(!this.mergeItemStack(itemStack1, 10, 37, false))
                {
                    return ItemStack.EMPTY;
                }
            } else if(!this.mergeItemStack(itemStack1, 10, 46, false))
            {
                return ItemStack.EMPTY;
            }

            if(itemStack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            } else
            {
                slot.onSlotChanged();
            }

            if(itemStack1.getCount() == itemStack.getCount())
            {
                return ItemStack.EMPTY;
            }

            ItemStack itemStack2 = slot.onTake(entityPlayer, itemStack1);

            if(slotID == 0)
            {
                entityPlayer.dropItem(itemStack2, false);
            }
        }

        return itemStack;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in
     * is null for the initial slot that was double-clicked.
     */
    public boolean canMergeSlot(ItemStack stack, Slot slot)
    {
        return slot.inventory != this.craftResult && super.canMergeSlot(stack, slot);
    }
}
