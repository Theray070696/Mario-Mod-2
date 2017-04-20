package io.github.Theray070696.mariodeath.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.Theray070696.mariodeath.container.ContainerMarioMaker;
import io.github.Theray070696.mariodeath.lib.ModInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * Created by Theray on 3/31/2016.
 */
@SideOnly(Side.CLIENT)
public class GuiMarioMaker extends GuiContainer
{
    private static final ResourceLocation marioMakerGuiTextures = new ResourceLocation(ModInfo.MOD_ID, "textures/gui/marioMakerGUI.png");

    public GuiMarioMaker(InventoryPlayer inventoryPlayer, World world, int x, int y, int z)
    {
        super(new ContainerMarioMaker(inventoryPlayer, world, x, y, z));
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        this.fontRendererObj.drawString(I18n.format("gui.mariodeath:marioMaker"), 28, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(marioMakerGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}
