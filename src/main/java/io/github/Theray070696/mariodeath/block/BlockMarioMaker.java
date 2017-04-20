package io.github.Theray070696.mariodeath.block;

import io.github.Theray070696.mariodeath.MarioDeath;
import io.github.Theray070696.mariodeath.lib.GuiIds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Theray070696 on 3/31/2016.
 */
public class BlockMarioMaker extends BlockMario
{
    public BlockMarioMaker()
    {
        super();

        this.setUnlocalizedName("marioBlockMarioMaker");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
        {
            player.openGui(MarioDeath.INSTANCE, GuiIds.MARIO_MAKER_GUI_ID, world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

        return true;
    }
}