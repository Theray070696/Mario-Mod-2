package io.github.Theray070696.mariodeath.client.gui;

import io.github.Theray070696.mariodeath.MarioDeath;
import io.github.Theray070696.mariodeath.network.PacketSyncPipeID;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

/**
 * Created by Theray070696 on 4/27/2017.
 */
@SideOnly(Side.CLIENT)
public class GuiPipe extends GuiScreen
{
    private int dimension;
    private BlockPos pos;
    private int oldID;
    private int id;

    public GuiPipe(int dimension, BlockPos blockPos, int oldID)
    {
        super();

        this.dimension = dimension;
        this.pos = blockPos;
        this.oldID = oldID;
        this.id = oldID;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if(this.id < 10)
        {
            this.fontRendererObj.drawString("" + id, this.width / 2, this.height / 4 + 46, 14737632);
        } else if(this.id >= 10 && this.id < 100)
        {
            this.fontRendererObj.drawString("" + id, this.width / 2 - 3, this.height / 4 + 46, 14737632);
        } else
        {
            this.fontRendererObj.drawString("" + id, this.width / 2 - 6, this.height / 4 + 46, 14737632);
        }
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();

        this.buttonList.add(new GuiButton(0, this.width / 2 - 50 + 1, this.height / 4 + 40, 20, 20, "--"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 50 + 23, this.height / 4 + 40, 20, 20, "-"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 50 + 62, this.height / 4 + 40, 20, 20, "+"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 50 + 83, this.height / 4 + 40, 20, 20, "++"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if(button.id == 0)
        {
            if(this.id - 10 <= 0)
            {
                this.id = 0;
            } else
            {
                this.id -= 10;
            }
        } else if(button.id == 1 && this.id != 0)
        {
            this.id--;
        } else if(button.id == 2 && this.id < 999)
        {
            this.id++;
        } else if(button.id == 3)
        {
            if(this.id + 10 >= 999)
            {
                this.id = 999;
            } else
            {
                this.id += 10;
            }
        }

        MarioDeath.network.sendToServer(new PacketSyncPipeID(this.id, this.pos, this.dimension, this.oldID));
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if(keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode))
        {
            this.mc.thePlayer.closeScreen();
        }
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }

    public void updateScreen()
    {
        super.updateScreen();

        if(!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead)
        {
            this.mc.thePlayer.closeScreen();
        }
    }
}
