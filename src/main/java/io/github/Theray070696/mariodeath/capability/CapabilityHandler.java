package io.github.Theray070696.mariodeath.capability;

import io.github.Theray070696.mariodeath.lib.ModInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Theray070696 on 4/11/2017.
 */
@SuppressWarnings("unused")
public class CapabilityHandler
{
    public static final ResourceLocation COIN_COUNT = new ResourceLocation(ModInfo.MOD_ID, "coinCount");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        if(!(event.getObject() instanceof EntityPlayer))
        {
            return;
        }

        event.addCapability(COIN_COUNT, new CoinCountProvider());
    }
}
