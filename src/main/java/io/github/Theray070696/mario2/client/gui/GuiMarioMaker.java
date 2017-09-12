package io.github.Theray070696.mario2.client.gui;

import io.github.Theray070696.mario2.MarioMod2;
import io.github.Theray070696.mario2.container.ContainerMarioMaker;
import io.github.Theray070696.mario2.lib.ModInfo;
import io.github.Theray070696.mario2.network.PacketGetCoins;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Theray070696 on 3/31/2016.
 */
@SideOnly(Side.CLIENT)
public class GuiMarioMaker extends GuiContainer
{
    private static final ResourceLocation marioMakerGuiTextures = new ResourceLocation(ModInfo.MOD_ID, "textures/gui/marioMakerGUI.png");

    private int coinsToGet = 0;

    public GuiMarioMaker(InventoryPlayer inventoryPlayer, World world, BlockPos pos)
    {
        super(new ContainerMarioMaker(inventoryPlayer, world, pos));
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        this.fontRendererObj.drawString(I18n.format("gui.mario2:marioMaker"), 28, 6, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(marioMakerGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if(this.coinsToGet < 10)
        {
            this.fontRendererObj.drawString("" + coinsToGet, this.guiLeft + 232, this.guiTop + 13, 14737632);
        } else if(this.coinsToGet >= 10)
        {
            this.fontRendererObj.drawString("" + coinsToGet, this.guiLeft + 228, this.guiTop + 13, 14737632);
        }
    }

    @Override
    public void initGui()
    {
        super.initGui();

        this.buttonList.clear();

        this.buttonList.add(new GuiButton(0, this.guiLeft + 183, this.guiTop + 7, 20, 20, "--"));
        this.buttonList.add(new GuiButton(1, this.guiLeft + 205, this.guiTop + 7, 20, 20, "-"));
        this.buttonList.add(new GuiButton(2, this.guiLeft + 243, this.guiTop + 7, 20, 20, "+"));
        this.buttonList.add(new GuiButton(3, this.guiLeft + 265, this.guiTop + 7, 20, 20, "++"));
        this.buttonList.add(new GuiButton(4, this.guiLeft + 183, this.guiTop + 29, 103, 20, "Get SMB1 Coins"));
        this.buttonList.add(new GuiButton(5, this.guiLeft + 183, this.guiTop + 51, 103, 20, "Get SMB3 Coins"));
        this.buttonList.add(new GuiButton(6, this.guiLeft + 183, this.guiTop + 73, 103, 20, "Get SMW Coins"));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if(button.id == 0)
        {
            if(this.coinsToGet - 10 <= 0)
            {
                this.coinsToGet = 0;
            } else
            {
                this.coinsToGet -= 10;
            }
        } else if(button.id == 1 && this.coinsToGet != 0)
        {
            this.coinsToGet--;
        } else if(button.id == 2 && this.coinsToGet < 64)
        {
            this.coinsToGet++;
        } else if(button.id == 3)
        {
            if(this.coinsToGet + 10 >= 64)
            {
                this.coinsToGet = 64;
            } else
            {
                this.coinsToGet += 10;
            }
        } else if(button.id == 4)
        {
            MarioMod2.network.sendToServer(new PacketGetCoins(this.coinsToGet, 0));
        } else if(button.id == 5)
        {
            MarioMod2.network.sendToServer(new PacketGetCoins(this.coinsToGet, 2));
        } else if(button.id == 6)
        {
            MarioMod2.network.sendToServer(new PacketGetCoins(this.coinsToGet, 1));
        }
    }

    public List<Rectangle> getExtraGuiAreas()
    {
        List<Rectangle> list = new ArrayList<>();

        list.add(new Rectangle(this.guiLeft + 150, this.guiTop + 5, 130, 85));

        return list;
    }
}
