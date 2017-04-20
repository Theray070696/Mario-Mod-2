package io.github.Theray070696.mariodeath.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.Theray070696.mariodeath.MarioDeath;
import io.github.Theray070696.mariodeath.lib.GuiIds;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * Created by Theray on 3/31/2016.
 */
public class BlockMarioMaker extends BlockMario
{
    @SideOnly(Side.CLIENT)
    private IIcon top_icon;
    @SideOnly(Side.CLIENT)
    private IIcon bottom_icon;

    public BlockMarioMaker()
    {
        super();

        this.setBlockName("marioBlockMarioMaker");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if(!world.isRemote)
        {
            player.openGui(MarioDeath.INSTANCE, GuiIds.MARIO_MAKER_GUI_ID, world, x, y, z);
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("mariodeath:marioMaker_side");
        top_icon = iconRegister.registerIcon("mariodeath:marioMaker_top");
        bottom_icon = iconRegister.registerIcon("mariodeath:marioBlockGround");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        if(side == 0)
        {
            return bottom_icon;
        } else if(side == 1)
        {
            return top_icon;
        } else
        {
            return blockIcon;
        }
    }
}
