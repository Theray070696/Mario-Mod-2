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
    private BlockPos masterPos;
    private BlockPos otherPipePos;
    private int otherPipeDim;

    public void reset()
    {
        masterPos = BlockPos.ORIGIN;
        hasMaster = false;
        isMaster = false;

        otherPipePos = BlockPos.ORIGIN;
        otherPipeDim = 0;

        this.markDirty();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data)
    {
        super.writeToNBT(data);

        data.setInteger("masterX", masterPos.getX());
        data.setInteger("masterY", masterPos.getY());
        data.setInteger("masterZ", masterPos.getZ());
        data.setBoolean("hasMaster", hasMaster);
        data.setBoolean("isMaster", isMaster);

        data.setBoolean("connectedRight", connectedRight);
        data.setBoolean("connectedDown", connectedDown);
        data.setBoolean("rearBlock", rearBlock);

        if(hasMaster() && isMaster())
        {
            // Any other values should ONLY BE SAVED TO THE MASTER
            data.setInteger("otherPipeX", otherPipePos.getX());
            data.setInteger("otherPipeY", otherPipePos.getY());
            data.setInteger("otherPipeZ", otherPipePos.getZ());
            data.setInteger("otherPipeDim", otherPipeDim);
        }

        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data)
    {
        super.readFromNBT(data);

        masterPos = new BlockPos(data.getInteger("masterX"), data.getInteger("masterY"), data.getInteger("masterZ"));
        hasMaster = data.getBoolean("hasMaster");
        isMaster = data.getBoolean("isMaster");

        connectedRight = data.getBoolean("connectedRight");
        connectedDown = data.getBoolean("connectedDown");
        rearBlock = data.getBoolean("rearBlock");

        if(hasMaster() && isMaster())
        {
            // Any other values should ONLY BE READ BY THE MASTER
            otherPipePos = new BlockPos(data.getInteger("otherPipeX"), data.getInteger("otherPipeY"), data.getInteger("otherPipeZ"));
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
            TileEntity tile = this.world.getTileEntity(this.masterPos);
            if(tile instanceof TilePipe)
            {
                TilePipe tilePipe = (TilePipe) tile;

                if(tilePipe.isMaster())
                {
                    return tilePipe.getOtherPipePos();
                }
            }
        }

        return this.otherPipePos;
    }

    public int getOtherPipeDimension()
    {
        if(this.hasMaster() && !this.isMaster())
        {
            TileEntity tile = this.world.getTileEntity(this.masterPos);
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

    public void setMasterCoords(BlockPos pos)
    {
        this.masterPos = pos;
        this.markDirty();
    }

    public void setOtherPipePos(BlockPos pos, int dim)
    {
        if(this.hasMaster() && !this.isMaster())
        {
            TileEntity tile = this.world.getTileEntity(this.masterPos);
            if(tile instanceof TilePipe)
            {
                TilePipe tilePipe = (TilePipe) tile;

                if(tilePipe.isMaster())
                {
                    tilePipe.setOtherPipePos(pos, dim);
                    tilePipe.markDirty();
                }
            }
        } else if(this.isMaster())
        {
            this.otherPipePos = pos;
            this.otherPipeDim = dim;
            this.markDirty();
        }
    }

    public BlockPos getMasterPos()
    {
        return this.masterPos;
    }

    public TilePipe getMasterTile()
    {
        if(this.isMaster())
        {
            return this;
        } else if(this.hasMaster())
        {
            if(this.world.getTileEntity(this.masterPos) instanceof TilePipe)
            {
                return (TilePipe) this.world.getTileEntity(this.masterPos);
            }
        }

        return null;
    }

    public BlockPipe getMasterBlock()
    {
        if(this.isMaster())
        {
            return (BlockPipe) this.world.getBlockState(pos).getBlock();
        } else if(this.hasMaster())
        {
            if(this.world.getBlockState(this.masterPos).getBlock() instanceof BlockPipe)
            {
                return (BlockPipe) this.world.getBlockState(this.masterPos).getBlock();
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
