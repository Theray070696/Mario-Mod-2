package io.github.Theray070696.mariodeath.core;

import io.github.Theray070696.mariodeath.client.gui.GuiCoinCountOverlay;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Theray070696 on 4/11/2017.
 */
@SideOnly(Side.CLIENT)
@SuppressWarnings("unused")
public class ClientEventHandler
{
    @SubscribeEvent
    public void onDrawHud(RenderGameOverlayEvent.Post event)
    {
        if(event.getType() == ElementType.ALL)
        {
            new GuiCoinCountOverlay(Minecraft.getMinecraft()).renderCoinCount(event.getResolution().getScaledWidth(), event.getResolution()
                    .getScaledHeight());
        }
    }
}
