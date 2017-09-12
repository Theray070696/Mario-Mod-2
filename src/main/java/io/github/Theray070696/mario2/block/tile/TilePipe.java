package io.github.Theray070696.mario2.block.tile;

import io.github.Theray070696.mario2.block.BlockPipe;
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
    private int otherPipeX, otherPipeY, otherPipeZ, otherPipeDim;

    public void reset()
    {
        masterX = 0;
        masterY = 0;
        masterZ = 0;
        hasMaster = false;
        isMaster = false;

        otherPipeX = 0;
        otherPipeY = 0;
        otherPipeZ = 0;
        otherPipeDim = 0;

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
            data.setInteger("otherPipeX", otherPipeX);
            data.setInteger("otherPipeY", otherPipeY);
            data.setInteger("otherPipeZ", otherPipeZ);
            data.setInteger("otherPipeDim", otherPipeDim);
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
            otherPipeX = data.getInteger("otherPipeX");
            otherPipeY = data.getInteger("otherPipeY");
            otherPipeZ = data.getInteger("otherPipeZ");
            otherPipeDim = data.getInteger("otherPipeDim");
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

    public void setConnectedRight(boolean connectedRight)
    {
        this.connectedRight = connectedRight;
        this.markDirty();
    }

    public boolean isConnectedDown()
    {
        return connectedDown;
    }

    public void setConnectedDown(boolean connectedDown)
    {
        this.connectedDown = connectedDown;
        this.markDirty();
    }

    public boolean isRearBlock()
    {
        return rearBlock;
    }

    public void setRearBlock(boolean rearBlock)
    {
        this.rearBlock = rearBlock;
        this.markDirty();
    }

    public BlockPos getOtherPipePos()
    {
        if(this.hasMaster() && !this.isMaster())
        {
            TileEntity tile = this.worldObj.getTileEntity(new BlockPos(this.getMasterX(), this.getMasterY(), this.getMasterZ()));
            if(tile instanceof TilePipe)
            {
                TilePipe tilePipe = (TilePipe) tile;

                if(tilePipe.isMaster())
                {
                    return tilePipe.getOtherPipePos();
                }
            }
        }

        return new BlockPos(this.otherPipeX, this.otherPipeY, this.otherPipeZ);
    }

    public int getOtherPipeDimension()
    {
        if(this.hasMaster() && !this.isMaster())
        {
            TileEntity tile = this.worldObj.getTileEntity(new BlockPos(this.getMasterX(), this.getMasterY(), this.getMasterZ()));
            if(tile instanceof TilePipe)
            {
                TilePipe tilePipe = (TilePipe) tile;

                if(tilePipe.isMaster())
                {
                    return tilePipe.getOtherPipeDimension();
                }
            }
        }

        return this.otherPipeDim;
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

    public void setOtherPipePos(BlockPos blockPos, int dim)
    {
        this.setOtherPipePos(blockPos.getX(), blockPos.getY(), blockPos.getZ(), dim);
    }

    public void setOtherPipePos(int x, int y, int z, int dim)
    {
        if(this.hasMaster() && !this.isMaster())
        {
            TileEntity tile = this.worldObj.getTileEntity(new BlockPos(this.getMasterX(), this.getMasterY(), this.getMasterZ()));
            if(tile instanceof TilePipe)
            {
                TilePipe tilePipe = (TilePipe) tile;

                if(tilePipe.isMaster())
                {
                    tilePipe.setOtherPipePos(x, y, z, dim);
                    tilePipe.markDirty();
                }
            }
        } else if(this.isMaster())
        {
            this.otherPipeX = x;
            this.otherPipeY = y;
            this.otherPipeZ = z;
            this.otherPipeDim = dim;
            this.markDirty();
        }
    }

    public BlockPos getMasterPos()
    {
        return new BlockPos(this.masterX, this.masterY, this.masterZ);
    }

    public TilePipe getMasterTile()
    {
        if(this.isMaster())
        {
            return this;
        } else if(this.hasMaster())
        {
            if(worldObj.getTileEntity(new BlockPos(this.masterX, this.masterY, this.masterZ)) instanceof TilePipe)
            {
                return (TilePipe) worldObj.getTileEntity(new BlockPos(this.masterX, this.masterY, this.masterZ));
            }
        }

        return null;
    }

    public BlockPipe getMasterBlock()
    {
        if(this.isMaster())
        {
            return (BlockPipe) worldObj.getBlockState(pos).getBlock();
        } else if(this.hasMaster())
        {
            if(worldObj.getBlockState(new BlockPos(this.masterX, this.masterY, this.masterZ)).getBlock() instanceof BlockPipe)
            {
                return (BlockPipe) worldObj.getBlockState(new BlockPos(this.masterX, this.masterY, this.masterZ)).getBlock();
            }
        }

        return null;
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
