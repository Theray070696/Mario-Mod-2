package io.github.Theray070696.mariodeath.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.Theray070696.mariodeath.configuration.ConfigHandler;
import net.minecraft.world.IBlockAccess;

/**
 * Created by Theray on 1/5/2017.
 */
public class BlockInvisibleBlockSMB extends BlockQuestionMarkBase implements SMBQBlock
{
    public BlockInvisibleBlockSMB()
    {
        super(false);

        this.setBlockName("marioBlockInvisibleBlockSMB");
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
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
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        return false;
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }
}
