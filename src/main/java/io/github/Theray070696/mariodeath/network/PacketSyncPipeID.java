package io.github.Theray070696.mariodeath.network;

import io.github.Theray070696.mariodeath.block.tile.TilePipe;
import io.github.Theray070696.mariodeath.core.PipeIDHandler;
import io.github.Theray070696.mariodeath.lib.BlockPosPair;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Theray070696 on 4/27/2017.
 */
public class PacketSyncPipeID implements IMessage
{
    private int newID;
    private BlockPos pos;
    private int dimension;
    private int oldID;

    public PacketSyncPipeID() {}

    public PacketSyncPipeID(int newID, BlockPos pos, int dimension, int oldID)
    {
        this.newID = newID;
        this.pos = pos;
        this.dimension = dimension;
        this.oldID = oldID;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.newID = buf.readInt();
        this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        this.dimension = buf.readInt();
        this.oldID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.newID);
        buf.writeInt(this.pos.getX());
        buf.writeInt(this.pos.getY());
        buf.writeInt(this.pos.getZ());
        buf.writeInt(this.dimension);
        buf.writeInt(this.oldID);
    }

    public static class Handler implements IMessageHandler<PacketSyncPipeID, IMessage>
    {
        @Override
        public IMessage onMessage(PacketSyncPipeID message, MessageContext ctx)
        {
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    if(PipeIDHandler.instance(false).getPosPair(message.oldID) != null)
                    {
                        BlockPosPair posPair = PipeIDHandler.instance(false).getPosPair(message.oldID);

                        if(posPair.getPos1() != null && !posPair.getPos1().equals(new BlockPos(0, 0, 0)) && posPair.getPos2() != null && !posPair.getPos2().equals(new BlockPos(0, 0, 0)))
                        {
                            if(posPair.getPos1().equals(message.pos) && posPair.getDim1() == message.dimension)
                            {
                                PipeIDHandler.instance(false).clearPosPair(message.oldID);
                                PipeIDHandler.instance(false).setPosPair(message.oldID, new BlockPosPair(posPair.getPos2(), posPair.getDim2(), null, 0));
                            } else if(posPair.getPos2().equals(message.pos) && posPair.getDim2() == message.dimension)
                            {
                                PipeIDHandler.instance(false).clearPosPair(message.oldID);
                                PipeIDHandler.instance(false).setPosPair(message.oldID, new BlockPosPair(posPair.getPos1(), posPair.getDim1(), null, 0));
                            } else
                            {
                                PipeIDHandler.instance(false).clearPosPair(message.oldID);
                            }
                        } else
                        {
                            PipeIDHandler.instance(false).clearPosPair(message.oldID);
                        }
                    } else
                    {
                        PipeIDHandler.instance(false).clearPosPair(message.oldID);
                    }

                    if(PipeIDHandler.instance(false).getPosPair(message.newID) != null)
                    {
                        BlockPosPair posPair = PipeIDHandler.instance(false).getPosPair(message.newID);

                        if(posPair.getPos1() != null && (posPair.getPos2() == null || posPair.getPos2().equals(new BlockPos(0, 0, 0))))
                        {
                            PipeIDHandler.instance(false).clearPosPair(message.newID);
                            PipeIDHandler.instance(false).setPosPair(message.newID, new BlockPosPair(posPair.getPos1(), posPair.getDim1(), message.pos, message.dimension));
                        } else if((posPair.getPos1() == null || posPair.getPos1().equals(new BlockPos(0, 0, 0))) && (posPair.getPos2() == null || posPair.getPos2().equals(new BlockPos(0, 0, 0))))
                        {
                            PipeIDHandler.instance(false).clearPosPair(message.newID);
                            PipeIDHandler.instance(false).setPosPair(message.newID, new BlockPosPair(message.pos, message.dimension, null, 0));
                        } else if((posPair.getPos1() == null || posPair.getPos1().equals(new BlockPos(0, 0, 0))) && posPair.getPos2() != null)
                        {
                            PipeIDHandler.instance(false).clearPosPair(message.newID);
                            PipeIDHandler.instance(false).setPosPair(message.newID, new BlockPosPair(message.pos, message.dimension, null, 0));
                        } else if(posPair.getPos2() != null && (posPair.getPos1() == null || posPair.getPos1().equals(new BlockPos(0, 0, 0))))
                        {
                            PipeIDHandler.instance(false).clearPosPair(message.newID);
                            PipeIDHandler.instance(false).setPosPair(message.newID, new BlockPosPair(posPair.getPos2(), posPair.getDim2(), message.pos, message.dimension));
                        }
                    } else
                    {
                        PipeIDHandler.instance(false).clearPosPair(message.newID);
                        PipeIDHandler.instance(false).setPosPair(message.newID, new BlockPosPair(message.pos, message.dimension, null, 0));
                    }

                    World world = ctx.getServerHandler().playerEntity.worldObj;

                    if(world.getTileEntity(message.pos) != null && world.getTileEntity(message.pos) instanceof TilePipe)
                    {
                        ((TilePipe) world.getTileEntity(message.pos)).setWarpID(message.newID);
                    }
                }
            });

            return null;
        }
    }
}
