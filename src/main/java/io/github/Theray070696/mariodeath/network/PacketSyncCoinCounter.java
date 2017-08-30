package io.github.Theray070696.mariodeath.network;

import io.github.Theray070696.mariodeath.MarioMod2;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Theray070696 on 4/11/2017.
 */
public class PacketSyncCoinCounter implements IMessage
{
    private int newCoinCount;

    public PacketSyncCoinCounter()
    {
    }

    public PacketSyncCoinCounter(int newCoinCount)
    {
        this.newCoinCount = newCoinCount;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.newCoinCount = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.newCoinCount);
    }

    public static class Handler implements IMessageHandler<PacketSyncCoinCounter, IMessage>
    {
        @Override
        public IMessage onMessage(PacketSyncCoinCounter message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    MarioMod2.proxy.setClientCoinCount(message.newCoinCount);
                }
            });

            return null;
        }
    }
}
