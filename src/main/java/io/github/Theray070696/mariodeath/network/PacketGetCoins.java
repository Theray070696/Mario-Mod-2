package io.github.Theray070696.mariodeath.network;

import io.github.Theray070696.mariodeath.MarioDeath;
import io.github.Theray070696.mariodeath.capability.CoinCountProvider;
import io.github.Theray070696.mariodeath.item.ModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public class PacketGetCoins implements IMessage
{
    private int coinCount;
    private int coinType;

    public PacketGetCoins() {}

    public PacketGetCoins(int coinCount, int coinType)
    {
        this.coinCount = coinCount;
        this.coinType = coinType;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.coinCount = buf.readInt();
        this.coinType = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.coinCount);
        buf.writeInt(this.coinType);
    }

    public static class Handler implements IMessageHandler<PacketGetCoins, IMessage>
    {
        @Override
        public IMessage onMessage(PacketGetCoins message, MessageContext ctx)
        {
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    if(message.coinCount <= ctx.getServerHandler().playerEntity.getCapability(CoinCountProvider.COIN_COUNT, null).getCoinCount())
                    {
                        ctx.getServerHandler().playerEntity.getCapability(CoinCountProvider.COIN_COUNT, null).subtractFromCoinCount(message.coinCount);
                        ctx.getServerHandler().playerEntity.getCapability(CoinCountProvider.COIN_COUNT, null).sync(ctx.getServerHandler().playerEntity);

                        EntityItem entityItem = ctx.getServerHandler().playerEntity.entityDropItem(new ItemStack(ModItems.itemMarioCoin, message.coinCount, message.coinType), 0.0f);

                        entityItem.setNoPickupDelay();
                    }
                }
            });

            return null;
        }
    }
}
