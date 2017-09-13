package io.github.Theray070696.mario2.network;

import io.github.Theray070696.mario2.capability.CoinCountProvider;
import io.github.Theray070696.mario2.item.ModItems;
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

    public PacketGetCoins()
    {
    }

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
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.world;
            mainThread.addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    if(message.coinCount <= ctx.getServerHandler().player.getCapability(CoinCountProvider.COIN_COUNT, null).getCoinCount())
                    {
                        ctx.getServerHandler().player.getCapability(CoinCountProvider.COIN_COUNT, null).subtractFromCoinCount(message
                                .coinCount);
                        ctx.getServerHandler().player.getCapability(CoinCountProvider.COIN_COUNT, null).sync(ctx.getServerHandler().player);

                        EntityItem entityItem = ctx.getServerHandler().player.entityDropItem(new ItemStack(ModItems.itemCoin, message
                                .coinCount, message.coinType), 0.0f);

                        entityItem.setNoPickupDelay();
                    }
                }
            });

            return null;
        }
    }
}
