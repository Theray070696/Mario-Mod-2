package io.github.Theray070696.mario2.core;

import io.github.Theray070696.mario2.client.gui.GuiCoinCountOverlay;
import io.github.Theray070696.mario2.configuration.ConfigHandler;
import io.github.Theray070696.mario2.world.biome.BiomeForestOfIllusion;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

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

    private static float fogDensity = 0;

    private static final float FOG_DENSITY_PER_TICK = 0.00005F;

    @SubscribeEvent
    public void onFogRender(EntityViewRenderEvent.RenderFogEvent event)
    {
        if(!ConfigHandler.enableBiomes)
        {
            return;
        }

        float fogDensityTarget;

        if(event.getEntity().world.getBiomeForCoordsBody(event.getEntity().getPosition()) instanceof BiomeForestOfIllusion)
        {
            fogDensityTarget = 0.075f;
        } else
        {
            fogDensityTarget = 0;
        }

        if(fogDensity < fogDensityTarget)
        {
            fogDensity += FOG_DENSITY_PER_TICK;
        } else if(fogDensity > fogDensityTarget)
        {
            fogDensity -= FOG_DENSITY_PER_TICK;
        }
        if(fogDensity < FOG_DENSITY_PER_TICK)
        {
            fogDensity = 0;
        }

        if(fogDensity > 0)
        {
            GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
            GL11.glFogf(GL11.GL_FOG_DENSITY, fogDensity);
        } else
        {
            GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
        }
    }
}
