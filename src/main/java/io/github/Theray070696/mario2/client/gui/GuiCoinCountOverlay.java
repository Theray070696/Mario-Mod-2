package io.github.Theray070696.mario2.client.gui;

import io.github.Theray070696.mario2.capability.CoinCountProvider;
import io.github.Theray070696.mario2.capability.ICoinCount;
import io.github.Theray070696.mario2.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.GuiIngameForge;

/**
 * Created by Theray070696 on 4/11/2017.
 */
public class GuiCoinCountOverlay extends GuiIngameForge
{
    public GuiCoinCountOverlay(Minecraft mc)
    {
        super(mc);
    }

    public void renderCoinCount(int width, int height)
    {
        if(mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            mc.mcProfiler.startSection("marioCoinCounter");

            EntityPlayer player = (EntityPlayer) mc.getRenderViewEntity();
            int left = 2;
            int top = height - 20;

            ICoinCount coinCountProvider = player.getCapability(CoinCountProvider.COIN_COUNT, null);
            int coinCount = coinCountProvider.getCoinCount();

            this.itemRenderer.renderItemIntoGUI(new ItemStack(ModItems.itemMarioCoin, 1, 1), left, top);
            left += 16;
            top += 4;
            getFontRenderer().drawString("x" + coinCount, left, top, 14737632, true);

            mc.mcProfiler.endSection();
        }
    }
}
