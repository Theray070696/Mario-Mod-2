package io.github.Theray070696.mariodeath.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

/**
 * Created by Theray070696 on 4/11/2017.
 */
public class CoinCountProvider implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(ICoinCount.class)
    public static final Capability<ICoinCount> COIN_COUNT = null;

    private ICoinCount instance = COIN_COUNT.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == COIN_COUNT;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        return capability == COIN_COUNT ? COIN_COUNT.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return COIN_COUNT.getStorage().writeNBT(COIN_COUNT, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        COIN_COUNT.getStorage().readNBT(COIN_COUNT, this.instance, null, nbt);
    }
}
