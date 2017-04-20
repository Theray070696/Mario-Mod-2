package io.github.Theray070696.mariodeath.network;

import io.github.Theray070696.mariodeath.MarioDeath;
import io.github.Theray070696.mariodeath.capability.CoinCountProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Theray070696 on 4/11/2017.
 */
public class PacketSyncCoinCounter implements IMessage
{
    private int newCoinCount;

    public PacketSyncCoinCounter() {}

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
                    MarioDeath.proxy.setClientCoinCount(message.newCoinCount);
                }
            });

            return null;
        }
    }
}
