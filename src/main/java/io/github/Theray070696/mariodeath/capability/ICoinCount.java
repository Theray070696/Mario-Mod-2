package io.github.Theray070696.mariodeath.capability;

import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nonnull;

/**
 * Created by Theray070696 on 4/11/2017.
 */
public interface ICoinCount
{
    public int getCoinCount();

    public void setCoinCount(int amount);

    public void addToCoinCount(int amount);

    public void subtractFromCoinCount(int amount);

    void sync(@Nonnull EntityPlayer player);
}
