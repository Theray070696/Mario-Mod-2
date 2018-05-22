package io.github.Theray070696.mario2.network;

import io.github.Theray070696.mario2.capability.CoinCountProvider;
import io.github.Theray070696.mario2.capability.ICoinCount;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestCoinSync implements IMessage
{
    public PacketRequestCoinSync()
    {
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
    }

    public static class Handler implements IMessageHandler<PacketRequestCoinSync, IMessage>
    {
        @Override
        public IMessage onMessage(PacketRequestCoinSync message, MessageContext ctx)
        {
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.world;
            mainThread.addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    ICoinCount coinCount = ctx.getServerHandler().player.getCapability(CoinCountProvider.COIN_COUNT, null);
                    coinCount.sync(ctx.getServerHandler().player);
                }
            });

            return null;
        }
    }
}
