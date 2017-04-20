package io.github.Theray070696.mariodeath.capability;

import io.github.Theray070696.mariodeath.MarioDeath;
import io.github.Theray070696.mariodeath.network.PacketSyncCoinCounter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import javax.annotation.Nonnull;

/**
 * Created by Theray070696 on 4/11/2017.
 */
public class CoinCount implements ICoinCount
{
    private int coinCount;

    public CoinCount()
    {
        this.coinCount = 0;
    }

    @Override
    public int getCoinCount()
    {
        return this.coinCount;
    }

    @Override
    public void setCoinCount(int amount)
    {
        this.coinCount = amount;
    }

    @Override
    public void addToCoinCount(int amount)
    {
        this.coinCount += amount;
    }

    @Override
    public void subtractFromCoinCount(int amount)
    {
        this.coinCount -= amount;
    }

    @Override
    public void sync(@Nonnull EntityPlayer player)
    {
        MarioDeath.network.sendTo(new PacketSyncCoinCounter(this.coinCount), (EntityPlayerMP) player);
    }
}
