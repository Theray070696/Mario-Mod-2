package io.github.Theray070696.mariodeath.block;

import io.github.Theray070696.mariodeath.configuration.ConfigHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Theray070696 on 1/5/2017.
 */
public class BlockInvisibleBlockSMB3 extends BlockQuestionMarkBase implements SMB3QBlock, IFilledQBlock
{
    public BlockInvisibleBlockSMB3()
    {
        super(false);

        this.setUnlocalizedName("marioBlockInvisibleBlockSMB3");
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        if(ConfigHandler.debugModeEnabled)
        {
            return EnumBlockRenderType.MODEL;
        } else
        {
            return EnumBlockRenderType.INVISIBLE;
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
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
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if(ConfigHandler.debugModeEnabled)
        {
            return true;
        } else
        {
            return false;
        }
    }
}
