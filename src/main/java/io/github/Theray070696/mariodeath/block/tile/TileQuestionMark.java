package io.github.Theray070696.mariodeath.block.tile;

import io.github.Theray070696.mariodeath.block.BlockQuestionMark;
import io.github.Theray070696.mariodeath.lib.ItemsInQuestionMarks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

/**
 * Created by Theray070696 on 8/27/2015.
 */
public class TileQuestionMark extends TileEntity implements ITickable
{
    private int itemInBlock = ItemsInQuestionMarks.ITEM_NOTHING;
    private boolean shouldRun = true;

    public void update()
    {
        if(shouldRun)
        {
            shouldRun = false;
            if(this.worldObj.getBlockState(this.pos).getBlock() instanceof BlockQuestionMark && this.itemInBlock == ItemsInQuestionMarks.ITEM_NOTHING)
            {
                this.worldObj.setBlockToAir(this.pos);
            }
        }
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        this.itemInBlock = nbtTagCompound.getInteger("itemInBlock");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setInteger("itemInBlock", this.itemInBlock);

        return nbtTagCompound;
    }

    public void setItemInBlock(int itemInBlock)
    {
        this.itemInBlock = itemInBlock;
        this.updateContainingBlockInfo();
        this.markDirty();
    }

    public int getItemInBlock()
    {
        return this.itemInBlock;
    }
}