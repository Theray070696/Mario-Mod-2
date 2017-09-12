package io.github.Theray070696.mario2.block.tile;

import io.github.Theray070696.mario2.lib.ItemsInQuestionMarks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Theray070696 on 8/27/2015.
 */
public class TileQuestionMark extends TileEntity
{
    private int itemInBlock = ItemsInQuestionMarks.ITEM_NOTHING;

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

    public int getItemInBlock()
    {
        return this.itemInBlock;
    }

    public void setItemInBlock(int itemInBlock)
    {
        this.itemInBlock = itemInBlock;
        this.updateContainingBlockInfo();
        this.markDirty();
    }
}