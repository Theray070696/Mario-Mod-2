package io.github.Theray070696.mario2.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Created by Theray070696 on 4/11/2017.
 */
public class CoinCountStorage implements Capability.IStorage<ICoinCount>
{
    @Override
    public NBTBase writeNBT(Capability<ICoinCount> capability, ICoinCount instance, EnumFacing side)
    {
        return new NBTTagInt(instance.getCoinCount());
    }

    @Override
    public void readNBT(Capability<ICoinCount> capability, ICoinCount instance, EnumFacing side, NBTBase nbt)
    {
        instance.setCoinCount(((NBTTagInt) nbt).getInt());
    }
}
