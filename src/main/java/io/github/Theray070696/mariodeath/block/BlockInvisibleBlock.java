package io.github.Theray070696.mariodeath.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.Theray070696.mariodeath.configuration.ConfigHandler;
import net.minecraft.world.IBlockAccess;

/**
 * Created by Theray on 3/31/2016.
 */
public class BlockInvisibleBlock extends BlockQuestionMarkBase implements SMWQBlock
{
    public BlockInvisibleBlock()
    {
        super(false);

        this.setBlockName("marioBlockInvisibleBlock");
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        if(ConfigHandler.debugModeEnabled)
        {
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 0;
    }

    @Override
    public boolean isOpaqueCube()
    {
        if(ConfigHandler.debugModeEnabled)
        {
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        if(ConfigHandler.debugModeEnabled)
        {
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }
}
