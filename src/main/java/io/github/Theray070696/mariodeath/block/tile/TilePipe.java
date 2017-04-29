package io.github.Theray070696.mariodeath.block.tile;

import io.github.Theray070696.mariodeath.block.BlockPipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Theray070696 on 2/9/2017.
 */
public class TilePipe extends TileEntity
{
    private boolean hasMaster;
    private boolean isMaster;
    private boolean connectedRight;
    private boolean connectedDown;
    private boolean rearBlock;
    private int masterX, masterY, masterZ;
    private int warpID;

    public void reset()
    {
        masterX = 0;
        masterY = 0;
        masterZ = 0;
        hasMaster = false;
        isMaster = false;
        warpID = 0;

        this.updateContainingBlockInfo();
        this.markDirty();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data)
    {
        super.writeToNBT(data);

        data.setInteger("masterX", masterX);
        data.setInteger("masterY", masterY);
        data.setInteger("masterZ", masterZ);
        data.setBoolean("hasMaster", hasMaster);
        data.setBoolean("isMaster", isMaster);

        data.setBoolean("connectedRight", connectedRight);
        data.setBoolean("connectedDown", connectedDown);
        data.setBoolean("rearBlock", rearBlock);

        if(hasMaster() && isMaster())
        {
            // Any other values should ONLY BE SAVED TO THE MASTER
            data.setInteger("warpID", warpID);
        }

        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data)
    {
        super.readFromNBT(data);
        masterX = data.getInteger("masterX");
        masterY = data.getInteger("masterY");
        masterZ = data.getInteger("masterZ");
        hasMaster = data.getBoolean("hasMaster");
        isMaster = data.getBoolean("isMaster");

        connectedRight = data.getBoolean("connectedRight");
        connectedDown = data.getBoolean("connectedDown");
        rearBlock = data.getBoolean("rearBlock");

        if(hasMaster() && isMaster())
        {
            // Any other values should ONLY BE READ BY THE MASTER
            warpID = data.getInteger("warpID");
        }
    }

    public boolean hasMaster()
    {
        return hasMaster;
    }

    public boolean isMaster()
    {
        return isMaster;
    }

    public int getMasterX()
    {
        return masterX;
    }

    public int getMasterY()
    {
        return masterY;
    }

    public int getMasterZ()
    {
        return masterZ;
    }

    public boolean isConnectedRight()
    {
        return connectedRight;
    }

    public boolean isConnectedDown()
    {
        return connectedDown;
    }

    public boolean isRearBlock()
    {
        return rearBlock;
    }

    public int getWarpID()
    {
        return this.warpID;
    }

    public void setHasMaster(boolean bool)
    {
        hasMaster = bool;
        this.markDirty();
    }

    public void setIsMaster(boolean bool)
    {
        isMaster = bool;
        this.markDirty();
    }

    public void setMasterCoords(int x, int y, int z)
    {
        masterX = x;
        masterY = y;
        masterZ = z;
        this.markDirty();
    }

    public void setConnectedRight(boolean connectedRight)
    {
        this.connectedRight = connectedRight;
        this.markDirty();
    }

    public void setConnectedDown(boolean connectedDown)
    {
        this.connectedDown = connectedDown;
        this.markDirty();
    }

    public void setRearBlock(boolean rearBlock)
    {
        this.rearBlock = rearBlock;
        this.markDirty();
    }

    public void setWarpID(int warpID)
    {
        this.warpID = warpID;
        this.markDirty();
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.getPos(), this.getBlockMetadata(), this.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        if(oldState.getBlock() instanceof BlockPipe && newSate.getBlock() instanceof BlockPipe)
        {
            return false;
        }

        return true;
    }
}
