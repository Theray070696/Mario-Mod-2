package io.github.Theray070696.mariodeath.container;

import io.github.Theray070696.mariodeath.block.ModBlocks;
import io.github.Theray070696.mariodeath.crafting.MarioMakerCraftingManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by Theray on 3/31/2016.
 */
public class ContainerMarioMaker extends Container
{
    /** The crafting matrix inventory (3x3). */
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();
    private World worldObj;
    private int posX;
    private int posY;
    private int posZ;

    public ContainerMarioMaker(InventoryPlayer inventoryPlayer, World world, int x, int y, int z)
    {
        this.worldObj = world;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.addSlotToContainer(new SlotCrafting(inventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        int l;
        int i1;

        for (l = 0; l < 3; ++l)
        {
            for (i1 = 0; i1 < 3; ++i1)
            {
                this.addSlotToContainer(new Slot(this.craftMatrix, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
            }
        }

        for (l = 0; l < 3; ++l)
        {
            for (i1 = 0; i1 < 9; ++i1)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
            }
        }

        for (l = 0; l < 9; ++l)
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
        this.craftResult.setInventorySlotContents(0, MarioMakerCraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer entityPlayer)
    {
        super.onContainerClosed(entityPlayer);

        if (!this.worldObj.isRemote)
        {
            for (int i = 0; i < 9; ++i)
            {
                ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

                if (itemstack != null)
                {
                    entityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
    }

    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return this.worldObj.getBlock(this.posX, this.posY, this.posZ) == ModBlocks.blockMarioMaker && entityPlayer.getDistanceSq((double) this.posX + 0.5D, (double) this.posY + 0.5D, (double) this.posZ + 0.5D) <= 64.0D;
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotID)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotID);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotID == 0)
            {
                if (!this.mergeItemStack(itemstack1, 10, 46, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (slotID >= 10 && slotID < 37)
            {
                if (!this.mergeItemStack(itemstack1, 37, 46, false))
                {
                    return null;
                }
            }
            else if (slotID >= 37 && slotID < 46)
            {
                if (!this.mergeItemStack(itemstack1, 10, 37, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 10, 46, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(entityPlayer, itemstack1);
        }

        return itemstack;
    }

    public boolean func_94530_a(ItemStack itemStack, Slot p_9slot530_2_)
    {
        return p_9slot530_2_.inventory != this.craftResult && super.func_94530_a(itemStack, p_9slot530_2_);
    }
}
