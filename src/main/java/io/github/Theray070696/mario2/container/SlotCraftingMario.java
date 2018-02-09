package io.github.Theray070696.mario2.container;

import io.github.Theray070696.mario2.crafting.MarioMakerCraftingManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public class SlotCraftingMario extends Slot
{
    /**
     * The craft matrix inventory linked to this result slot.
     */
    private final InventoryCrafting craftMatrix;
    /**
     * The player that is using the GUI where this slot resides.
     */
    private final EntityPlayer player;
    /**
     * The number of items that have been crafted so far. Gets passed to ItemStack.onCrafting before being reset.
     */
    private int amountCrafted;

    public SlotCraftingMario(EntityPlayer player, InventoryCrafting craftingInventory, IInventory inventoryIn, int slotIndex, int xPosition, int
            yPosition)
    {
        super(inventoryIn, slotIndex, xPosition, yPosition);
        this.player = player;
        this.craftMatrix = craftingInventory;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean isItemValid(@Nullable ItemStack stack)
    {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int amount)
    {
        if(this.getHasStack())
        {
            this.amountCrafted += Math.min(amount, this.getStack().stackSize);
        }

        return super.decrStackSize(amount);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    protected void onCrafting(ItemStack stack, int amount)
    {
        this.amountCrafted += amount;
        this.onCrafting(stack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    protected void onCrafting(ItemStack stack)
    {
        if(this.amountCrafted > 0)
        {
            stack.onCrafting(this.player.world, this.player, this.amountCrafted);
        }

        this.amountCrafted = 0;

        // Can do achievements here. Left an example for crafting a workbench

        /*if(stack.getItem() == Item.getItemFromBlock(Blocks.CRAFTING_TABLE))
        {
            this.player.addStat(AchievementList.BUILD_WORK_BENCH);
        }*/
    }

    public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
    {
        this.onCrafting(stack);
        ItemStack[] aItemStack = MarioMakerCraftingManager.getInstance().getRemainingItems(this.craftMatrix, playerIn.world);

        for(int i = 0; i < aItemStack.length; ++i)
        {
            ItemStack itemStack = this.craftMatrix.getStackInSlot(i);
            ItemStack itemStack1 = aItemStack[i];

            if(itemStack != null)
            {
                this.craftMatrix.decrStackSize(i, 1);
                itemStack = this.craftMatrix.getStackInSlot(i);
            }

            if(itemStack1 != null)
            {
                if(itemStack == null)
                {
                    this.craftMatrix.setInventorySlotContents(i, itemStack1);
                } else if(ItemStack.areItemsEqual(itemStack, itemStack1) && ItemStack.areItemStackTagsEqual(itemStack, itemStack1))
                {
                    itemStack1.stackSize += itemStack.stackSize;
                    this.craftMatrix.setInventorySlotContents(i, itemStack1);
                } else if(!this.player.inventory.addItemStackToInventory(itemStack1))
                {
                    this.player.dropItem(itemStack1, false);
                }
            }
        }
    }
}
