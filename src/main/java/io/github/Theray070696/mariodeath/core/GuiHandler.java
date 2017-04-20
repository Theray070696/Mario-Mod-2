package io.github.Theray070696.mariodeath.core;

import cpw.mods.fml.common.network.IGuiHandler;
import io.github.Theray070696.mariodeath.block.BlockMarioMaker;
import io.github.Theray070696.mariodeath.client.gui.GuiMarioMaker;
import io.github.Theray070696.mariodeath.container.ContainerMarioMaker;
import io.github.Theray070696.mariodeath.lib.GuiIds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by Theray on 3/31/2016.
 */
public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == GuiIds.MARIO_MAKER_GUI_ID && (world.getBlock(x, y, z) != null && world.getBlock(x, y, z) instanceof BlockMarioMaker))
        {
            return new ContainerMarioMaker(player.inventory, world, x, y, z);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == GuiIds.MARIO_MAKER_GUI_ID && (world.getBlock(x, y, z) != null && world.getBlock(x, y, z) instanceof BlockMarioMaker))
        {
            return new GuiMarioMaker(player.inventory, world, x, y, z);
        }

        return null;
    }
}
