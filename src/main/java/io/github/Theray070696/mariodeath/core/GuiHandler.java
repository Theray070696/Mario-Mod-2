package io.github.Theray070696.mariodeath.core;

import io.github.Theray070696.mariodeath.block.BlockMarioMaker;
import io.github.Theray070696.mariodeath.client.gui.GuiMarioMaker;
import io.github.Theray070696.mariodeath.container.ContainerMarioMaker;
import io.github.Theray070696.mariodeath.lib.GuiIds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by Theray070696 on 3/31/2016.
 */
public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        IBlockState blockState = world.getBlockState(pos);

        if(ID == GuiIds.MARIO_MAKER_GUI_ID && blockState.getBlock() instanceof BlockMarioMaker)
        {
            return new ContainerMarioMaker(player.inventory, world, pos);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        IBlockState blockState = world.getBlockState(pos);

        if(ID == GuiIds.MARIO_MAKER_GUI_ID && blockState.getBlock() instanceof BlockMarioMaker)
        {
            return new GuiMarioMaker(player.inventory, world, pos);
        }

        return null;
    }
}
